package com.mowitnow.ports.spi;

import com.mowitnow.data.TondeuseDTO;

/**
 * Persiste une tondeuse
 */
public interface TondeusePersistence {

  /**
   * Enregistre une tondeuse.
   *
   * @param tondeuseDTO La tondeuse à enregistrer.
   *
   * @return La tondeuse enregistrée
   */
  TondeuseDTO initialiserTondeuse(TondeuseDTO tondeuseDTO);

  /**
   * Modifie une tondeuse et la persiste.
   *
   * @param tondeuseDTO La tondeuse à modifier.
   *
   * @return La tondeuse modifiée.
   */
  TondeuseDTO modifierTondeuse(TondeuseDTO tondeuseDTO);

  /**
   * Récupère les données de la tondeuse.
   *
   * @param id L'identifiant de la tondeuse.
   *
   * @return La tondeuse persistée.
   */
  TondeuseDTO recupererTondeuse(Long id);

}
