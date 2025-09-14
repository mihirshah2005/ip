package shahzam.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import shahzam.utils.DateTimeFormatUtils;

public class DeadlineTest {

    private static LocalDateTime fixed(int y, int m, int d, int h, int min) {
        return LocalDateTime.of(y, m, d, h, min);
    }

    @Test
    public void constructor_andToString_notDone_success() {
        LocalDateTime dt = fixed(2025, 1, 2, 8, 5); // Jan 2 2025, 8:05 AM
        Deadline dl = new Deadline("test deadline", dt);

        String expected = "[D][ ] test deadline (by: " + DateTimeFormatUtils.formatDateTime(dt) + ")";
        assertEquals(expected, dl.toString());
    }

    @Test
    public void markDone_updatesStatusIconInToString() {
        LocalDateTime dt = fixed(2024, 12, 31, 23, 0);
        Deadline dl = new Deadline("party prep", dt);
        dl.MarkDone();

        String expected = "[D][X] party prep (by: " + DateTimeFormatUtils.formatDateTime(dt) + ")";
        assertEquals(expected, dl.toString());
    }

    @Test
    public void unmark_revertsStatusIconInToString() {
        LocalDateTime dt = fixed(2025, 3, 10, 14, 30);
        Deadline dl = new Deadline("submit report", dt);
        dl.MarkDone();
        dl.Unmark();

        String expected = "[D][ ] submit report (by: " + DateTimeFormatUtils.formatDateTime(dt) + ")";
        assertEquals(expected, dl.toString());
    }

    @Test
    public void unicodeDescription_preservedInToString() {
        LocalDateTime dt = fixed(2025, 2, 1, 9, 0);
        Deadline dl = new Deadline("fix 🚀 blockers", dt);

        String expected = "[D][ ] fix 🚀 blockers (by: " + DateTimeFormatUtils.formatDateTime(dt) + ")";
        assertEquals(expected, dl.toString());
    }

    @Test
    public void longDescription_preservedInToString() {
        LocalDateTime dt = fixed(2025, 5, 1, 9, 0);
        String longDesc = "x".repeat(500);
        Deadline dl = new Deadline(longDesc, dt);

        String expected = "[D][ ] " + longDesc + " (by: " + DateTimeFormatUtils.formatDateTime(dt) + ")";
        assertEquals(expected, dl.toString());
    }
}
