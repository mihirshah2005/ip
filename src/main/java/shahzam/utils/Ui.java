package shahzam.utils;

import java.util.Scanner;

/**
 * Represents the User Interface (UI) of the SHAHZAM application.
 * The UI is responsible for interacting with the user, displaying messages,
 * reading commands from the user, and providing feedback when the user exits.
 */
public class Ui {

    private final String WELCOME_MSG = "\nThe word was spoken, SHAHZAM awakens.\n"
            + "What can I do for you today?";
    private final String EXIT_MSG = "Thunder quiets. SHAHZAM signing off, until next time.";

    private final Scanner sc;

    /**
     * Constructs a Ui object and prints the welcome message.
     * Initializes the Scanner to read user input from the console.
     */
    public Ui() {
        sc = new Scanner(System.in);
    }

    /**
     * Reads the next command entered by the user.
     * This method waits for the user to enter a command and returns it as a trimmed string.
     *
     * @return The user's input command as a string, with leading and trailing spaces removed.
     */
    public String readCommand() {

        return sc.nextLine().trim();
    }

    /**
     * Closes the Scanner and prints the exit message.
     * This method is called when the user wants to exit the application.
     */
    public void exit() {
        sc.close();
        System.out.println(EXIT_MSG);
    }
}
