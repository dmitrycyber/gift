package by.mjs.ivoninsky.gift.dao.exception;

public class GiftNotFoundException extends RuntimeException {
    public GiftNotFoundException() {
        super();
    }

    public GiftNotFoundException(String message) {
        super(message);
    }

    public GiftNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
