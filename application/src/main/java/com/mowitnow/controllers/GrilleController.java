package com.mowitnow.controllers;

import com.mowitnow.data.GrilleDTO;
import com.mowitnow.ports.api.GrilleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grille")
public class GrilleController {
    private final GrilleService grilleService;

    public GrilleController(GrilleService grilleService) {
        this.grilleService = grilleService;
    }

    @PostMapping
    public ResponseEntity<GrilleDTO> initialiserGrille(@RequestParam int posX, @RequestParam int posY) {
        GrilleDTO grilleDTO = new GrilleDTO()
                .setDimX(posX + 1)
                .setDimY(posY + 1);
        GrilleDTO resultat = grilleService.initialiserGrille(grilleDTO);
        return new ResponseEntity<>(resultat, HttpStatus.CREATED);
    }
}
