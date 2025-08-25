package shahzam.task;

import shahzam.utils.DateTimeFormatUtils;

import java.time.LocalDateTime;

public class Deadline extends Task {
    private final LocalDateTime time;
    public Deadline(String description, LocalDateTime time) {
        super(description);
        this.time = time;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeFormatUtils.formatDateTime(time) + ")";
    }

}
