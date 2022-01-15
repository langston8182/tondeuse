package com.mowitnow.data;

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
                .orElseThrow(() -> new IllegalStateException("La direction n'existe pas."));
    }
}
