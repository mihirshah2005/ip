package shahzam.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TodoTest {
    @Test
    public void constructor_success() {
        ToDo todo = new ToDo("testing todo");
        String expected = "[T][ ] testing todo";
        assertEquals(expected, todo.toString());
    }

    @Test
    public void markAsDone_success() {
        ToDo todo = new ToDo("testing todo");
        todo.MarkDone();
        String expected = "[T][X] testing todo";
        assertEquals(expected, todo.toString());
    }
}
