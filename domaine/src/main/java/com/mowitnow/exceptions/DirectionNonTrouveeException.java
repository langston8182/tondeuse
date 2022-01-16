package com.mowitnow.exceptions;

/**
 * Erreur lorsque la direction n'existe pas.
 */
public class DirectionNonTrouveeException extends RuntimeException {
    private String message;

    public DirectionNonTrouveeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
