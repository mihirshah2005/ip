package shahzam.exception;

public class UnknownCommandException extends ShahzamExceptions {
    public UnknownCommandException() {
        super("Sorry but my knowledge does not have such command.");
    }
}