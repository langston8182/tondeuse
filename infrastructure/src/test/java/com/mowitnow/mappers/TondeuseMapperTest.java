package com.mowitnow.mappers;

import com.mowitnow.data.GrilleDTO;
import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.entities.Grille;
import com.mowitnow.entities.Tondeuse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.mowitnow.data.OrientationEnum.NORTH;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class TondeuseMapperTest {
    @Autowired
    private TondeuseMapper sut;

    @Configuration
    @ComponentScan(basePackageClasses = {TondeuseMapper.class, GrilleMapper.class},
            useDefaultFilters = false, includeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {TondeuseMapper.class, GrilleMapper.class})})
    public static class TestConfiguration {
        //
    }

    @Test
    public void mapVersTondeuseDto() {
        Tondeuse tondeuse = creerTondeuse(creerGrille());
        TondeuseDTO tondeuseDTO = creerTondeuseDto(creerGrilleDto());

        TondeuseDTO mut = sut.mapVersTondeuseDto(tondeuse);

        assertThat(mut).isEqualToComparingFieldByField(tondeuseDTO);
    }

    @Test
    public void mapVersTondeuse() {
        TondeuseDTO tondeuseDTO = creerTondeuseDto(creerGrilleDto());
        Tondeuse tondeuse = creerTondeuse(creerGrille());

        Tondeuse mut = sut.mapVersTondeuse(tondeuseDTO);

        assertThat(mut).isEqualToComparingFieldByField(tondeuse);
    }

    private Grille creerGrille() {
        return new Grille()
                .setDimX(5)
                .setDimY(5)
                .setId(1L);
    }

    private GrilleDTO creerGrilleDto() {
        return new GrilleDTO()
                .setDimX(5)
                .setDimY(5)
                .setId(1L);
    }

    private Tondeuse creerTondeuse(Grille grille) {
        return new Tondeuse()
                .setGrille(grille)
                .setPosY(1)
                .setPosX(2)
                .setOrientation(NORTH)
                .setId(1L);
    }

    private TondeuseDTO creerTondeuseDto(GrilleDTO grilleDto) {
        return new TondeuseDTO()
                .setGrilleDTO(grilleDto)
                .setPosY(1)
                .setPosX(2)
                .setOrientation(NORTH)
                .setId(1L);
    }
}