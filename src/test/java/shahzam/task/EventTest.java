package shahzam.task;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import shahzam.utils.DateTimeFormatUtils;

public class EventTest {

    private static LocalDateTime fixed(int y, int m, int d, int h, int min) {
        return LocalDateTime.of(y, m, d, h, min);
    }

    @Test
    public void toString_notDone_singleDay_success() {
        LocalDateTime from = fixed(2025, 1, 2, 8, 5);  // Jan 2 2025, 8:05
        LocalDateTime to   = fixed(2025, 1, 2, 10, 0); // Jan 2 2025, 10:00
        Event ev = new Event("standup", from, to);

        String expected = "[E][ ] standup (from: " + DateTimeFormatUtils.formatDateTime(from)
                + " to: " + DateTimeFormatUtils.formatDateTime(to) + ")";
        assertEquals(expected, ev.toString());
    }

    @Test
    public void markDone_updatesStatusIcon() {
        LocalDateTime from = fixed(2024, 12, 31, 23, 0);
        LocalDateTime to   = fixed(2025, 1, 1, 1, 0);
        Event ev = new Event("new year party", from, to);

        ev.MarkDone();

        String expected = "[E][X] new year party (from: " + DateTimeFormatUtils.formatDateTime(from)
                + " to: " + DateTimeFormatUtils.formatDateTime(to) + ")";
        assertEquals(expected, ev.toString());
    }

    @Test
    public void unmark_revertsStatusIcon() {
        LocalDateTime from = fixed(2025, 3, 10, 14, 30);
        LocalDateTime to   = fixed(2025, 3, 10, 16, 0);
        Event ev = new Event("sprint review", from, to);

        ev.MarkDone();
        ev.Unmark();

        String expected = "[E][ ] sprint review (from: " + DateTimeFormatUtils.formatDateTime(from)
                + " to: " + DateTimeFormatUtils.formatDateTime(to) + ")";
        assertEquals(expected, ev.toString());
    }

    @Test
    public void markDone_idempotent() {
        LocalDateTime from = fixed(2025, 2, 14, 18, 45);
        LocalDateTime to   = fixed(2025, 2, 14, 20, 0);
        Event ev = new Event("date night", from, to);

        ev.MarkDone();
        String once = ev.toString();
        ev.MarkDone(); // again
        String twice = ev.toString();

        assertEquals(once, twice);
        assertTrue(twice.startsWith("[E][X]"));
    }

    @Test
    public void sameStartAndEndTime_allowedAndFormatted() {
        LocalDateTime t = fixed(2025, 5, 1, 9, 0);
        Event ev = new Event("sync", t, t);

        String expected = "[E][ ] sync (from: " + DateTimeFormatUtils.formatDateTime(t)
                + " to: " + DateTimeFormatUtils.formatDateTime(t) + ")";
        assertEquals(expected, ev.toString());
    }

    @Test
    public void crossDayRange_bothDatesShown() {
        LocalDateTime from = fixed(2025, 7, 31, 23, 30);
        LocalDateTime to   = fixed(2025, 8, 1, 0, 30);
        Event ev = new Event("deployment", from, to);

        String s = ev.toString();
        assertTrue(s.contains(DateTimeFormatUtils.formatDateTime(from)));
        assertTrue(s.contains(DateTimeFormatUtils.formatDateTime(to)));
        assertTrue(s.startsWith("[E][ ]"));
    }

    @Test
    public void unicodeDescription_preserved() {
        LocalDateTime from = fixed(2025, 4, 1, 12, 0);
        LocalDateTime to   = fixed(2025, 4, 1, 13, 0);
        Event ev = new Event("fix 🚀 blockers", from, to);

        String expected = "[E][ ] fix 🚀 blockers (from: " + DateTimeFormatUtils.formatDateTime(from)
                + " to: " + DateTimeFormatUtils.formatDateTime(to) + ")";
        assertEquals(expected, ev.toString());
    }

    @Test
    public void longDescription_preserved() {
        LocalDateTime from = fixed(2025, 6, 10, 9, 0);
        LocalDateTime to   = fixed(2025, 6, 10, 17, 0);
        String longDesc = "x".repeat(600);
        Event ev = new Event(longDesc, from, to);

        String expected = "[E][ ] " + longDesc + " (from: " + DateTimeFormatUtils.formatDateTime(from)
                + " to: " + DateTimeFormatUtils.formatDateTime(to) + ")";
        assertEquals(expected, ev.toString());
    }
}
