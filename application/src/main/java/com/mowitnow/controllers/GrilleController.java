package com.mowitnow.controllers;

import com.mowitnow.data.GrilleDTO;
import com.mowitnow.ports.api.GrilleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grille")
public class GrilleController {
    private final GrilleService grilleService;

    public GrilleController(GrilleService grilleService) {
        this.grilleService = grilleService;
    }

    @PostMapping
    public ResponseEntity<GrilleDTO> initialiserGrille(@RequestBody GrilleDTO grilleDTO) {
        GrilleDTO resultat = grilleService.initialiserGrille(grilleDTO);
        return new ResponseEntity<>(resultat, HttpStatus.CREATED);
    }
}
