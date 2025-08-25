import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Storage {

    private final String fileName;

    /**
     * Constructor for Storage.
     *
     * @param filePath file path to local storage
     */
    public Storage(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Saves data stored locally. If file does not exist, then a new file will be created.
     *
     * @param tasks the list of tasks to be saved into local storage
     * @throws IOException if the named file exists but is a directory rather than a regular file,
     * does not exist but cannot be created, or cannot be opened for any other reason
     */
    public void save(List<Task> tasks) throws IOException {
        // Reformat the list of tasks for storage
        StringBuilder output = new StringBuilder();
        tasks.forEach(task -> output.append(task.toString()).append("\n"));

        // Overwrite the current save file
        FileWriter fw = new FileWriter(fileName, false);
        fw.write(output.toString());
        fw.flush();
        fw.close();
    }

    /**
     * Loads data stored locally. If file does not exist, then a new file will be created.
     *
     * @return an array list of tasks containing any tasks that can be read from local storage
     * @throws DataIntegrityException if the save file is not in the correct format
     * @throws IOException if the named file exists but is a directory rather than a regular file,
     * does not exist but cannot be created, or cannot be opened for any other reason
     */
    public ArrayList<Task> load() throws DataIntegrityException, IOException {
        File file = new File(fileName);
        ArrayList<Task> TaskList = new ArrayList<>();

        if (file.createNewFile()) {
            return TaskList;
        }
        String input;
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        // Read the rest of data and add to list of tasks
        while ((input = br.readLine()) != null) {
            Task newTask;
            String description;
            String time;

            // Obtain relevant info based on type of task
            switch (input.charAt(1)) {
                case 'T': // todo
                    newTask = new ToDo(input.substring(7));
                    break;
                case 'D': { // [D][ ] desc (by: <datetime>)
                    description = input.substring(7, input.indexOf(" ("));
                    String byStr = input.substring(input.indexOf("(by: ") + 5, input.length() - 1);

                    LocalDateTime by = parseStoredDateTime(byStr);
                    // If Deadline constructor expects LocalDateTime:
                    newTask = new Deadline(description, by);
                    // If yours still takes a String that it parses internally, you can instead pass byStr.
                    break;
                }

                case 'E': { // [E][ ] desc (from: <datetime> to: <datetime>)
                    description = input.substring(7, input.indexOf(" ("));

                    int fromStart = input.indexOf("(from: ") + 7;
                    int toSep     = input.indexOf(" to: ", fromStart);
                    int endParen  = input.lastIndexOf(')');
                    if (fromStart < 7 || toSep == -1 || endParen == -1) {
                        throw new DataIntegrityException();
                    }

                    String fromStr = input.substring(fromStart, toSep).trim();
                    String toStr   = input.substring(toSep + 5, endParen).trim();

                    LocalDateTime from = parseStoredDateTime(fromStr);
                    LocalDateTime to   = parseStoredDateTime(toStr);

                    // If Event constructor expects LocalDateTime:
                    newTask = new Event(description, from, to);
                    // If yours expects LocalDate + LocalTime:
                    // newTask = new Event(description, from.toLocalDate(), from.toLocalTime(), to.toLocalTime());
                    break;
                }
                default:
                    throw new DataIntegrityException();
            }
            if (input.charAt(4) == 'X') {
                newTask.MarkDone();
            }

            // Add task to list but do not show a confirmation msg
            TaskList.add(newTask);
        }

        // Close reader
        br.close();
        return TaskList;
    }

    private LocalDateTime parseStoredDateTime(String s) throws DataIntegrityException {
        try {
            return DateTimeFormatUtils.getLocalDateTimeFromString(s);
        } catch (ShahzamExceptions ignored) {
            // If your toString() prints with DateTimeFormatUtils.formatDateTime(...)
            try {
                DateTimeFormatter OUT = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
                return LocalDateTime.parse(s, OUT);
            } catch (Exception e) {
                throw new DataIntegrityException(); // not a recognized stored datetime
            }
        }
    }
}
