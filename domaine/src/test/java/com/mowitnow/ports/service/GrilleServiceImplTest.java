package com.mowitnow.ports.service;


import com.mowitnow.data.GrilleDTO;
import com.mowitnow.exceptions.GrilleNonTrouveeException;
import com.mowitnow.ports.spi.GrillePersistence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

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

  @Test
  public void recupererGrille_Nominal() {
    GrilleDTO grilleDTO = creerGrilleDtoAvecId();
    given(grillePersistence.recupererGrille(1L)).willReturn(grilleDTO);

    GrilleDTO mut = sut.recupererGrille(1L);

    assertThat(mut).isEqualTo(grilleDTO);
  }

  @Test
  public void recupererGrille_GrilleNonTrouvee_LanceErreur() {
    given(grillePersistence.recupererGrille(1L))
            .willReturn(null);

    Throwable throwable = catchThrowable(() -> sut.recupererGrille(1L));

    assertThat(throwable).isNotNull()
            .isExactlyInstanceOf(GrilleNonTrouveeException.class);
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