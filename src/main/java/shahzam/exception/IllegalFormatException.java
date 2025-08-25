package shahzam.exception;

public class IllegalFormatException extends ShahzamExceptions {
    public IllegalFormatException(String format) {
        super("Please follow this format:\n  " + format);
    }
}