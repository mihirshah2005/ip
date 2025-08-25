package shahzam.task;

import shahzam.utils.DateTimeFormatUtils;

import java.time.LocalDateTime;

/**
 * Represents a task with a deadline.
 * A Deadline task has a description and a due date and time.
 */
public class Deadline extends Task {
    private final LocalDateTime time;

    /**
     * Constructs a new Deadline task.
     *
     * @param description The description of the task.
     * @param time        The time by which the task must be completed.
     */
    public Deadline(String description, LocalDateTime time) {
        super(description);
        this.time = time;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeFormatUtils.formatDateTime(time) + ")";
    }

}
