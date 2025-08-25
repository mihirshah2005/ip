package shahzam.task;


/**
 * Represents a generic task.
 * A task has a description and a status (done or not done).
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Constructs a new Task.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void MarkDone() {
        this.isDone = true;
    }

    /**
     * Returns true if the task is marked as done.
     *
     * @return True if the task is done, false otherwise.
     */
    public boolean isDone() {
        return this.isDone;
    }

    public boolean matchDescription(String keyword) {
        return description.contains(keyword);
    }

    public void UnmarkDone() {
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task (either "X" or " ").
     *
     * @return The status icon of the task.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + description;
    }
}
