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

    private final ArrayList<String> TaskList = new ArrayList<>();


    public void run() {
        String input;
        System.out.println(logo + WELCOME_MSG);

        Scanner sc = new Scanner(System.in);
        while (!(input = sc.nextLine()).equals("bye")) {

            if (input.equals("list")) {
                for (int i = 0; i < TaskList.size(); i++) {
                    System.out.println((i+1) + ". " + TaskList.get(i));
                }
            } else {
                TaskList.add(input);
                System.out.println("added: " + input);
            }
        }
        sc.close();

        System.out.println(EXIT_MSG);
    }

    public static void main(String[] args) {
        new Shahzam().run();
    }
}
