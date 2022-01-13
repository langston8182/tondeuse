package com.mowitnow.ports.spi;

import com.mowitnow.data.GrilleDTO;

/**
 * Persiste une grille
 */
public interface GrillePersistence {
  /**
   * Enregistre une grille
   *
   * @param grilleDTO La grille à enregistrer.
   *
   * @return La grille enregistrée
   */
  GrilleDTO initialiserGrille(GrilleDTO grilleDTO);
}
