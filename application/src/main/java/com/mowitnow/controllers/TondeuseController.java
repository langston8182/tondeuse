package com.mowitnow.controllers;

import com.mowitnow.data.DirectionEnum;
import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.exceptions.LimiteTondeuseException;
import com.mowitnow.ports.api.TondeuseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/tondeuse")
public class TondeuseController {
    private final TondeuseService tondeuseService;

    public TondeuseController(TondeuseService tondeuseService) {
        this.tondeuseService = tondeuseService;
    }

    @PostMapping
    public ResponseEntity<TondeuseDTO> initialiserTondeuse(@RequestBody TondeuseDTO tondeuseDTO) {
        try {
            TondeuseDTO resultat = tondeuseService.initialiserTondeuse(tondeuseDTO);
            return new ResponseEntity<>(resultat, HttpStatus.CREATED);
        } catch(LimiteTondeuseException ex) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ex.getMessage(), ex);
        }
    }

    @PutMapping("/{id}/pivoter")
    public ResponseEntity<TondeuseDTO> pivoterTondeuse(@PathVariable Long id, @RequestParam String direction) {
        TondeuseDTO tondeuseDTO = tondeuseService.recupererTondeuse(id);
        TondeuseDTO resultat =
                tondeuseService.pivoterTondeuse(tondeuseDTO, DirectionEnum.recupererDirectionParValeur(direction));
        return new ResponseEntity<>(resultat, HttpStatus.OK);
    }

    @PutMapping("/{id}/avancer")
    public ResponseEntity<TondeuseDTO> avancerTondeuse(@PathVariable Long id, @RequestParam int nombreCases) {
        try {
            TondeuseDTO tondeuseDTO = tondeuseService.recupererTondeuse(id);
            TondeuseDTO resultat = tondeuseService.avancerTondeuse(tondeuseDTO, nombreCases);
            return new ResponseEntity<>(resultat, HttpStatus.OK);
        } catch (LimiteTondeuseException ex) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, ex.getMessage(), ex);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TondeuseDTO> recupererTondeuse(@PathVariable Long id) {
        TondeuseDTO tondeuseDto = tondeuseService.recupererTondeuse(id);
        return new ResponseEntity<>(tondeuseDto, HttpStatus.OK);
    }
}
