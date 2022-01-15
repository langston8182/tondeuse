package com.mowitnow.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mowitnow.data.GrilleDTO;
import com.mowitnow.data.OrientationEnum;
import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.exceptions.LimiteTondeuseException;
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
import static org.mockito.BDDMockito.given;
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

    @Configuration
    @ComponentScan(basePackageClasses = {TondeuseService.class, TondeuseController.class},
            useDefaultFilters = false, includeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                    classes = {TondeuseService.class, TondeuseController.class})})
    public static class TestConfiguration {
        //
    }

    @Test
    public void initialiserTondeuse() throws Exception {
        TondeuseDTO tondeuseDto = creerTondeuseDto();
        TondeuseDTO tondeuseDtoAvecId = creerTondeuseDto()
                .setId(2L);
        given(tondeuseService.initialiserTondeuse(tondeuseDto))
                .willReturn(tondeuseDtoAvecId);

        mockMvc.perform(post("/tondeuse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tondeuseDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(2)));
    }

    @Test
    public void initialiserTondeuse_HorsLimite_Erreur412() throws Exception {
        TondeuseDTO tondeuseDto = creerTondeuseDto();
        given(tondeuseService.initialiserTondeuse(tondeuseDto))
                .willThrow(new LimiteTondeuseException("Hors limite"));

        mockMvc.perform(post("/tondeuse")
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
    public void avancerTondeuse_HorsLimite_Erreur412() throws Exception {
        TondeuseDTO tondeuseDto = creerTondeuseDto()
                .setId(1L);
        given(tondeuseService.recupererTondeuse(1L)).willReturn(tondeuseDto);
        given(tondeuseService.avancerTondeuse(tondeuseDto, 1))
                .willThrow(new LimiteTondeuseException("Hors limite."));

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

    private TondeuseDTO creerTondeuseDto() {
        GrilleDTO grilleDto = new GrilleDTO()
                .setId(1L)
                .setDimY(5)
                .setDimX(5);
        return new TondeuseDTO()
                .setOrientation(NORTH)
                .setPosX(1)
                .setPosY(1)
                .setGrilleDTO(grilleDto);
    }

}