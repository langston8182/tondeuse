package com.mowitnow.exceptions;

/**
 * Erreur lorsque la grille n'à pas été trouvée
 */
public class GrilleNonTrouveeException extends RuntimeException {
    private String message;

    public GrilleNonTrouveeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
