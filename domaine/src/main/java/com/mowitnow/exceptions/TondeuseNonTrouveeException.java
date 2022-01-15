package com.mowitnow.exceptions;

/**
 * Erreur lorsque la tondeuse n'à pas été trouvée
 */
public class TondeuseNonTrouveeException extends RuntimeException {
    private String message;

    public TondeuseNonTrouveeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
