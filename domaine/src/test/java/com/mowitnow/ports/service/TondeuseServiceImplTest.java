package com.mowitnow.ports.service;

import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.ports.spi.TondeusePersistence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.mowitnow.data.OrientationEnum.EAST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class TondeuseServiceImplTest {
  @InjectMocks
  private TondeuseServiceImpl sut;

  @Mock
  private TondeusePersistence tondeusePersistence;

  @Test
  public void initialiseTondeuse_Nominal() {
    TondeuseDTO tondeuseDTO = creerTondeuse();
    TondeuseDTO tondeuseDTOAvecId = creerTondeuseAvecId();
    given(tondeusePersistence.initialiserTondeuse(tondeuseDTO))
      .willReturn(tondeuseDTOAvecId);

    TondeuseDTO mut = sut.initialiserTondeuse(tondeuseDTO);

    assertThat(mut.getId()).isEqualTo(1L);
    assertThat(mut.getOrientation()).isEqualTo(EAST);
    assertThat(mut.getPosX()).isEqualTo(1);
    assertThat(mut.getPosY()).isEqualTo(2);
  }

  @Test
  public void modifierTondeuse_Nominal() {
    TondeuseDTO tondeuseDTO = creerTondeuse();
    given(tondeusePersistence.modifierTondeuse(tondeuseDTO))
      .willReturn(tondeuseDTO);

    TondeuseDTO mut = sut.modifierTondeuse(tondeuseDTO);

    assertThat(mut).isEqualTo(tondeuseDTO);
  }

  @Test
  public void recupererTondeuse_Nominal() {
    TondeuseDTO tondeuseDTO = creerTondeuseAvecId();
    given(tondeusePersistence.recupererTondeuse(1L))
      .willReturn(tondeuseDTO);

    TondeuseDTO mut = sut.recupererTondeuse(1L);

    assertThat(mut).isEqualTo(tondeuseDTO);
  }

  private TondeuseDTO creerTondeuse() {
    return new TondeuseDTO()
      .setOrientation(EAST)
      .setPosX(1)
      .setPosY(2);
  }

  private TondeuseDTO creerTondeuseAvecId() {
    return creerTondeuse()
      .setId(1L);
  }
}