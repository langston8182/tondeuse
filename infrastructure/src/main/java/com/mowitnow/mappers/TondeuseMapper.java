package com.mowitnow.mappers;

import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.entities.Tondeuse;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = GrilleMapper.class)
public interface TondeuseMapper {
    @Mapping(target = "grilleDTO", source = "grille")
    TondeuseDTO mapVersTondeuseDto(Tondeuse tondeuse);

    @InheritInverseConfiguration
    Tondeuse mapVersTondeuse(TondeuseDTO tondeuseDTO);
}
