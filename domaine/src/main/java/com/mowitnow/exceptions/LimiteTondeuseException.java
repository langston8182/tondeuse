package com.mowitnow.exceptions;

/**
 * Erreur en cas de dépassement de la limite de la grille
 */
public class LimiteTondeuseException extends RuntimeException{
    private String message;

    public LimiteTondeuseException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
