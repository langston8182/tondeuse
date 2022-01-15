package com.mowitnow.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mowitnow.data.GrilleDTO;
import com.mowitnow.ports.api.GrilleService;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(GrilleController.class)
@ContextConfiguration
public class GrilleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GrilleService grilleService;

    @Configuration
    @ComponentScan(basePackageClasses = {GrilleService.class, GrilleController.class},
            useDefaultFilters = false, includeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                    classes = {GrilleService.class, GrilleController.class}) })
    public static class TestConfiguration {
        //
    }

    @Test
    public void initialiserGrille() throws Exception {
        GrilleDTO grilleDto = new GrilleDTO()
                .setDimX(5)
                .setDimY(5);
        GrilleDTO grilleDtoAvecId = new GrilleDTO()
                .setId(1L)
                .setDimY(5)
                .setDimX(5);
        given(grilleService.initialiserGrille(grilleDto))
                .willReturn(grilleDtoAvecId);

        mockMvc.perform(post("/grille")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(grilleDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)));
    }
}