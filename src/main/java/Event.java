import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Event extends Task {
    private String time;
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
