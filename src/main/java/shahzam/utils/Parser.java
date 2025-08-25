package shahzam.utils;
import shahzam.exception.ShahzamExceptions;
import shahzam.exception.UnknownCommandException;
import shahzam.task.TaskList;

public class Parser {

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
        } else if (input.startsWith("deadline ")) {
            return (TaskList) -> TaskList.addDeadline(input);
        } else if (input.startsWith("delete ")) {
            return (TaskList) -> TaskList.deleteTask(input);
        } else {
            throw new UnknownCommandException();
        }
    }


    public interface CheckedFunction {
        String execute(TaskList newlist) throws ShahzamExceptions;
    }
}
