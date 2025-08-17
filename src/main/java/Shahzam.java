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

            if (input.equals("bye")){
                break;
            }

            if (input.equals("list")) {
                PrintList(TaskList);
            } else if (input.startsWith("mark ")) {
                TaskDone(input);
            } else if (!input.isEmpty()) {
                AddTask(input);
            }
        }
        sc.close();

        System.out.println(EXIT_MSG);
    }

    private void AddTask(String input) {
        Task t = new Task(input);
        TaskList.add(t);
        System.out.println("added: " + input);
    }

    private void TaskDone(String input) {
        int idx = Integer.parseInt(input.substring(5).trim()); // 1-based
        Task t = TaskList.get(idx - 1);
        t.MarkDone();
        System.out.println("Nice! I've marked this task as done:\n  " + t);
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
