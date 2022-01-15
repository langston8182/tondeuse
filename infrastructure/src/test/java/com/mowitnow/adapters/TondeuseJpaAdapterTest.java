package com.mowitnow.adapters;


import com.mowitnow.data.GrilleDTO;
import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.entities.Grille;
import com.mowitnow.entities.Tondeuse;
import com.mowitnow.mappers.TondeuseMapper;
import com.mowitnow.repositories.TondeuseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static com.mowitnow.data.OrientationEnum.EAST;
import static com.mowitnow.data.OrientationEnum.NORTH;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class TondeuseJpaAdapterTest {
    @InjectMocks
    private TondeuseJpaAdapter sut;

    @Mock
    private TondeuseMapper tondeuseMapper;

    @Mock
    private TondeuseRepository tondeuseRepository;

    @Test
    public void initialiserTondeuse() {
        TondeuseDTO tondeuseDto = creerTondeuseDto();
        Tondeuse tondeuse = creerTondeuse();
        given(tondeuseMapper.mapVersTondeuse(tondeuseDto))
                .willReturn(tondeuse);
        Tondeuse tondeuseAvecId = creerTondeuse()
                .setId(1L);
        TondeuseDTO tondeuseDtoAvecId = creerTondeuseDto()
                .setId(1L);
        given(tondeuseRepository.save(tondeuse))
                .willReturn(tondeuseAvecId);
        given(tondeuseMapper.mapVersTondeuseDto(tondeuseAvecId))
                .willReturn(tondeuseDtoAvecId);

        TondeuseDTO mut = sut.initialiserTondeuse(tondeuseDto);

        assertThat(mut).isEqualTo(tondeuseDtoAvecId);
    }

    @Test
    public void modifierTondeuse() {
        TondeuseDTO tondeuseDto = creerTondeuseDto();
        Tondeuse tondeuse = creerTondeuse();
        given(tondeuseMapper.mapVersTondeuse(tondeuseDto))
                .willReturn(tondeuse);
        Tondeuse tondeuseAvecId = creerTondeuse()
                .setId(1L);
        TondeuseDTO tondeuseDtoAvecId = creerTondeuseDto()
                .setId(1L);
        given(tondeuseRepository.save(tondeuse))
                .willReturn(tondeuseAvecId);
        given(tondeuseMapper.mapVersTondeuseDto(tondeuseAvecId))
                .willReturn(tondeuseDtoAvecId);

        TondeuseDTO mut = sut.modifierTondeuse(tondeuseDto);

        assertThat(mut).isEqualTo(tondeuseDtoAvecId);
    }

    @Test
    public void recupererTondeuse() {
        Grille grille = new Grille()
                .setId(1L)
                .setDimX(5)
                .setDimY(5);
        Optional<Tondeuse> tondeuse = of(new Tondeuse()
                .setId(1L)
                .setOrientation(NORTH)
                .setPosX(1)
                .setPosY(1)
                .setGrille(grille));
        given(tondeuseRepository.findById(1L))
                .willReturn(tondeuse);
        TondeuseDTO tondeuseDto = creerTondeuseDto();
        given(tondeuseMapper.mapVersTondeuseDto(tondeuse.get())).willReturn(tondeuseDto);

        TondeuseDTO mut = sut.recupererTondeuse(1L);

        assertThat(mut).isEqualTo(tondeuseDto);
    }

    private Grille creerGrille() {
        return new Grille()
                .setDimX(5)
                .setDimY(5)
                .setId(1L);
    }

    private GrilleDTO creerGrilleDto() {
        return new GrilleDTO()
                .setDimY(5)
                .setDimY(5)
                .setId(1L);
    }

    private Tondeuse creerTondeuse() {
        return new Tondeuse()
                .setOrientation(EAST)
                .setPosX(1)
                .setPosY(1)
                .setGrille(creerGrille());
    }

    private TondeuseDTO creerTondeuseDto() {
        return new TondeuseDTO()
                .setOrientation(EAST)
                .setGrilleDTO(creerGrilleDto())
                .setPosX(1)
                .setPosY(1);
    }
}