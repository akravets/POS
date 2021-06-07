package pos.exception;

public class SKUNotFoundException extends CommandException {

    public SKUNotFoundException(String sku) {
        super("SKU " + sku + " not found");
    }
}
