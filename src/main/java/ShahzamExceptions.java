class UnknownCommandException extends Exception {
    public UnknownCommandException() {
        super("Sorry but my knowledge does not have such command.");
    }
}

class EmptyTaskDescriptionException extends Exception {
    public EmptyTaskDescriptionException(String message) {
        super(message);
    }
}

class InvalidTaskNumberException extends Exception {
    public InvalidTaskNumberException(String message) {
        super(message);
    }
}

class InvalidDeadlineFormatException extends Exception {
    public InvalidDeadlineFormatException(String message) {
        super(message);
    }
}

class InvalidEventFormatException extends Exception {
    public InvalidEventFormatException(String message) {
        super(message);
    }
}

