package shahzam.task;

/**
 * Represents a To-Do task that extends from the Task class.
 * This class is used to model tasks that have a description but are not yet marked as completed.
 * The ToDo task type is represented by the "[T]" symbol when printed.
 */
public class ToDo extends Task {

    /**
     * Constructs a ToDo task with the specified description.
     *
     * @param description The description of the task.
     */
    public ToDo(String description) {

        super(description);
    }

    @Override
    public String toString() {

        return "[T]" + super.toString();
    }
}