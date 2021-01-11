package learning.product.exception;

@SuppressWarnings("CdiInjectionPointsInspection")
public class InvalidPriceException extends IllegalArgumentException {

    public InvalidPriceException(String message) {
        super(message);
    }

}
