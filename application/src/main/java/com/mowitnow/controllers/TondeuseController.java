package com.mowitnow.controllers;

import com.mowitnow.data.DirectionEnum;
import com.mowitnow.data.GrilleDTO;
import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.exceptions.DirectionNonTrouveeException;
import com.mowitnow.exceptions.GrilleNonTrouveeException;
import com.mowitnow.exceptions.TondeuseLimiteException;
import com.mowitnow.exceptions.TondeuseNonTrouveeException;
import com.mowitnow.ports.api.GrilleService;
import com.mowitnow.ports.api.TondeuseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/tondeuse")
public class TondeuseController {
    private final TondeuseService tondeuseService;
    private final GrilleService grilleService;

    public TondeuseController(TondeuseService tondeuseService, GrilleService grilleService) {
        this.tondeuseService = tondeuseService;
        this.grilleService = grilleService;
    }

    @PostMapping
    @ApiOperation("Initialise une tondeuse en fournissant une grille et la position initiale de la tondeuse.")
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_CREATED, message = "Retourne la tondeuse crée avec un identifiant"),
            @ApiResponse(code = HttpServletResponse.SC_PRECONDITION_FAILED,
                    message = "Grille inexistante ou la tondeuse est en dehors des limites de la grille")
    })
    public ResponseEntity<TondeuseDTO> initialiserTondeuse(
            @ApiParam(value = "Identifiant de la grille", example = "1")
            @RequestParam Long idGrille,
            @ApiParam("Tondeuse à initialiser")
            @RequestBody TondeuseDTO tondeuseDTO) {
        try {
            GrilleDTO grilleDTO = grilleService.recupererGrille(idGrille);
            tondeuseDTO.setGrilleDTO(grilleDTO);
            TondeuseDTO resultat = tondeuseService.initialiserTondeuse(tondeuseDTO);
            return new ResponseEntity<>(resultat, HttpStatus.CREATED);
        } catch (TondeuseLimiteException ex) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ex.getMessage(), ex);
        } catch (GrilleNonTrouveeException ex) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ex.getMessage(), ex);
        }
    }

    @PutMapping("/{id}/pivoter")
    @ApiOperation("Pivote la tondeuse à gauche 'G' ou droite 'D'")
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "La nouvelle orientation de la tondeuse"),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "Tondeuse inexistante")
    })
    public ResponseEntity<TondeuseDTO> pivoterTondeuse(
            @ApiParam(value = "Identifiant de la tondeuse", example = "1")
            @PathVariable Long id,
            @ApiParam("Direction de la tondeuse : gauche (G) ou droite (D)")
            @RequestParam String direction) {
        try {
            TondeuseDTO tondeuseDTO = tondeuseService.recupererTondeuse(id);
            TondeuseDTO resultat =
                    tondeuseService.pivoterTondeuse(tondeuseDTO, DirectionEnum.recupererDirectionParValeur(direction));
            return new ResponseEntity<>(resultat, HttpStatus.OK);
        } catch (TondeuseNonTrouveeException ex) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage(), ex);
        } catch (DirectionNonTrouveeException ex) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ex.getMessage(), ex);
        }
    }

    @PutMapping("/{id}/avancer")
    @ApiOperation("Avance la tondeuse du nombre de cases précisé en attribut")
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Retourne la nouvelle position de la tondeuse"),
            @ApiResponse(code = HttpServletResponse.SC_PRECONDITION_FAILED, message = "La tondeuse est en dehors des limites de la grille"),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "Tondeuse inexistante")
    })
    public ResponseEntity<TondeuseDTO> avancerTondeuse(
            @ApiParam(value = "Identifiant de la tondeuse", example = "1")
            @PathVariable Long id,
            @ApiParam(value = "Nombre de case à avancer", example = "3")
            @RequestParam int nombreCases) {
        try {
            TondeuseDTO tondeuseDTO = tondeuseService.recupererTondeuse(id);
            TondeuseDTO resultat = tondeuseService.avancerTondeuse(tondeuseDTO, nombreCases);
            return new ResponseEntity<>(resultat, HttpStatus.OK);
        } catch (TondeuseLimiteException ex) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ex.getMessage(), ex);
        } catch (TondeuseNonTrouveeException ex) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage(), ex);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("Récupère la position de la tondeuse")
    @ApiResponses({
            @ApiResponse(code = HttpServletResponse.SC_OK, message = "Retourne la position de la tondeuse"),
            @ApiResponse(code = HttpServletResponse.SC_NO_CONTENT, message = "Tondeuse inexistante")
    })
    public ResponseEntity<TondeuseDTO> recupererTondeuse(
            @ApiParam(value = "Identifiant de la tondeuse", example = "1")
            @PathVariable Long id) {
        try {
            TondeuseDTO tondeuseDto = tondeuseService.recupererTondeuse(id);
            return new ResponseEntity<>(tondeuseDto, HttpStatus.OK);
        } catch (TondeuseNonTrouveeException ex) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage(), ex);
        }
    }
}
