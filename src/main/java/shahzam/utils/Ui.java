package shahzam.utils;

import java.util.Scanner;

public class Ui {

    private final String WELCOME_MSG = "\nThe word was spoken, SHAHZAM awakens.\n"
            + "What can I do for you today?";
    private final String EXIT_MSG = "Thunder quiets. SHAHZAM signing off, until next time.";
    /*private final String logo =  "  ____  _           _                        \n" +
            " / ___|| |__   __ _| |__  ______ _ _ __ ___  \n" +
            " \\___ \\| '_ \\ / _` | '_ \\|_  / _` | '_ ` _ \\ \n" +
            "  ___) | | | | (_| | | | |/ / (_| | | | | | |\n" +
            " |____/|_| |_|\\__,_|_| |_/___\\__,_|_| |_| |_|\n" +
            "                                             ";

     */

    private final Scanner sc;

    public Ui() {
        sc = new Scanner(System.in);
        System.out.println(WELCOME_MSG);
    }

    public String readCommand() {
        return sc.nextLine().trim();
    }

    public void exit() {
        sc.close();
        System.out.println(EXIT_MSG);
    }
}
