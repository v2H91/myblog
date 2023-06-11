package training.spring.monolithic.exception;

public class MonolithicException extends Exception {

    public MonolithicException() {
        super();
    }

    public MonolithicException(String message) {
        super(message);
    }

    public MonolithicException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MonolithicException(String message, Throwable throwable,boolean enableSuppression,boolean writableStackTrace) {
        super(message, throwable,enableSuppression,writableStackTrace);
    }

}
