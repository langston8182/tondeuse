package com.mowitnow.exceptions;

/**
 * Erreurs lors de l'utilisation de la tondeuse.
 */
public class UtilisationException extends RuntimeException {
  private String message;

  public UtilisationException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
