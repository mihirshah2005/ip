package shahzam.task;

import shahzam.utils.DateTimeFormatUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a start time and an end time.
 * An Event task has a description, start time, and end time.
 */
public class Event extends Task {
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

    /**
     * Constructs a new Event task.
     *
     * @param description The description of the task.
     * @param fromTime    The starting time of the event.
     * @param toTime      The ending time of the event.
     */
    public Event(String description, LocalDateTime fromTime, LocalDateTime toTime) {
        super(description);
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        return "[E]" + super.toString() + " (from: " + DateTimeFormatUtils.formatDateTime(fromTime) + " to: "
                + DateTimeFormatUtils.formatDateTime(toTime) + ")";
    }
}
