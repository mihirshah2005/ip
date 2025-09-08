package shahzam;
import shahzam.exception.ShahzamExceptions;
import shahzam.task.TaskList;
import shahzam.utils.Parser;
import shahzam.utils.Storage;
import shahzam.utils.Ui;

import java.io.IOException;

/**
 * Main class representing the SHAHZAM application.
 * This class handles the application lifecycle, including loading tasks, interpreting user commands,
 * and saving tasks to a file. It interacts with the user through the UI and manages tasks through
 * the TaskList, Storage, and Parser utilities.
 */
public class Shahzam {

    private final String FILE_NAME = "data.txt";
    private TaskList taskList;
    private final Storage storage;
    private final Ui ui;

    /**
     * Constructs a Shahzam application with the specified file name for data storage.
     * Initializes the UI, Storage, and TaskList objects. Attempts to load tasks from the specified
     * file, and creates a new task list if an error occurs during loading.
     *
     * @param FILE_NAME The name of the file where task data is stored.
     */
    public Shahzam(String FILE_NAME) {
        ui = new Ui();
        storage = new Storage(FILE_NAME);

        try {
            taskList = new TaskList(storage.load());
        } catch (ShahzamExceptions | IOException e) {
            System.out.println(e.getMessage());
            taskList = new TaskList();

        }

    }


    /**
     * Runs the SHAHZAM application, continuously accepting user commands until the user exits.
     * Interprets each command and performs the corresponding action on the TaskList. Saves the task list
     * to the file after each modification. The program terminates when the user types "bye".
     */
    public void run() {
        String input;

        while (!(input = ui.readCommand()).equals("bye")) {
            try {
                String message = Parser.interpretCommand(input).execute(taskList);
                System.out.println(message);
                storage.save(taskList.getTasks());
            } catch (ShahzamExceptions | IOException e) {
                System.out.println(e.getMessage());
            }

        }
        ui.exit();

    }

    public static void main(String[] args) {
        new Shahzam("data.txt").run();
    }


    /**
     * Processes a user command and returns a corresponding response message.
     * This method interprets the command, executes the appropriate action on the task list,
     * and returns a message to the user. If the command is invalid or an error occurs, an error message is returned.
     *
     * @param command The user input command.
     * @return The response message based on the command execution result.
     */
    public String getResponse(String command) {
        if (command.equals("bye")) {
            return "Thunder quiets. SHAHZAM signing off, until next time.";
        }

        String message;
        try {

            message = Parser.interpretCommand(command).execute(taskList);

            storage.save(taskList.getTasks());
        } catch (ShahzamExceptions | IOException e) {
            message = e.getMessage();
        }

        return message;
    }
}
