package com.gavinkim.model.user;

public class SignInException extends RuntimeException {
    public SignInException() {
        super();
    }

    public SignInException(String message) {
        super(message);
    }

    public SignInException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignInException(Throwable cause) {
        super(cause);
    }
}
