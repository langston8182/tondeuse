package com.mowitnow.controllers;

import com.mowitnow.data.DirectionEnum;
import com.mowitnow.data.GrilleDTO;
import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.exceptions.TondeuseLimiteException;
import com.mowitnow.exceptions.TondeuseNonTrouveeException;
import com.mowitnow.ports.api.GrilleService;
import com.mowitnow.ports.api.TondeuseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<TondeuseDTO> initialiserTondeuse(@RequestParam Long idGrille, @RequestBody TondeuseDTO tondeuseDTO) {
        try {
            GrilleDTO grilleDTO = grilleService.recupererGrille(idGrille);
            tondeuseDTO.setGrilleDTO(grilleDTO);
            TondeuseDTO resultat = tondeuseService.initialiserTondeuse(tondeuseDTO);
            return new ResponseEntity<>(resultat, HttpStatus.CREATED);
        } catch (TondeuseLimiteException ex) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ex.getMessage(), ex);
        }
    }

    @PutMapping("/{id}/pivoter")
    public ResponseEntity<TondeuseDTO> pivoterTondeuse(@PathVariable Long id, @RequestParam String direction) {
        try {
            TondeuseDTO tondeuseDTO = tondeuseService.recupererTondeuse(id);
            TondeuseDTO resultat =
                    tondeuseService.pivoterTondeuse(tondeuseDTO, DirectionEnum.recupererDirectionParValeur(direction));
            return new ResponseEntity<>(resultat, HttpStatus.OK);
        } catch (TondeuseNonTrouveeException ex) {
            throw  new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage(), ex);
        }
    }

    @PutMapping("/{id}/avancer")
    public ResponseEntity<TondeuseDTO> avancerTondeuse(@PathVariable Long id, @RequestParam int nombreCases) {
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
    public ResponseEntity<TondeuseDTO> recupererTondeuse(@PathVariable Long id) {
        try {
            TondeuseDTO tondeuseDto = tondeuseService.recupererTondeuse(id);
            return new ResponseEntity<>(tondeuseDto, HttpStatus.OK);
        } catch (TondeuseNonTrouveeException ex) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage(), ex);
        }
    }
}
