package learning.product.exception;

@SuppressWarnings("CdiInjectionPointsInspection")
public class InvalidProductException extends IllegalArgumentException {

    public InvalidProductException(String message) {
        super(message);
    }

}
