package org.penguinframework.test.exception;

public class AssertRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -2720604675677770890L;

    public AssertRuntimeException() {
        super();
    }

    public AssertRuntimeException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AssertRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AssertRuntimeException(String message) {
        super(message);
    }

    public AssertRuntimeException(Throwable cause) {
        super(cause);
    }
}
