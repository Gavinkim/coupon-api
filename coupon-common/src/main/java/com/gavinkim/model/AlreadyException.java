package com.gavinkim.model;

public class AlreadyException extends RuntimeException {
    public AlreadyException() {
        super();
    }

    public AlreadyException(String message) {
        super(message);
    }
}
