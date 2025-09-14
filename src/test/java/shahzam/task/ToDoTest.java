package shahzam.task;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ToDoTest {

    @Test
    public void newToDo_defaultNotDone_toStringMatches() {
        ToDo td = new ToDo("read book");
        assertEquals("[T][ ] read book", td.toString());
        assertEquals(" ", td.getStatusIcon());
    }

    @Test
    public void markDone_setsStatusAndToString() {
        ToDo td = new ToDo("write code");
        td.MarkDone();
        assertEquals("[T][X] write code", td.toString());
        assertEquals("X", td.getStatusIcon());
    }

    @Test
    public void markDone_idempotent() {
        ToDo td = new ToDo("send email");
        td.MarkDone();
        String once = td.toString();
        td.MarkDone(); // again
        String twice = td.toString();
        assertEquals(once, twice);
    }

    @Test
    public void unmark_revertsStatus() {
        ToDo td = new ToDo("do chores");
        td.MarkDone();
        td.Unmark();
        assertEquals("[T][ ] do chores", td.toString());
        assertEquals(" ", td.getStatusIcon());
    }

    @Test
    public void matchDescription_isCaseSensitive_containsSubstring() {
        ToDo td = new ToDo("Finish the Report");
        assertTrue(td.matchDescription("Report"));   // exact case substring
        assertFalse(td.matchDescription("report"));  // case-sensitive -> false
        assertFalse(td.matchDescription("ReporT"));  // case-sensitive -> false
        assertFalse(td.matchDescription("NotThere"));
    }

    @Test
    public void matchDescriptionIgnoreCase_isCaseInsensitive() {
        ToDo td = new ToDo("Finish the Report");
        assertTrue(td.matchDescriptionIgnoreCase("report"));
        assertTrue(td.matchDescriptionIgnoreCase("REPORT"));
        assertTrue(td.matchDescriptionIgnoreCase("repORt"));
        assertFalse(td.matchDescriptionIgnoreCase("missing"));
    }

    @Test
    public void unicodeDescription_preservedInToString() {
        ToDo td = new ToDo("fix 🚀 blockers");
        assertEquals("[T][ ] fix 🚀 blockers", td.toString());
    }

    @Test
    public void longDescription_preservedInToString() {
        String longDesc = "x".repeat(600);
        ToDo td = new ToDo(longDesc);
        assertEquals("[T][ ] " + longDesc, td.toString());
    }

    @Test
    public void matchDescription_nullKeyword_throwsNpe() {
        ToDo td = new ToDo("anything");
        assertThrows(NullPointerException.class, () -> td.matchDescription(null));
    }

    @Test
    public void matchDescriptionIgnoreCase_nullKeyword_throwsNpe() {
        ToDo td = new ToDo("anything");
        assertThrows(NullPointerException.class, () -> td.matchDescriptionIgnoreCase(null));
    }
}
