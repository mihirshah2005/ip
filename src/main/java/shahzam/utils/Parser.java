package shahzam.utils;
import shahzam.exception.ShahzamExceptions;
import shahzam.exception.UnknownCommandException;
import shahzam.task.TaskList;

/**
 * Utility class for parsing user input and interpreting commands.
 * This class is responsible for determining which command the user intends to execute
 * and returning the corresponding function that will perform the desired action on the TaskList.
 */
public class Parser {

    /**
     * Interprets the user input and returns the corresponding function to be executed on the TaskList.
     * The function determines the action based on the command and delegates the execution to the
     * appropriate method in the TaskList class.
     *
     * @param input The user input, which represents a command.
     * @return A CheckedFunction that executes the corresponding TaskList method based on the input.
     * @throws ShahzamExceptions If the input does not match any known command.
     * @throws UnknownCommandException If the command is unrecognized.
     */
    public static CheckedFunction interpretCommand(String input) throws ShahzamExceptions {
        if (input.equals("list")) {
            return TaskList::PrintList;
        } else if (input.startsWith("mark ")) {
            return (TaskList) -> TaskList.TaskDone(input);
        } else if (input.startsWith("unmark ")) {
            return (TaskList) -> TaskList.TaskUnmark(input);
        } else if (input.startsWith("todo ")) {
            return (TaskList) -> TaskList.addToDo(input);
        } else if (input.startsWith("event ")) {
            return (TaskList) -> TaskList.addEvent(input);
        } else if (input.startsWith("find ")) {
            return (TaskList) -> TaskList.findInList(input);
        } else if (input.startsWith("deadline ")) {
            return (TaskList) -> TaskList.addDeadline(input);
        } else if (input.startsWith("delete ")) {
            return (TaskList) -> TaskList.deleteTask(input);
        } else {
            throw new UnknownCommandException();
        }
    }


    /**
     * Functional interface representing a function that executes an action on the TaskList.
     * The function takes a TaskList object and returns a string result, possibly throwing
     * a ShahzamExceptions.
     */
    public interface CheckedFunction {
        String execute(TaskList newlist) throws ShahzamExceptions;
    }
}
