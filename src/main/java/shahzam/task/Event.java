package shahzam.task;

import shahzam.utils.DateTimeFormatUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Event extends Task {
    private LocalDateTime fromTime;
    private LocalDateTime toTime;

    public Event(String description, LocalDateTime fromTime, LocalDateTime toTime) {
        super(description);
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        return "[E]" + super.toString() + " (from: " + DateTimeFormatUtils.formatDateTime(fromTime) + " to: " + DateTimeFormatUtils.formatDateTime(toTime) + ")";
    }
}
