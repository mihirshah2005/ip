import java.util.Scanner;
import java.util.ArrayList;

public class Shahzam {

    private final String WELCOME_MSG = "\nThe word was spoken, SHAHZAM awakens.\n" + "What can I do for you today?";
    private final String EXIT_MSG = "Thunder quiets. SHAHZAM signing off, until next time.";
    private final String logo =  "  ____  _           _                        \n" +
            " / ___|| |__   __ _| |__  ______ _ _ __ ___  \n" +
            " \\___ \\| '_ \\ / _` | '_ \\|_  / _` | '_ ` _ \\ \n" +
            "  ___) | | | | (_| | | | |/ / (_| | | | | | |\n" +
            " |____/|_| |_|\\__,_|_| |_/___\\__,_|_| |_| |_|\n" +
            "                                             ";

    private final ArrayList<Task> TaskList = new ArrayList<>();


    public void run() {
        System.out.println(logo + WELCOME_MSG);

        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine().trim();

            try {
                if (input.equals("bye")){
                    break;
                }

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
                } else {
                    throw new UnknownCommandException();
                }
            } catch ( UnknownCommandException | EmptyTaskDescriptionException | InvalidTaskNumberException | InvalidDeadlineFormatException | InvalidEventFormatException e) {
                System.out.println(" OOPS! " + e.getMessage());
            }

        }
        sc.close();

        System.out.println(EXIT_MSG);
    }

    private void AddTask(String input) throws EmptyTaskDescriptionException {
        if (input.isEmpty()) {
            throw new EmptyTaskDescriptionException("The description of a task cannot be empty.");
        } else {
            Task t = new Task(input);
            TaskList.add(t);
            System.out.println("added: " + input);
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
            TaskList.add(t);
            System.out.println("Got it. I've added this task:\n  " + t);
            System.out.println("Now you have " + TaskList.toArray().length + " tasks in your list.");
        }
    }

    private void addDeadline(String input) throws InvalidDeadlineFormatException{
        try {
            String[] time_str = input.substring(9).split("/by");
            if (time_str.length < 2) {
                throw new InvalidDeadlineFormatException("Please specify a deadline with the /by keyword.");
            }

            Task t = new Deadline(time_str[0].trim(), time_str[1].trim());
            TaskList.add(t);
            System.out.println("Got it. I've added this task:\n  " + t);
            System.out.println("Now you have " + TaskList.toArray().length + " tasks in your list.");
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidDeadlineFormatException("Deadline format is incorrect. Use '/by' to specify deadline.");
        }

    }

    private void addEvent(String input) throws InvalidEventFormatException{
        try {
            String[] parts = input.substring(6).split("/from");
            if (parts.length < 2) {
                throw new InvalidEventFormatException("Please specify an event with '/from' keyword.");
            }

            String description = parts[0].trim();

            String[] timeParts = parts[1].split("/to");
            if (timeParts.length < 2) {
                throw new InvalidEventFormatException("Please specify both /from and /to time for the event.");
            }

            String fromTime = timeParts[0].trim();
            String toTime = timeParts[1].trim();

            Task t = new Event(description, fromTime, toTime);
            TaskList.add(t);
            System.out.println("Got it. I've added this task:\n  " + t);
            System.out.println("Now you have " + TaskList.size() + " tasks in your list." );
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidEventFormatException("Event format is incorrect. Use '/from' and '/to' to specify time.");
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
