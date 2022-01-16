package com.mowitnow.controllers;

import com.mowitnow.data.GrilleDTO;
import com.mowitnow.ports.api.GrilleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/grille")
public class GrilleController {
    private final GrilleService grilleService;

    public GrilleController(GrilleService grilleService) {
        this.grilleService = grilleService;
    }

    @PostMapping
    @ApiOperation("Initialise une grille en fournissant en paramètre la position X et Y du coin supérieur droit")
    @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Retourne la grille crée avec un identifiant")
    public ResponseEntity<GrilleDTO> initialiserGrille(
            @ApiParam("Position X du coin supérieur droit")
            @RequestParam int posX,
            @ApiParam("Position Y du coin supérieur droit")
            @RequestParam int posY) {
        GrilleDTO grilleDTO = new GrilleDTO()
                .setDimX(posX + 1)
                .setDimY(posY + 1);
        GrilleDTO resultat = grilleService.initialiserGrille(grilleDTO);
        return new ResponseEntity<>(resultat, HttpStatus.CREATED);
    }
}
