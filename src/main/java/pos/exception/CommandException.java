package pos.exception;

public abstract class CommandException extends BasePOSException {
    public CommandException(String message) {
        super(message);
    }
}
