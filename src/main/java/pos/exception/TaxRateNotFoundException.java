package pos.exception;

public class TaxRateNotFoundException extends BasePOSException {
    public TaxRateNotFoundException(String message){
        super("Tax rate for jurisdiction " + message + " not found");
    }
}
