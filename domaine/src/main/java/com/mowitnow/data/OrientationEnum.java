package com.mowitnow.data;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum OrientationEnum {
    NORTH("N", 360),
    EAST("E", 90),
    SOUTH("S", 180),
    WEST("W", 270);

    private String orientation;
    private int valeur;

    OrientationEnum(String orientation, int valeur) {
        this.orientation = orientation;
        this.valeur = valeur;
    }

    public static OrientationEnum recupererOrientationParValeur(int valeur) {
        return Arrays.stream(OrientationEnum.values())
                .filter(orientationEnum -> orientationEnum.getValeur() == valeur)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("La valeur n'existe pas"));
    }
}
