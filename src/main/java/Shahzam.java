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
}
