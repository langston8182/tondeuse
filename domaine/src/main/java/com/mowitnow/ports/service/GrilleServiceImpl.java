package com.mowitnow.ports.service;

import com.mowitnow.data.GrilleDTO;
import com.mowitnow.exceptions.GrilleNonTrouveeException;
import com.mowitnow.ports.api.GrilleService;
import com.mowitnow.ports.spi.GrillePersistence;

import static java.util.Optional.ofNullable;

public class GrilleServiceImpl implements GrilleService {
  private final GrillePersistence grillePersistence;

  public GrilleServiceImpl(GrillePersistence grillePersistence) {
    this.grillePersistence = grillePersistence;
  }

  @Override
  public GrilleDTO initialiserGrille(GrilleDTO grilleDTO) {
    return grillePersistence.initialiserGrille(grilleDTO);
  }

  @Override
  public GrilleDTO recupererGrille(Long id) {
    return ofNullable(grillePersistence.recupererGrille(id))
            .orElseThrow(() -> new GrilleNonTrouveeException("Grille non trouvée"));
  }
}
