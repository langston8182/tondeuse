package com.mowitnow.adapters;

import com.mowitnow.data.GrilleDTO;
import com.mowitnow.mappers.GrilleMapper;
import com.mowitnow.ports.spi.GrillePersistence;
import com.mowitnow.repositories.GrilleRepository;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class GrilleJpaAdapter implements GrillePersistence {
  private final GrilleRepository grilleRepository;
  private final GrilleMapper grilleMapper;

  public GrilleJpaAdapter(GrilleRepository grilleRepository, GrilleMapper grilleMapper) {
    this.grilleMapper = grilleMapper;
    this.grilleRepository = grilleRepository;
  }

  @Override
  public GrilleDTO initialiserGrille(GrilleDTO grilleDTO) {
    return ofNullable(grilleDTO)
            .map(grilleMapper::mapVersGrille)
            .map(grilleRepository::save)
            .map(grilleMapper::mapVersGrilleDto)
            .orElse(new GrilleDTO());
  }
}
