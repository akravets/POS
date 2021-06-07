package pos.exception;

public abstract class BasePOSException extends RuntimeException {
    public BasePOSException(String message){
        super(message);
    }
}
