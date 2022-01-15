package com.mowitnow.ports.api;

import com.mowitnow.data.GrilleDTO;
import com.mowitnow.exceptions.GrilleNonTrouveeException;

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
   * @throws GrilleNonTrouveeException Si la grille n'à pas été trouvée.
   * @return La grille récupérée
   */
  GrilleDTO recupererGrille(Long id) throws GrilleNonTrouveeException;
}
