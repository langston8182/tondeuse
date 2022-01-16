package com.mowitnow.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mowitnow.data.DirectionEnum;
import com.mowitnow.data.GrilleDTO;
import com.mowitnow.data.OrientationEnum;
import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.exceptions.DirectionNonTrouveeException;
import com.mowitnow.exceptions.TondeuseLimiteException;
import com.mowitnow.exceptions.TondeuseNonTrouveeException;
import com.mowitnow.ports.api.GrilleService;
import com.mowitnow.ports.api.TondeuseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.mowitnow.data.DirectionEnum.GAUCHE;
import static com.mowitnow.data.OrientationEnum.NORTH;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(TondeuseController.class)
@ContextConfiguration
public class TondeuseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TondeuseService tondeuseService;

    @MockBean
    private GrilleService grilleService;

    @Configuration
    @ComponentScan(basePackageClasses = {TondeuseService.class, TondeuseController.class, GrilleService.class},
            useDefaultFilters = false, includeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                    classes = {TondeuseService.class, TondeuseController.class, GrilleService.class})})
    public static class TestConfiguration {
        //
    }

    @Test
    public void initialiserTondeuse() throws Exception {
        TondeuseDTO tondeuseDto = creerTondeuseDto();
        TondeuseDTO tondeuseDtoAvecId = creerTondeuseDto()
                .setId(2L);
        GrilleDTO grilleDTO = creerGrilleDto();
        given(tondeuseService.initialiserTondeuse(tondeuseDto))
                .willReturn(tondeuseDtoAvecId);
        given(grilleService.recupererGrille(1L))
                .willReturn(grilleDTO);

        mockMvc.perform(post("/tondeuse?idGrille=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tondeuseDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(2)))
                .andExpect(jsonPath("$.grilleDTO.id", equalTo(1)));
    }

    @Test
    public void initialiserTondeuse_HorsLimite_Erreur412() throws Exception {
        TondeuseDTO tondeuseDto = creerTondeuseDto();
        GrilleDTO grilleDTO = creerGrilleDto();
        given(grilleService.recupererGrille(1L))
                .willReturn(grilleDTO);
        given(tondeuseService.initialiserTondeuse(tondeuseDto))
                .willThrow(new TondeuseLimiteException("Hors limite"));

        mockMvc.perform(post("/tondeuse?idGrille=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tondeuseDto)))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void pivoterTondeuseGauche() throws Exception {
        TondeuseDTO tondeuseDto = creerTondeuseDto()
                .setId(1L);
        TondeuseDTO tondeuseDtoNouvelleDirection = creerTondeuseDto()
                .setOrientation(OrientationEnum.WEST);
        given(tondeuseService.recupererTondeuse(1L)).willReturn(tondeuseDto);
        given(tondeuseService.pivoterTondeuse(tondeuseDto, GAUCHE))
                .willReturn(tondeuseDtoNouvelleDirection);

        mockMvc.perform(put("/tondeuse/1/pivoter?direction=G")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orientation", equalTo("WEST")));
    }

    @Test
    public void pivoterTondeuse_DirectionNonTrouvee_LanceErreur412() throws Exception {
        given(tondeuseService.pivoterTondeuse(any(TondeuseDTO.class), any(DirectionEnum.class)))
                .willThrow(new DirectionNonTrouveeException("Direction non trouvée"));

        mockMvc.perform(put("/tondeuse/1/pivoter?direction=T")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void pivoterTondeuseGauche_TondeuseNonTrouvee_LanceErreur204() throws Exception {
        given(tondeuseService.recupererTondeuse(1L)).willThrow(new TondeuseNonTrouveeException("Tondeuse non trouvée"));

        mockMvc.perform(put("/tondeuse/1/pivoter?direction=G")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        then(tondeuseService).should(never()).pivoterTondeuse(any(), any());
    }

    @Test
    public void avancerTondeuse1Cases() throws Exception {
        TondeuseDTO tondeuseDto = creerTondeuseDto()
                .setId(1L);
        TondeuseDTO tondeuseDtoNouvellePosition = creerTondeuseDto()
                .setPosX(1)
                .setPosY(2);
        given(tondeuseService.recupererTondeuse(1L)).willReturn(tondeuseDto);
        given(tondeuseService.avancerTondeuse(tondeuseDto, 1))
                .willReturn(tondeuseDtoNouvellePosition);

        mockMvc.perform(put("/tondeuse/1/avancer?nombreCases=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posX", equalTo(1)))
                .andExpect(jsonPath("$.posY", equalTo(2)));
    }

    @Test
    public void avancerTondeuse_TondeuseNonTrouvee_LanceErreur204() throws Exception {
        given(tondeuseService.recupererTondeuse(1L)).willThrow(new TondeuseNonTrouveeException("Tondeuse non trouvée"));

        mockMvc.perform(put("/tondeuse/1/avancer?nombreCases=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    public void avancerTondeuse_HorsLimite_Erreur412() throws Exception {
        TondeuseDTO tondeuseDto = creerTondeuseDto()
                .setId(1L);
        given(tondeuseService.recupererTondeuse(1L)).willReturn(tondeuseDto);
        given(tondeuseService.avancerTondeuse(tondeuseDto, 1))
                .willThrow(new TondeuseLimiteException("Hors limite."));

        mockMvc.perform(put("/tondeuse/1/avancer?nombreCases=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    public void recupererTondeuse() throws Exception {
        TondeuseDTO tondeuseDTO = creerTondeuseDto()
                .setId(1L);
        given(tondeuseService.recupererTondeuse(1L)).willReturn(tondeuseDTO);

        mockMvc.perform(get("/tondeuse/1/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)));
    }

    @Test
    public void recupererTondeuse_TondeuseNonTrouvee_Erreur204() throws Exception {
        given(tondeuseService.recupererTondeuse(1L))
                .willThrow(new TondeuseNonTrouveeException("Tondeuse non trouvée"));

        mockMvc.perform(get("/tondeuse/1/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private TondeuseDTO creerTondeuseDto() {
        GrilleDTO grilleDto = creerGrilleDto();
        return new TondeuseDTO()
                .setOrientation(NORTH)
                .setPosX(1)
                .setPosY(1)
                .setGrilleDTO(grilleDto);
    }

    private GrilleDTO creerGrilleDto() {
        return new GrilleDTO()
                .setId(1L)
                .setDimY(5)
                .setDimX(5);
    }

}