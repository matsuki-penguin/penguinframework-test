package org.penguinframework.test.exception;

public class ReflectionRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -2720604675677770890L;

    public ReflectionRuntimeException() {
        super();
    }

    public ReflectionRuntimeException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ReflectionRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReflectionRuntimeException(String message) {
        super(message);
    }

    public ReflectionRuntimeException(Throwable cause) {
        super(cause);
    }
}
