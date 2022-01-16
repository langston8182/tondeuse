package com.mowitnow.data;

import com.mowitnow.exceptions.DirectionNonTrouveeException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum DirectionEnum {
    GAUCHE("G"),
    DROITE("D");

    private final String valeur;

    DirectionEnum(String valeur) {
        this.valeur = valeur;
    }

    public static DirectionEnum recupererDirectionParValeur(String valeur) {
        return Arrays.stream(DirectionEnum.values())
                .filter(directionEnum -> directionEnum.getValeur().equals(valeur))
                .findFirst()
                .orElseThrow(() -> new DirectionNonTrouveeException("La direction n'existe pas."));
    }
}
