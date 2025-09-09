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
     * Checks if the description contains the specified keyword.
     *
     * @param keyword The keyword to search for in the description.
     * @return true if the description contains the keyword, false otherwise.
     */
    public boolean matchDescription(String keyword) {

        return description.contains(keyword);
    }

    /**
     * Marks the current item as not done.
     * This method sets the {@code isDone} flag to false, indicating that the item is not completed.
     */
    public void Unmark() {

        this.isDone = false;
    }

    /**
     * Returns the status icon of the task (either "X" or " ").
     *
     * @return The status icon of the task.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Checks if the task's description contains the given keyword, ignoring case.
     *
     * @param keyword The keyword to search for in the description. It will be matched in a case-insensitive manner.
     * @return true if the task's description contains the keyword, false otherwise.
     */
    public boolean matchDescriptionIgnoreCase(String keyword) {
        return description != null && description.toLowerCase().contains(keyword.toLowerCase());
    }


    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + description;
    }
}
