package com.mowitnow.controllers;

import com.mowitnow.data.DirectionEnum;
import com.mowitnow.data.GrilleDTO;
import com.mowitnow.data.OrientationEnum;
import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.exceptions.DirectionNonTrouveeException;
import com.mowitnow.exceptions.TondeuseLimiteException;
import com.mowitnow.ports.api.GrilleService;
import com.mowitnow.ports.api.TondeuseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;

@RestController
public class ApiController {
    Logger logger = LoggerFactory.getLogger(ApiController.class);
    private static final String SEPARATEUR = " ";
    private static final char AVANCER = 'A';
    private final GrilleService grilleService;
    private final TondeuseService tondeuseService;
    private GrilleDTO grilleDtoEnregistre;
    private final List<TondeuseDTO> tondeuseDTOS = new ArrayList<>();

    public ApiController(GrilleService grilleService, TondeuseService tondeuseService) {
        this.grilleService = grilleService;
        this.tondeuseService = tondeuseService;
    }

    @PostMapping
    @ApiOperation("Upload un fichier pour lancer la tondeuse automatisée")
    public ResponseEntity<List<TondeuseDTO>> chargerFichier(
            @ApiParam("Fichier contenant l'itnitialisation de la grille, du ou des tondeuses, ainsi que tous les mouvements")
            @RequestParam("fichier") MultipartFile fichier) throws IOException {
        try {
            new BufferedReader(new InputStreamReader(fichier.getInputStream()))
                    .lines()
                    .forEach(this::appelerMethode);
        } catch (TondeuseLimiteException | DirectionNonTrouveeException ex) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ex.getMessage(), ex);
        }
        return new ResponseEntity<>(tondeuseDTOS, HttpStatus.OK);
    }

    private void appelerMethode(String ligne) {
        long nombreElements = stream(ligne.split(SEPARATEUR))
                .count();
        if (nombreElements == 2) {
            GrilleDTO grilleDto = new GrilleDTO()
                    .setDimX(parseInt(ligne.split(SEPARATEUR)[0]) + 1)
                    .setDimY(parseInt(ligne.split(SEPARATEUR)[1]) + 1);
            grilleDtoEnregistre = grilleService.initialiserGrille(grilleDto);
        } else if (nombreElements == 3) {
            TondeuseDTO tondeuseDTO = new TondeuseDTO()
                    .setGrilleDTO(grilleDtoEnregistre)
                    .setOrientation(OrientationEnum.recupererOrientationParNom(ligne.split(SEPARATEUR)[2]))
                    .setPosX(parseInt(ligne.split(SEPARATEUR)[0]))
                    .setPosY(parseInt(ligne.split(SEPARATEUR)[1]));
            TondeuseDTO tondeuseDtoEnregistree = tondeuseService.initialiserTondeuse(tondeuseDTO);
            tondeuseDTOS.add(tondeuseDtoEnregistree);
        } else {
            ligne.chars()
                    .mapToObj(i -> (char) i)
                    .forEach(this::appelerMethodeTondeuse);
        }
    }

    private void appelerMethodeTondeuse(char valeur) {
        TondeuseDTO tondeuseDTO = tondeuseDTOS.get(tondeuseDTOS.size() - 1);
        if (valeur != AVANCER) {
            tondeuseService.pivoterTondeuse(tondeuseDTO, DirectionEnum.recupererDirectionParValeur(valeur + ""));
        } else {
            try {
                tondeuseService.avancerTondeuse(tondeuseDTO, 1);
            } catch (TondeuseLimiteException ex) {
                logger.warn("Tondeuse hors limite.");
            }
        }
    }
}
