package com.mowitnow.mappers;

import com.mowitnow.data.GrilleDTO;
import com.mowitnow.entities.Grille;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GrilleMapper {
  GrilleDTO mapVersGrilleDto(Grille grille);
  Grille mapVersGrille(GrilleDTO grilleDTO);
}
