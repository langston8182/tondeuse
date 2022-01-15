package com.mowitnow.adapters;

import com.mowitnow.data.GrilleDTO;
import com.mowitnow.entities.Grille;
import com.mowitnow.mappers.GrilleMapper;
import com.mowitnow.repositories.GrilleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GrilleJpaAdapterTest {
    @InjectMocks
    private GrilleJpaAdapter sut;

    @Mock
    private GrilleRepository grilleRepository;

    @Mock
    private GrilleMapper grilleMapper;

    @Test
    public void initialiserGrille_Nominal() {
        GrilleDTO grilleDto = creerGrilleDto();
        Grille grille = creerGrille();
        Grille grilleAvecId = creerGrille();
        GrilleDTO grilleDtoAvecId = creerGrilleDto();
        given(grilleMapper.mapVersGrille(grilleDto)).willReturn(grille);
        given(grilleRepository.save(grille)).willReturn(grilleAvecId);
        given(grilleMapper.mapVersGrilleDto(grilleAvecId)).willReturn(grilleDtoAvecId);

        GrilleDTO mut = sut.initialiserGrille(grilleDto);

        assertThat(mut).isEqualTo(grilleDtoAvecId);
    }

    private Grille creerGrille() {
        return new Grille()
                .setDimX(5)
                .setDimY(5);
    }

    private GrilleDTO creerGrilleDto() {
        return new GrilleDTO()
                .setDimX(5)
                .setDimY(5);
    }

}