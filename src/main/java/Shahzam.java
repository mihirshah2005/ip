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


    /*private final String logo =  "  ____  _           _                        \n" +
            " / ___|| |__   __ _| |__  ______ _ _ __ ___  \n" +
            " \\___ \\| '_ \\ / _` | '_ \\|_  / _` | '_ ` _ \\ \n" +
            "  ___) | | | | (_| | | | |/ / (_| | | | | | |\n" +
            " |____/|_| |_|\\__,_|_| |_/___\\__,_|_| |_| |_|\n" +
            "                                             ";

     */


    private final String fileName = "data.txt";
    private TaskList taskList;


    public void run() {
        //System.out.println(logo + WELCOME_MSG);
        System.out.println("WELCOME_MSG");

//        try {
//            loadData();
//        } catch (ShahzamExceptions | IOException e) {
//            System.out.println(e.getMessage());
//        }

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine().trim();

            try {
                if (input.equals("bye")){
                    break;
                }

                Parser.interpretCommand(input);

                // Update storage file
                //saveData();

            } catch ( ShahzamExceptions  e) {
                System.out.println(" OOPS! " + e.getMessage());
            }

        }
        sc.close();

        System.out.println("EXIT_MSG");
    }

//    private void interpretCommand(String input) throws ShahzamExceptions {
//        // Each if else handles one type of command
//            if (input.equals("list")) {
//                PrintList(TaskList);
//            } else if (input.startsWith("mark ")) {
//                TaskDone(input);
//            } else if (input.startsWith("unmark ")) {
//                TaskUnmark(input);
//            } else if (input.startsWith("todo ")) {
//                addToDo(input);
//            } else if (input.startsWith("event ")) {
//                addEvent(input);
//            } else if (input.startsWith("deadline ")) {
//                addDeadline(input);
//            } else if (input.startsWith("delete ")) {
//                deleteTask(input);
//            } else {
//                throw new UnknownCommandException();
//            }
//        }

//    private void saveData() throws IOException {
//        // Reformat the list of tasks for storage
//        StringBuilder output = new StringBuilder();
//        TaskList.forEach(task -> output.append(task.toString()).append("\n"));
//
//        FileWriter fw = new FileWriter(fileName, false);
//        fw.write(output.toString());
//        fw.close();
//    }
//
//    private void loadData() throws DataIntegrityException, IOException {
//        // Initialize save file and create parent directory
//        File file = new File(fileName);
//        if (file.createNewFile()) {
//            return;
//        }
//        String input;
//        BufferedReader br = new BufferedReader(new FileReader(fileName));
//
//        // Read the rest of data and add to list of tasks
//        while ((input = br.readLine()) != null) {
//            Task newTask;
//            String description;
//            String time;
//
//            // Obtain relevant info based on type of task
//            switch (input.charAt(1)) {
//            case 'T': // todo
//                newTask = new ToDo(input.substring(7));
//                break;
//            case 'D': { // [D][ ] desc (by: <datetime>)
//                description = input.substring(7, input.indexOf(" ("));
//                String byStr = input.substring(input.indexOf("(by: ") + 5, input.length() - 1);
//
//                LocalDateTime by = parseStoredDateTime(byStr);
//                // If Deadline constructor expects LocalDateTime:
//                newTask = new Deadline(description, by);
//                // If yours still takes a String that it parses internally, you can instead pass byStr.
//                break;
//            }
//
//            case 'E': { // [E][ ] desc (from: <datetime> to: <datetime>)
//                description = input.substring(7, input.indexOf(" ("));
//
//                int fromStart = input.indexOf("(from: ") + 7;
//                int toSep     = input.indexOf(" to: ", fromStart);
//                int endParen  = input.lastIndexOf(')');
//                if (fromStart < 7 || toSep == -1 || endParen == -1) {
//                    throw new DataIntegrityException();
//                }
//
//                String fromStr = input.substring(fromStart, toSep).trim();
//                String toStr   = input.substring(toSep + 5, endParen).trim();
//
//                LocalDateTime from = parseStoredDateTime(fromStr);
//                LocalDateTime to   = parseStoredDateTime(toStr);
//
//                // If Event constructor expects LocalDateTime:
//                newTask = new Event(description, from, to);
//                // If yours expects LocalDate + LocalTime:
//                // newTask = new Event(description, from.toLocalDate(), from.toLocalTime(), to.toLocalTime());
//                break;
//            }
//            default:
//                throw new DataIntegrityException();
//            }
//            if (input.charAt(4) == 'X') {
//                newTask.MarkDone();
//            }
//
//            // Add task to list but do not show a confirmation msg
//            AddTask(newTask, false);
//        }
//
//        // Close reader
//        br.close();
//    }

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

    public static void main(String[] args) {
        new Shahzam().run();
    }
}
