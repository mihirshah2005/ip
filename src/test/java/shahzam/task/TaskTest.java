package shahzam.task;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void newTask_defaultNotDone_toStringMatches() {
        Task t = new Task("read book");
        assertEquals("[ ] read book", t.toString());
        assertEquals(" ", t.getStatusIcon());
    }

    @Test
    public void markDone_setsStatusAndToString() {
        Task t = new Task("write code");
        t.MarkDone();
        assertEquals("[X] write code", t.toString());
        assertEquals("X", t.getStatusIcon());
    }

    @Test
    public void markDone_idempotent() {
        Task t = new Task("send email");
        t.MarkDone();
        String once = t.toString();
        t.MarkDone(); // again
        String twice = t.toString();
        assertEquals(once, twice);
    }

    @Test
    public void unmark_revertsStatus() {
        Task t = new Task("do chores");
        t.MarkDone();
        t.Unmark();
        assertEquals("[ ] do chores", t.toString());
        assertEquals(" ", t.getStatusIcon());
    }

    @Test
    public void matchDescription_isCaseSensitive_containsSubstring() {
        Task t = new Task("Finish the Report");
        assertTrue(t.matchDescription("Report"));   // exact case substring
        assertFalse(t.matchDescription("report"));  // case-sensitive -> false
        assertFalse(t.matchDescription("ReporT"));  // case-sensitive -> false
        assertFalse(t.matchDescription("NotThere"));
    }

    @Test
    public void matchDescriptionIgnoreCase_isCaseInsensitive() {
        Task t = new Task("Finish the Report");
        assertTrue(t.matchDescriptionIgnoreCase("report"));
        assertTrue(t.matchDescriptionIgnoreCase("REPORT"));
        assertTrue(t.matchDescriptionIgnoreCase("repORt"));
        assertFalse(t.matchDescriptionIgnoreCase("missing"));
    }

    @Test
    public void matchDescription_nullKeyword_throwsNpe() {
        Task t = new Task("anything");
        assertThrows(NullPointerException.class, () -> t.matchDescription(null));
    }

    @Test
    public void matchDescriptionIgnoreCase_nullKeyword_throwsNpe() {
        Task t = new Task("anything");
        assertThrows(NullPointerException.class, () -> t.matchDescriptionIgnoreCase(null));
    }
}
