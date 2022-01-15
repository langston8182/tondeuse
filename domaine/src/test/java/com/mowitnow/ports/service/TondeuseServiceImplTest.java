package com.mowitnow.ports.service;

import com.mowitnow.data.GrilleDTO;
import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.exceptions.TondeuseLimiteException;
import com.mowitnow.exceptions.TondeuseNonTrouveeException;
import com.mowitnow.exceptions.UtilisationException;
import com.mowitnow.ports.spi.TondeusePersistence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.mowitnow.data.DirectionEnum.DROITE;
import static com.mowitnow.data.DirectionEnum.GAUCHE;
import static com.mowitnow.data.OrientationEnum.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

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
    public void initialiserTondeuse_DepasseZoneX_LanceErreur() {
        TondeuseDTO tondeuseDTO = creerTondeuseAvecId();
        tondeuseDTO.setPosX(6);

        Throwable throwable = catchThrowable(() -> sut.initialiserTondeuse(tondeuseDTO));

        assertThat(throwable).isNotNull()
                .isInstanceOf(UtilisationException.class);
    }

    @Test
    public void initialiserTondeuse_DepasseZoneY_LanceErreur() {
        TondeuseDTO tondeuseDTO = creerTondeuseAvecId();
        tondeuseDTO.setPosY(6);

        Throwable throwable = catchThrowable(() -> sut.initialiserTondeuse(tondeuseDTO));

        assertThat(throwable).isNotNull()
                .isInstanceOf(UtilisationException.class);
    }

    @Test
    public void modifierTondeuse_Nominal() {
        TondeuseDTO tondeuseDTO = creerTondeuseAvecId();
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

    @Test
    public void recupererTondeuse_TondeuseNonTrouvee_LanceErreur() {
        given(tondeusePersistence.recupererTondeuse(1L))
                .willReturn(null);

        Throwable throwable = catchThrowable(() -> sut.recupererTondeuse(1L));

        assertThat(throwable).isNotNull()
                .isExactlyInstanceOf(TondeuseNonTrouveeException.class);
    }

    @Test
    public void pivoterTondeuse_PositionEastGauche() {
        TondeuseDTO tondeuseDto = creerTondeuseAvecId()
                .setOrientation(EAST);
        TondeuseDTO tondeuseModifiee = creerTondeuseAvecId()
                .setOrientation(NORTH);
        given(tondeusePersistence.modifierTondeuse(tondeuseModifiee))
                .willReturn(tondeuseModifiee);

        TondeuseDTO mut = sut.pivoterTondeuse(tondeuseDto, GAUCHE);

        assertThat(mut).isEqualTo(tondeuseModifiee);
    }

    @Test
    public void pivoterTondeuse_PositionNorthGauche() {
        TondeuseDTO tondeuseDto = creerTondeuseAvecId()
                .setOrientation(NORTH);
        TondeuseDTO tondeuseModifiee = creerTondeuseAvecId()
                .setOrientation(WEST);
        given(tondeusePersistence.modifierTondeuse(tondeuseModifiee))
                .willReturn(tondeuseModifiee);

        TondeuseDTO mut = sut.pivoterTondeuse(tondeuseDto, GAUCHE);

        assertThat(mut).isEqualTo(tondeuseModifiee);
    }


    @Test
    public void pivoterTondeuse_PositionSouthGauche() {
        TondeuseDTO tondeuseDto = creerTondeuseAvecId()
                .setOrientation(SOUTH);
        TondeuseDTO tondeuseModifiee = creerTondeuseAvecId()
                .setOrientation(EAST);
        given(tondeusePersistence.modifierTondeuse(tondeuseModifiee))
                .willReturn(tondeuseModifiee);

        TondeuseDTO mut = sut.pivoterTondeuse(tondeuseDto, GAUCHE);

        assertThat(mut).isEqualTo(tondeuseModifiee);
    }

    @Test
    public void pivoterTondeuse_PositionWestGauche() {
        TondeuseDTO tondeuseDto = creerTondeuseAvecId()
                .setOrientation(WEST);
        TondeuseDTO tondeuseModifiee = creerTondeuseAvecId()
                .setOrientation(SOUTH);
        given(tondeusePersistence.modifierTondeuse(tondeuseModifiee))
                .willReturn(tondeuseModifiee);

        TondeuseDTO mut = sut.pivoterTondeuse(tondeuseDto, GAUCHE);

        assertThat(mut).isEqualTo(tondeuseModifiee);
    }

    @Test
    public void pivoterTondeuse_PositionNorthDroite() {
        TondeuseDTO tondeuseDto = creerTondeuseAvecId()
                .setOrientation(NORTH);
        TondeuseDTO tondeuseModifiee = creerTondeuseAvecId()
                .setOrientation(EAST);
        given(tondeusePersistence.modifierTondeuse(tondeuseModifiee))
                .willReturn(tondeuseModifiee);

        TondeuseDTO mut = sut.pivoterTondeuse(tondeuseDto, DROITE);

        assertThat(mut).isEqualTo(tondeuseModifiee);
    }

    @Test
    public void pivoterTondeuse_PositionEastDroite() {
        TondeuseDTO tondeuseDto = creerTondeuseAvecId()
                .setOrientation(EAST);
        TondeuseDTO tondeuseModifiee = creerTondeuseAvecId()
                .setOrientation(SOUTH);
        given(tondeusePersistence.modifierTondeuse(tondeuseModifiee))
                .willReturn(tondeuseModifiee);

        TondeuseDTO mut = sut.pivoterTondeuse(tondeuseDto, DROITE);

        assertThat(mut).isEqualTo(tondeuseModifiee);
    }

    @Test
    public void pivoterTondeuse_PositionSouthDroite() {
        TondeuseDTO tondeuseDto = creerTondeuseAvecId()
                .setOrientation(SOUTH);
        TondeuseDTO tondeuseModifiee = creerTondeuseAvecId()
                .setOrientation(WEST);
        given(tondeusePersistence.modifierTondeuse(tondeuseModifiee))
                .willReturn(tondeuseModifiee);

        TondeuseDTO mut = sut.pivoterTondeuse(tondeuseDto, DROITE);

        assertThat(mut).isEqualTo(tondeuseModifiee);
    }

    @Test
    public void pivoterTondeuse_PositionWestDroite() {
        TondeuseDTO tondeuseDto = creerTondeuseAvecId()
                .setOrientation(WEST);
        TondeuseDTO tondeuseModifiee = creerTondeuseAvecId()
                .setOrientation(NORTH);
        given(tondeusePersistence.modifierTondeuse(tondeuseModifiee))
                .willReturn(tondeuseModifiee);

        TondeuseDTO mut = sut.pivoterTondeuse(tondeuseDto, DROITE);

        assertThat(mut).isEqualTo(tondeuseModifiee);
    }

    @Test
    public void avancerTondeuse_OrientatiomNorth2Cases() {
        TondeuseDTO tondeuseDTO = creerTondeuseAvecId()
                .setOrientation(NORTH);
        TondeuseDTO tondeuseModifiee = creerTondeuseAvecId()
                .setOrientation(NORTH)
                .setPosX(1)
                .setPosY(4);
        given(tondeusePersistence.modifierTondeuse(tondeuseModifiee))
                .willReturn(tondeuseModifiee);

        TondeuseDTO mut = sut.avancerTondeuse(tondeuseDTO, 2);


        assertThat(mut).isEqualTo(tondeuseModifiee);
    }

    @Test
    public void avancerTondeuse_OrientationNorthHorsLimite_LanceErreur() {
        TondeuseDTO tondeuseDTO = creerTondeuseAvecId()
                .setOrientation(NORTH);

        Throwable throwable = catchThrowable(() -> sut.avancerTondeuse(tondeuseDTO, 3));

        assertThat(throwable).isNotNull()
                .isExactlyInstanceOf(TondeuseLimiteException.class);
        then(tondeusePersistence).should(never()).modifierTondeuse(any());
    }

    @Test
    public void avancerTondeuse_OrientationSouth2Cases() {
        TondeuseDTO tondeuseDTO = creerTondeuseAvecId()
                .setOrientation(SOUTH);
        TondeuseDTO tondeuseModifiee = creerTondeuseAvecId()
                .setOrientation(SOUTH)
                .setPosX(1)
                .setPosY(0);
        given(tondeusePersistence.modifierTondeuse(tondeuseModifiee))
                .willReturn(tondeuseModifiee);

        TondeuseDTO mut = sut.avancerTondeuse(tondeuseDTO, 2);

        assertThat(mut).isEqualTo(tondeuseModifiee);
    }

    @Test
    public void avancerTondeuse_OrientationSouthHorsLimite_LanceErreur() {
        TondeuseDTO tondeuseDTO = creerTondeuseAvecId()
                .setOrientation(SOUTH);

        Throwable throwable = catchThrowable(() -> sut.avancerTondeuse(tondeuseDTO, 3));

        assertThat(throwable).isNotNull()
                .isExactlyInstanceOf(TondeuseLimiteException.class);
        then(tondeusePersistence).should(never()).modifierTondeuse(any());
    }

    @Test
    public void avancerTondeuse_OrientationEast3Cases() {
        TondeuseDTO tondeuseDTO = creerTondeuseAvecId()
                .setOrientation(EAST);
        TondeuseDTO tondeuseModifiee = creerTondeuseAvecId()
                .setOrientation(EAST)
                .setPosX(4)
                .setPosY(2);
        given(tondeusePersistence.modifierTondeuse(tondeuseModifiee))
                .willReturn(tondeuseModifiee);

        TondeuseDTO mut = sut.avancerTondeuse(tondeuseDTO, 3);

        assertThat(mut).isEqualTo(tondeuseModifiee);
    }

    @Test
    public void avancerTondeuse_OrientationEastHorsLimite_LanceErreur() {
        TondeuseDTO tondeuseDTO = creerTondeuseAvecId()
                .setOrientation(NORTH);

        Throwable throwable = catchThrowable(() -> sut.avancerTondeuse(tondeuseDTO, 4));

        assertThat(throwable).isNotNull()
                .isExactlyInstanceOf(TondeuseLimiteException.class);
        then(tondeusePersistence).should(never()).modifierTondeuse(any());
    }

    @Test
    public void avancerTondeuse_OrientationWest1Cases() {
        TondeuseDTO tondeuseDTO = creerTondeuseAvecId()
                .setOrientation(WEST);
        TondeuseDTO tondeuseModifiee = creerTondeuseAvecId()
                .setOrientation(WEST)
                .setPosX(0)
                .setPosY(2);
        given(tondeusePersistence.modifierTondeuse(tondeuseModifiee))
                .willReturn(tondeuseModifiee);

        TondeuseDTO mut = sut.avancerTondeuse(tondeuseDTO, 1);

        assertThat(mut).isEqualTo(tondeuseModifiee);
    }

    @Test
    public void avancerTondeuse_OrientationWestHorsLimite_LanceErreur() {
        TondeuseDTO tondeuseDTO = creerTondeuseAvecId()
                .setOrientation(WEST);

        Throwable throwable = catchThrowable(() -> sut.avancerTondeuse(tondeuseDTO, 2));

        assertThat(throwable).isNotNull()
                .isExactlyInstanceOf(TondeuseLimiteException.class);
        then(tondeusePersistence).should(never()).modifierTondeuse(any());
    }

    private TondeuseDTO creerTondeuse() {
        GrilleDTO grilleDTO = creerGrilleDto();
        return new TondeuseDTO()
                .setOrientation(EAST)
                .setPosX(1)
                .setPosY(2)
                .setGrilleDTO(grilleDTO);
    }

    private GrilleDTO creerGrilleDto() {
        return new GrilleDTO()
                .setDimX(5)
                .setDimY(5)
                .setId(1L);
    }

    private TondeuseDTO creerTondeuseAvecId() {
        return creerTondeuse()
                .setId(1L);
    }
}