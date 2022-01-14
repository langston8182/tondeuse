package com.mowitnow.ports.api;

import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.exceptions.UtilisationException;

/**
 * Service de gestion d'une tondeuse;
 */
public interface TondeuseService {
  /**
   * Initialise une tondeuse. Lance une erreur si la position de la tondeuse dépasse la zone de tonte.
   *
   * @param tondeuseDTO La tondeuse à initialiser.
   *
   * @throws UtilisationException Erreur si la tondeuse dépasse la zone de tonte.
   * @return La tondeuse initialisée avec une position et une orientation.
   */
  TondeuseDTO initialiserTondeuse(TondeuseDTO tondeuseDTO) throws UtilisationException;

  /**
   * Modifiation la position et l'orientation de la tondeuse.
   *
   * @param tondeuseDTO La tondeuse à modifier.
   *
   * @return la tondeuse avec les nouveaux paramètres.
   */
  TondeuseDTO modifierTondeuse(TondeuseDTO tondeuseDTO);

  /**
   * Récupère les informations d'une tondeuse (position et orientation).
   *
   * @param id L'identifiant de la tondeuse
   *
   * @return Les informations de la tondeuse (position et orientation).
   */
  TondeuseDTO recupererTondeuse(Long id);
}
