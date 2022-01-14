package com.mowitnow.adapters;

import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.mappers.TondeuseMapper;
import com.mowitnow.ports.spi.TondeusePersistence;
import com.mowitnow.repositories.TondeuseRepository;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class TondeuseJpaAdapter implements TondeusePersistence {
    private final TondeuseRepository tondeuseRepository;
    private final TondeuseMapper tondeuseMapper;

    public TondeuseJpaAdapter(TondeuseRepository tondeuseRepository, TondeuseMapper tondeuseMapper) {
        this.tondeuseRepository = tondeuseRepository;
        this.tondeuseMapper = tondeuseMapper;
    }

    @Override
    public TondeuseDTO initialiserTondeuse(TondeuseDTO tondeuseDTO) {
        return ofNullable(tondeuseDTO)
                .map(tondeuseMapper::mapVersTondeuse)
                .map(tondeuseRepository::save)
                .map(tondeuseMapper::mapVersTondeuseDto)
                .orElse(new TondeuseDTO());
    }

    @Override
    public TondeuseDTO modifierTondeuse(TondeuseDTO tondeuseDTO) {
        return ofNullable(tondeuseDTO)
                .map(tondeuseMapper::mapVersTondeuse)
                .map(tondeuseRepository::save)
                .map(tondeuseMapper::mapVersTondeuseDto)
                .orElse(new TondeuseDTO());
    }

    @Override
    public TondeuseDTO recupererTondeuse(Long id) {
        return tondeuseRepository.findById(id)
                .map(tondeuseMapper::mapVersTondeuseDto)
                .orElse(new TondeuseDTO());
    }
}
