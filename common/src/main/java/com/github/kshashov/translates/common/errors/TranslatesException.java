package com.github.kshashov.translates.common.errors;

public class TranslatesException extends RuntimeException {

    public TranslatesException() {
    }

    public TranslatesException(String message) {
        super(message);
    }

    public TranslatesException(String message, Throwable cause) {
        super(message, cause);
    }

    public TranslatesException(Throwable cause) {
        super(cause);
    }

    public TranslatesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
