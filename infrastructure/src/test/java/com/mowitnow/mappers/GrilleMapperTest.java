package com.mowitnow.mappers;


import com.mowitnow.data.GrilleDTO;
import com.mowitnow.entities.Grille;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class GrilleMapperTest {
    @Autowired
    private GrilleMapper sut;

    @Configuration
    @ComponentScan(basePackageClasses = GrilleMapper.class, useDefaultFilters = false, includeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = GrilleMapper.class)})
    public static class TestConfiguration {
        //
    }

    @Test
    public void mapVersGrilleDto() {
        Grille grille = new Grille()
                .setDimY(5)
                .setDimX(5)
                .setId(1L);

        GrilleDTO mut = sut.mapVersGrilleDto(grille);

        assertThat(mut).isEqualToComparingFieldByField(grille);
    }

    @Test
    public void mapVersGrille() {
        GrilleDTO grilleDTO = new GrilleDTO()
                .setDimY(5)
                .setDimX(5)
                .setId(1L);

        Grille mut = sut.mapVersGrille(grilleDTO);

        assertThat(mut).isEqualToComparingFieldByField(grilleDTO);
    }
}