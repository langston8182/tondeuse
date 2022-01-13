package com.mowitnow.ports.service;


import com.mowitnow.data.GrilleDTO;
import com.mowitnow.ports.spi.GrillePersistence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GrilleServiceImplTest {
  @InjectMocks
  private GrilleServiceImpl sut;

  @Mock
  private GrillePersistence grillePersistence;

  @Test
  public void initialiserGrille_Nominal() {
    GrilleDTO grilleDTO = creerGrilleDto();
    GrilleDTO grilleDtoAvecId = creerGrilleDtoAvecId();
    given(grillePersistence.initialiserGrille(grilleDTO))
      .willReturn(grilleDtoAvecId);

    GrilleDTO mut = sut.initialiserGrille(grilleDTO);

    assertThat(mut).isEqualTo(grilleDtoAvecId);
  }

  private GrilleDTO creerGrilleDto() {
    return new GrilleDTO()
      .setDimX(5)
      .setDimY(5);
  }

  private GrilleDTO creerGrilleDtoAvecId() {
    return creerGrilleDto()
      .setId(1L);
  }
}