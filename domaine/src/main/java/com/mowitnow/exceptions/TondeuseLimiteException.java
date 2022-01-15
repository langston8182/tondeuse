package com.mowitnow.exceptions;

/**
 * Erreur en cas de dépassement de la limite de la grille
 */
public class TondeuseLimiteException extends RuntimeException{
    private String message;

    public TondeuseLimiteException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
