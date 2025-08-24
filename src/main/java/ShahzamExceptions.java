public class ShahzamExceptions extends Exception {
    public ShahzamExceptions(String message) {
        super(message);
    }
}

class UnknownCommandException extends ShahzamExceptions {
    public UnknownCommandException() {
        super("Sorry but my knowledge does not have such command.");
    }
}

class EmptyTaskDescriptionException extends ShahzamExceptions {
    public EmptyTaskDescriptionException(String message) {
        super(message);
    }
}

class InvalidTaskNumberException extends ShahzamExceptions {
    public InvalidTaskNumberException(String message) {
        super(message);
    }
}

class InvalidDeadlineFormatException extends ShahzamExceptions {
    public InvalidDeadlineFormatException(String message) {
        super(message);
    }
}

class InvalidEventFormatException extends ShahzamExceptions {
    public InvalidEventFormatException(String message) {
        super(message);
    }
}

class DataIntegrityException extends ShahzamExceptions {
    public DataIntegrityException() {
        super("Storage file integrity compromised :(");
    }
}