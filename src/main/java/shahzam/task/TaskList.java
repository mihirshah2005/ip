package shahzam.task;

import shahzam.exception.InvalidDeadlineFormatException;
import shahzam.exception.InvalidEventFormatException;
import shahzam.exception.InvalidTaskNumberException;
import shahzam.exception.IllegalFormatException;
import shahzam.exception.ShahzamExceptions;
import shahzam.utils.DateTimeFormatUtils;

import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 * Represents a list of tasks.
 * Provides methods to add, mark, unmark, delete, and display tasks.
 */
public class TaskList {

    private ArrayList<Task> TaskList = new ArrayList<>();

    public TaskList() {
        this(new ArrayList<>());
    }

    /**
     * Constructs a TaskList with a specified list of tasks.
     *
     * @param TaskList The list of tasks to initialize the TaskList with.
     */
    public TaskList(ArrayList<Task> TaskList) {
        this.TaskList = TaskList;
    }

    /**
     * Returns the list of tasks in the TaskList.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return TaskList;
    }

    /**
     * Adds a new task to the TaskList.
     *
     * @param newTask The task to be added.
     * @param showMsg Whether to show a message after adding the task.
     * @return A message indicating the task has been added.
     */
    public String AddTask(Task newTask, boolean showMsg) {
        TaskList.add(newTask);
        if (showMsg) {
            return "Alright, new task added to the list:" + "\n  " +
                    newTask + "\n" +
                    "Now you have " + TaskList.size() + " task(s) in the list.";
        }
        return null;

    }

    /**
     * Marks a task as done based on its index.
     *
     * @param input The input string containing the task index to mark as done.
     * @return A message indicating the task has been marked as done.
     * @throws InvalidTaskNumberException If the task number is invalid.
     */
    public String TaskDone(String input) throws InvalidTaskNumberException {
        try {
            int idx = Integer.parseInt(input.substring(5).trim());
            if (idx < 1 || idx > TaskList.size()) {
                throw new InvalidTaskNumberException("There is no task with that number.");
            }

            Task t = TaskList.get(idx - 1);
            t.MarkDone();
            return "Nice! I've marked this task as done:\n  " + t;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new InvalidTaskNumberException("Please provide a valid task number.");
        }

    }


    /**
     * Unmarks a task as done based on its index.
     *
     * @param input The input string containing the task index to unmark.
     * @return A message indicating the task has been marked as not done.
     * @throws InvalidTaskNumberException If the task number is invalid.
     */
    public String TaskUnmark(String input) throws InvalidTaskNumberException{
        try {
            int idx = Integer.parseInt(input.substring(7).trim());
            if (idx < 1 || idx > TaskList.size()) {
                throw new InvalidTaskNumberException("There is no task with that number.");
            }
            Task t = TaskList.get(idx - 1);
            t.UnmarkDone();
            return "OK, I've marked this task as not done yet:\n  " + t;
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new InvalidTaskNumberException("Please provide a valid task number.");
        }

    }

    /**
     * Adds a ToDo task to the TaskList.
     *
     * @param input The input string containing the description of the task.
     * @return A message indicating the task has been added.
     * @throws IllegalFormatException If the input format is invalid.
     */
    public String addToDo(String input) throws IllegalFormatException{
        validateCommand(input, "^todo .*", "todo [description]");
        Task newTask = new ToDo(input.substring(5).trim());

        TaskList.add(newTask);
        return formatAddMessage(newTask, TaskList.size());
    }

    /**
     * Adds a Deadline task to the TaskList.
     *
     * @param input The input string containing the description and deadline.
     * @return A message indicating the task has been added.
     * @throws InvalidDeadlineFormatException If the deadline format is invalid.
     */

    public String addDeadline(String input) throws InvalidDeadlineFormatException{
        try {
            String[] parts = input.substring(9).split("/by", 2);
            if (parts.length < 2) {
                throw new InvalidDeadlineFormatException("Please specify a deadline with the /by keyword.");
            }

            String description = parts[0].trim();
            String byRaw       = parts[1].trim();

            LocalDateTime by = DateTimeFormatUtils.getLocalDateTimeFromString(byRaw);

            Task t = new Deadline(description, by);
            return AddTask(t, true);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidDeadlineFormatException(
                    "shahzam.task.Deadline format is incorrect. Use '/by' to specify deadline.");
        } catch (Exception e) {
            throw new InvalidDeadlineFormatException(
                    "Use '/by yyyy-MM-dd HHmm' or '/by d/M/yyyy HHmm' (e.g., 2019-12-02 1800 or 2/12/2019 1800)."
            );
        }


    }

    /**
     * Adds an Event task to the TaskList.
     *
     * @param input The input string containing the description, start time, and end time.
     * @return A message indicating the task has been added.
     * @throws InvalidEventFormatException If the event format is invalid.
     */
    public String addEvent(String input) throws InvalidEventFormatException {
        try {
            String[] parts = input.substring(6).split("/from", 2);
            if (parts.length < 2) throw new InvalidEventFormatException("Please specify '/from'.");
            String description = parts[0].trim();

            String[] timeParts = parts[1].split("/to", 2);
            if (timeParts.length < 2) throw new InvalidEventFormatException("Please specify '/to'.");

            LocalDateTime from = DateTimeFormatUtils.getLocalDateTimeFromString(timeParts[0].trim());
            LocalDateTime to   = DateTimeFormatUtils.getLocalDateTimeFromString(timeParts[1].trim());

            Task t = new Event(description, from, to);
            return AddTask(t, true);
        } catch (ShahzamExceptions e) {
            throw new InvalidEventFormatException(
                    "Use '/from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm' or '/from d/M/yyyy HHmm /to d/M/yyyy HHmm'."
            );
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidEventFormatException("shahzam.task.Event format is incorrect. Use '/from' and '/to'.");
        }

    }

    /**
     * Deletes a task from the TaskList based on its index.
     *
     * @param input The input string containing the task index to delete.
     * @return A message indicating the task has been removed.
     * @throws InvalidTaskNumberException If the task number is invalid.
     */
    public String deleteTask(String input) throws InvalidTaskNumberException {
        try {
            int idx = Integer.parseInt(input.substring(7).trim()); // 1-based index
            if (idx < 1 || idx > TaskList.size()) {
                throw new InvalidTaskNumberException("There is no task with that number.");
            }
            Task t = TaskList.remove(idx - 1); // Remove task at the index
            return "Noted. I've removed this task:\n  " + t + "\n"
                    + "Now you have " + TaskList.size() + " tasks in your list.";
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new InvalidTaskNumberException("Please provide a valid task number.");
        }
    }
    public String PrintList() {
        int size = TaskList.size();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size - 1; i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(TaskList.get(i))
                    .append("\n");
        }

        sb.append(size)
                .append(". ")
                .append(TaskList.get(size - 1));

        return "Here are the tasks in your list: \n" + sb;
    }

    /**
     * Validates the format of a command using regular expressions.
     *
     * @param command The command string to validate.
     * @param regex   The regular expression to check against.
     * @param format  The expected format description.
     * @throws IllegalFormatException If the command doesn't match the format.
     */
    private void validateCommand(String command, String regex, String format) throws IllegalFormatException {
        if (!command.matches(regex)) {
            throw new IllegalFormatException(format);
        }
    }

    private String formatAddMessage(Task task, int size) {
        return "Alright, new task added to the list:" + "\n  " +
                task + "\n" +
                "Now you have " + size + " task" + (size == 1 ? "" : "s") + " in the list.";
    }

}
