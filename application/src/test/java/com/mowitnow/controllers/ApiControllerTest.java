package com.mowitnow.controllers;

import com.mowitnow.data.GrilleDTO;
import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.exceptions.TondeuseLimiteException;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.mowitnow.data.OrientationEnum.NORTH;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ApiController.class)
@ContextConfiguration
public class ApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GrilleService grilleService;

    @MockBean
    private TondeuseService tondeuseService;

    @Configuration
    @ComponentScan(basePackageClasses = {TondeuseService.class, ApiController.class, GrilleService.class},
            useDefaultFilters = false, includeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                    classes = {TondeuseService.class, ApiController.class, GrilleService.class})})
    public static class TestConfiguration {
        //
    }

    @Test
    public void chargerFichier() throws Exception {
        String contenuFichier = "5 5\n1 2 N\nGAGAGAGAA";
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("fichier", "tondeuse.txt", "text/plain", contenuFichier.getBytes());
        GrilleDTO grilleDto = creerGrilleDto();
        GrilleDTO grilleDtoAvecId = creerGrilleDto()
                .setId(1L);
        TondeuseDTO tondeuseDto = creerTondeuseDto(grilleDtoAvecId);
        TondeuseDTO tondeuseDtoAvecId = creerTondeuseDto(grilleDtoAvecId)
                .setId(1L);
        given(grilleService.initialiserGrille(refEq(grilleDto)))
                .willReturn(grilleDtoAvecId);
        given(tondeuseService.initialiserTondeuse(tondeuseDto))
                .willReturn(tondeuseDtoAvecId);
        given(tondeuseService.pivoterTondeuse(any(), any()))
                .willReturn(tondeuseDtoAvecId);
        given(tondeuseService.avancerTondeuse(any(), anyInt()))
                .willReturn(tondeuseDtoAvecId);

        mockMvc.perform(multipart("/")
                        .file(mockMultipartFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].posX", equalTo(1)))
                .andExpect(jsonPath("$[0].posY", equalTo(2)))
                .andExpect(jsonPath("$[0].orientation", equalTo("NORTH")));
    }

    @Test
    public void chargerFichier_TondeuseHorsLimite_Erreur412() throws Exception {
        String contenuFichier = "5 5\n6 2 N\nGAGAGAGAA";
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("fichier", "tondeuse.txt", "text/plain", contenuFichier.getBytes());
        GrilleDTO grilleDto = creerGrilleDto();
        GrilleDTO grilleDtoAvecId = creerGrilleDto()
                .setId(1L);
        given(grilleService.initialiserGrille(refEq(grilleDto)))
                .willReturn(grilleDtoAvecId);
        given(tondeuseService.initialiserTondeuse(any(TondeuseDTO.class)))
                .willThrow(new TondeuseLimiteException("Tondeuse hors limite"));

        mockMvc.perform(multipart("/")
                        .file(mockMultipartFile))
                .andExpect(status().isPreconditionFailed());
    }

    private TondeuseDTO creerTondeuseDto(GrilleDTO grilleDtoAvecId) {
        return new TondeuseDTO()
                .setOrientation(NORTH)
                .setPosX(1)
                .setPosY(2)
                .setGrilleDTO(grilleDtoAvecId);
    }

    private GrilleDTO creerGrilleDto() {
        return new GrilleDTO()
                .setDimX(6)
                .setDimY(6);
    }
}