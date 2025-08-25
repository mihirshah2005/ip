package shahzam.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class EventTest {
    @Test
    public void constructor_success() {
        LocalDate date = LocalDate.now();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(2);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

        Event event = new Event("test event",startTime, endTime);

        String expected = "[E][ ] test event (from: " + date.format(dateFormatter) + ", " +
                startTime.format(timeFormatter) + " to: " + date.format(dateFormatter) + ", " +
                endTime.format(timeFormatter) + ")";
        assertEquals(expected, event.toString());
    }
}