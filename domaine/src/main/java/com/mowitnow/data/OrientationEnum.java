package com.mowitnow.data;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrientationEnum {
    NORTH("N", 360),
    EAST("E", 90),
    SOUTH("S", 180),
    WEST("W", 270);

    private final String nom;
    private final int valeur;

    OrientationEnum(String orientation, int valeur) {
        this.nom = orientation;
        this.valeur = valeur;
    }

    public static OrientationEnum recupererOrientationParNom(String nom) {
        return Arrays.stream(OrientationEnum.values())
                .filter(orientationEnum -> orientationEnum.getNom().equals(nom))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("La valeur n'existe pas"));
    }

    public static OrientationEnum recupererOrientationParValeur(int valeur) {
        return Arrays.stream(OrientationEnum.values())
                .filter(orientationEnum -> orientationEnum.getValeur() == valeur)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("La valeur n'existe pas"));
    }
}
