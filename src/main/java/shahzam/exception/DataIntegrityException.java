package shahzam.exception;

public class DataIntegrityException extends ShahzamExceptions {
    public DataIntegrityException() {
        super("shahzam.utils.Storage file integrity compromised :(");
    }
}
