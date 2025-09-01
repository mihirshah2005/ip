package shahzam;
import shahzam.exception.ShahzamExceptions;
import shahzam.task.TaskList;
import shahzam.utils.Parser;
import shahzam.utils.Storage;
import shahzam.utils.Ui;

import java.io.IOException;


public class Shahzam {

    private final String fileName = "data.txt";
    private TaskList taskList;
    private final Storage storage;
    private final Ui ui;

    public Shahzam(String fileName) {
        ui = new Ui();
        storage = new Storage(fileName);

        try {
            taskList = new TaskList(storage.load());
        } catch (ShahzamExceptions | IOException e) {
            System.out.println(e.getMessage());
            taskList = new TaskList();

        }

    }


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


    public String getResponse(String command) {
        // Exit is handled separately
        if (command.equals("bye")) {
            return "Thunder quiets. SHAHZAM signing off, until next time.";
        }

        String message;
        try {
            // Interpret and execute the command by user
            message = Parser.interpretCommand(command).execute(taskList);

            // Update storage file
            storage.save(taskList.getTasks());
        } catch (ShahzamExceptions | IOException e) {
            message = e.getMessage();
        }

        return message;
    }
}
