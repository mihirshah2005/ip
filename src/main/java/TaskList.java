import java.util.ArrayList;
import java.util.Arrays;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TaskList {



    private ArrayList<Task> TaskList = new ArrayList<>();

    public TaskList() {
        this(new ArrayList<>());
    }

    /**
     * Overloading constructor for TaskList. Allows user to input a list of tasks.
     *
     * @param tasks an existing list of tasks
     */
    public TaskList(ArrayList<Task> TaskList) {
        this.TaskList = TaskList;
    }

    public ArrayList<Task> getTasks() {
        return TaskList;
    }

    public String AddTask(Task newTask, boolean showMsg) {
        TaskList.add(newTask);
        if (showMsg) {
            return "Alright, new task added to the list:" + "\n  " +
                    newTask + "\n" +
                    "Now you have " + TaskList.size() + " task(s) in the list.";
        }
        return null;

    }

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

    public String addToDo(String input) throws IllegalFormatException{
        validateCommand(input, "^todo .*", "todo [description]");

        // Add new todo
        Task newTask = new ToDo(input.substring(5).trim());

        // Add the newly created task into list of tasks
        TaskList.add(newTask);

        // Return confirmation message
        return formatAddMessage(newTask, TaskList.size());
    }

    public String addDeadline(String input) throws InvalidDeadlineFormatException{
        try {
            String[] parts = input.substring(9).split("/by", 2);
            if (parts.length < 2) {
                throw new InvalidDeadlineFormatException("Please specify a deadline with the /by keyword.");
            }

            String description = parts[0].trim();
            String byRaw       = parts[1].trim();

            // parse full datetime using your util (Option A)
            LocalDateTime by = DateTimeFormatUtils.getLocalDateTimeFromString(byRaw);

            Task t = new Deadline(description, by);
            return AddTask(t, true);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidDeadlineFormatException("Deadline format is incorrect. Use '/by' to specify deadline.");
        } catch (Exception e) { // whatever your util throws (e.g., ButtercupException)
            throw new InvalidDeadlineFormatException(
                    "Use '/by yyyy-MM-dd HHmm' or '/by d/M/yyyy HHmm' (e.g., 2019-12-02 1800 or 2/12/2019 1800)."
            );
        }


    }

    public String addEvent(String input) throws InvalidEventFormatException{
        try {
            String[] parts = input.substring(6).split("/from", 2);
            if (parts.length < 2) throw new InvalidEventFormatException("Please specify '/from'.");
            String description = parts[0].trim();

            String[] timeParts = parts[1].split("/to", 2);
            if (timeParts.length < 2) throw new InvalidEventFormatException("Please specify '/to'.");

            LocalDateTime from = DateTimeFormatUtils.getLocalDateTimeFromString(timeParts[0].trim());
            LocalDateTime to   = DateTimeFormatUtils.getLocalDateTimeFromString(timeParts[1].trim());

            Task t = new Event(description, from, to); // Event(LocalDateTime, LocalDateTime)
            return AddTask(t, true);
        } catch (ShahzamExceptions e) {
            throw new InvalidEventFormatException(
                    "Use '/from yyyy-MM-dd HHmm /to yyyy-MM-dd HHmm' or '/from d/M/yyyy HHmm /to d/M/yyyy HHmm'."
            );
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidEventFormatException("Event format is incorrect. Use '/from' and '/to'.");
        }

    }

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

        // Last task is special as it does not need the '\n'
        sb.append(size)
                .append(". ")
                .append(TaskList.get(size - 1));

        return "Here are the tasks in your list:" + sb.toString();
    }

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
