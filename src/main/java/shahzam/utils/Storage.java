package shahzam.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import shahzam.exception.DataIntegrityException;
import shahzam.exception.ShahzamExceptions;
import shahzam.task.Deadline;
import shahzam.task.Event;
import shahzam.task.Task;
import shahzam.task.ToDo;


//@@author SkyBlaise99-reused
public class Storage {

    private final String FILE_NAME;

    /**
     * Constructor for shahzam.utils.Storage.
     *
     * @param fileName file path to local storage
     */
    public Storage(String fileName) {

        this.FILE_NAME = fileName;
    }

    /**
     * Saves data stored locally. If file does not exist, then a new file will be created.
     *
     * @param tasks the list of tasks to be saved into local storage
     * @throws IOException if the named file exists but is a directory rather than a regular file,
     *     does not exist but cannot be created, or cannot be opened for any other reason
     */
    public void save(List<Task> tasks) throws IOException {
        assert tasks != null : "Tasks is not initialized";

        StringBuilder output = new StringBuilder();
        tasks.forEach(task -> output.append(task.toString()).append("\n"));

        FileWriter fw = new FileWriter(FILE_NAME, false);
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
     *     does not exist but cannot be created, or cannot be opened for any other reason
     */
    public ArrayList<Task> load() throws DataIntegrityException, IOException {
        File file = new File(FILE_NAME);
        ArrayList<Task> TaskList = new ArrayList<>();

        if (file.createNewFile()) {
            return TaskList;
        }
        String input;
        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));

        while ((input = br.readLine()) != null) {
            Task newTask;
            String description;

            switch (input.charAt(1)) {
            case 'T':
                newTask = new ToDo(input.substring(7));
                break;
            case 'D':
                description = input.substring(7, input.indexOf(" ("));
                String byStr = input.substring(input.indexOf("(by: ") + 5, input.length() - 1);
                LocalDateTime by = parseStoredDateTime(byStr);
                newTask = new Deadline(description, by);
                break;
            case 'E':
                description = input.substring(7, input.indexOf(" ("));

                int fromStart = input.indexOf("(from: ") + 7;
                int toSep = input.indexOf(" to: ", fromStart);
                int endParen = input.lastIndexOf(')');
                if (fromStart < 7 || toSep == -1 || endParen == -1) {
                    throw new DataIntegrityException();
                }

                String fromStr = input.substring(fromStart, toSep).trim();
                String toStr = input.substring(toSep + 5, endParen).trim();

                LocalDateTime from = parseStoredDateTime(fromStr);
                LocalDateTime to = parseStoredDateTime(toStr);

                newTask = new Event(description, from, to);
                break;
            default:
                throw new DataIntegrityException();
            }
            if (input.charAt(4) == 'X') {
                newTask.MarkDone();
            }

            TaskList.add(newTask);
        }

        br.close();
        return TaskList;
    }

    private LocalDateTime parseStoredDateTime(String s) throws DataIntegrityException {
        s = s.trim();
        DateTimeFormatter[] formats = new DateTimeFormatter[] {
                java.time.format.DateTimeFormatter.ofPattern("MMM d yyyy, h:mm a", java.util.Locale.ENGLISH),
                java.time.format.DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a", java.util.Locale.ENGLISH),
                java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
                java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME,
                java.time.format.DateTimeFormatter.ofPattern("MMM dd yyyy HHmm", java.util.Locale.ENGLISH) // your old fallback
        };

        for (DateTimeFormatter fmt : formats) {
            try {
                return LocalDateTime.parse(s, fmt);
            } catch (Exception ignore) {
                // try next
            }
        }

        try {
            return DateTimeFormatUtils.getLocalDateTimeFromString(s);
        } catch (ShahzamExceptions e) {
            throw new DataIntegrityException();
        }
    }
}
