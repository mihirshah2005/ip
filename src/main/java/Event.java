import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Event extends Task {
    private String time;
    private LocalTime fromTime;
    private LocalTime toTime;

    public Event(String description, LocalTime fromTime, LocalTime toTime) {
        super(description);
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        return "[E]" + super.toString() + " (from: " + fromTime.format(timeFormatter) + " to: " + toTime.format(timeFormatter) + ")";
    }
}
