package com.mowitnow.ports.api;

import com.mowitnow.data.GrilleDTO;

/**
 * Service pour la gestion d'une grille rectangulaire de tonte.
 */
public interface GrilleService {
  /**
   * Initialise une grille aux dimensions X et Y.
   *
   * @param grilleDTO La grille à initialiser
   *
   * @return La grille avec les dimensions X et Y.
   */
  GrilleDTO initialiserGrille(GrilleDTO grilleDTO);

  /**
   * Récupère une grille selon son identifiant.
   *
   * @param id L'identifiant de la grille
   * @return La grille récupérée
   */
  GrilleDTO recupererGrille(Long id);
}
