package com.odeyalo.sonata.gateway.client;

public final class InvalidAccessTokenException extends RuntimeException {

    public InvalidAccessTokenException() {
        super("Invalid access token");
    }

    public InvalidAccessTokenException(final String message) {
        super(message);
    }

    public InvalidAccessTokenException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
