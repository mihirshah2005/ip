import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.IOException;


public class Shahzam {

    private final String WELCOME_MSG = "\nThe word was spoken, SHAHZAM awakens.\n" + "What can I do for you today?";
    private final String EXIT_MSG = "Thunder quiets. SHAHZAM signing off, until next time.";
    /*private final String logo =  "  ____  _           _                        \n" +
            " / ___|| |__   __ _| |__  ______ _ _ __ ___  \n" +
            " \\___ \\| '_ \\ / _` | '_ \\|_  / _` | '_ ` _ \\ \n" +
            "  ___) | | | | (_| | | | |/ / (_| | | | | | |\n" +
            " |____/|_| |_|\\__,_|_| |_/___\\__,_|_| |_| |_|\n" +
            "                                             ";

     */

    private final ArrayList<Task> TaskList = new ArrayList<>();
    private final String fileName = "data.txt";


    public void run() {
        //System.out.println(logo + WELCOME_MSG);
        System.out.println(WELCOME_MSG);

        try {
            loadData();
        } catch (ShahzamExceptions | IOException e) {
            System.out.println(e.getMessage());
        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine().trim();

            try {
                if (input.equals("bye")){
                    break;
                }

                interpretCommand(input);

                // Update storage file
                saveData();

            } catch ( ShahzamExceptions | IOException e) {
                System.out.println(" OOPS! " + e.getMessage());
            }

        }
        sc.close();

        System.out.println(EXIT_MSG);
    }

    private void interpretCommand(String input) throws ShahzamExceptions {
        // Each if else handles one type of command
            if (input.equals("list")) {
                PrintList(TaskList);
            } else if (input.startsWith("mark ")) {
                TaskDone(input);
            } else if (input.startsWith("unmark ")) {
                TaskUnmark(input);
            } else if (input.startsWith("todo ")) {
                addToDo(input);
            } else if (input.startsWith("event ")) {
                addEvent(input);
            } else if (input.startsWith("deadline ")) {
                addDeadline(input);
            } else if (input.startsWith("delete ")) {
                deleteTask(input);
            } else {
                throw new UnknownCommandException();
            }
        }

    private void saveData() throws IOException {
        // Reformat the list of tasks for storage
        StringBuilder output = new StringBuilder();
        TaskList.forEach(task -> output.append(task.toString()).append("\n"));

        FileWriter fw = new FileWriter(fileName, false);
        fw.write(output.toString());
        fw.close();
    }

    private void loadData() throws DataIntegrityException, IOException {
        // Initialize save file and create parent directory
        File file = new File(fileName);
        if (file.createNewFile()) {
            return;
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
                newTask = new Event(description, from.toLocalTime(), to.toLocalTime());
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
            AddTask(newTask, false);
        }

        // Close reader
        br.close();
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

    private void AddTask(Task newTask, boolean showMsg) {
        TaskList.add(newTask);
        if (showMsg) {
            System.out.println("Alright, new task added to the list:" + "\n  " +
                    newTask + "\n" +
                    "Now you have " + TaskList.size() + " task(s) in the list.");
        }

    }

    private void TaskDone(String input) throws InvalidTaskNumberException {
        try {
            int idx = Integer.parseInt(input.substring(5).trim());
            if (idx < 1 || idx > TaskList.size()) {
                throw new InvalidTaskNumberException("There is no task with that number.");
            }

            Task t = TaskList.get(idx - 1);
            t.MarkDone();
            System.out.println("Nice! I've marked this task as done:\n  " + t);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new InvalidTaskNumberException("Please provide a valid task number.");
        }

    }

    private void TaskUnmark(String input) throws InvalidTaskNumberException{
        try {
            int idx = Integer.parseInt(input.substring(7).trim());
            if (idx < 1 || idx > TaskList.size()) {
                throw new InvalidTaskNumberException("There is no task with that number.");
            }
            Task t = TaskList.get(idx - 1);
            t.UnmarkDone();
            System.out.println("OK, I've marked this task as not done yet:\n  " + t);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new InvalidTaskNumberException("Please provide a valid task number.");
        }

    }

    private void addToDo(String input) throws EmptyTaskDescriptionException{
        if (input.length() < 5) {
            throw new EmptyTaskDescriptionException("The description of a todo cannot be empty.");
        } else {
            Task t = new ToDo(input.substring(5).trim());
            AddTask(t, true);
        }
    }

    private void addDeadline(String input) throws InvalidDeadlineFormatException{
        try {
            String[] parts = input.substring(9).split("/by", 2);
            if (parts.length < 2) {
                throw new InvalidDeadlineFormatException("Please specify a deadline with the /by keyword.");
            }

            String description = parts[0].trim();
            String byRaw       = parts[1].trim();

            // parse full datetime using your util (Option A)
            LocalDateTime by = DateTimeFormatUtils.getLocalDateTimeFromString(byRaw);

            Task t = new Deadline(description, by);
            AddTask(t, true);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidDeadlineFormatException("Deadline format is incorrect. Use '/by' to specify deadline.");
        } catch (Exception e) { // whatever your util throws (e.g., ButtercupException)
            throw new InvalidDeadlineFormatException(
                    "Use '/by yyyy-MM-dd HHmm' or '/by d/M/yyyy HHmm' (e.g., 2019-12-02 1800 or 2/12/2019 1800)."
            );
        }


    }

    private void addEvent(String input) throws InvalidEventFormatException{
        try {
            String[] parts = input.substring(6).split("/from", 2);
            if (parts.length < 2) throw new InvalidEventFormatException("Please specify '/from'.");
            String description = parts[0].trim();

            String[] timeParts = parts[1].split("/to", 2);
            if (timeParts.length < 2) throw new InvalidEventFormatException("Please specify '/to'.");

            LocalDateTime from = DateTimeFormatUtils.getLocalDateTimeFromString(timeParts[0].trim());
            LocalDateTime to   = DateTimeFormatUtils.getLocalDateTimeFromString(timeParts[1].trim());

            Task t = new Event(description, from.toLocalTime(), to.toLocalTime()); // Event(LocalDateTime, LocalDateTime)
            AddTask(t, true);
        } catch (ShahzamExceptions e) {
            throw new InvalidEventFormatException(
                    "Use '/from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm' or '/from d/M/yyyy HHmm /to d/M/yyyy HHmm'."
            );
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidEventFormatException("Event format is incorrect. Use '/from' and '/to'.");
        }

    }

    private void deleteTask(String input) throws InvalidTaskNumberException {
        try {
            int idx = Integer.parseInt(input.substring(7).trim()); // 1-based index
            if (idx < 1 || idx > TaskList.size()) {
                throw new InvalidTaskNumberException("There is no task with that number.");
            }
            Task t = TaskList.remove(idx - 1); // Remove task at the index
            System.out.println("Noted. I've removed this task:\n  " + t);
            System.out.println("Now you have " + TaskList.size() + " tasks in your list.");
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new InvalidTaskNumberException("Please provide a valid task number.");
        }
    }
    private void PrintList(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }
    public static void main(String[] args) {
        new Shahzam().run();
    }
}
