package com.blackbox.common.selenium;


public class WebTestingException extends RuntimeException {
    public WebTestingException() {
        super();
    }

    public WebTestingException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebTestingException(String message) {
        super(message);
    }

    public WebTestingException(Throwable cause) {
        super(cause);
    }
}
