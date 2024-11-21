package com.javierlnc.back_app.exception;

public class BadPassword extends RuntimeException {
    public BadPassword(String message) {
        super(message);
    }
}
