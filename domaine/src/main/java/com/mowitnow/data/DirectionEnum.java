package com.mowitnow.data;

import lombok.Getter;

@Getter
public enum DirectionEnum {
    GAUCHE("D"),
    DROITE("D");

    private String valeur;

    DirectionEnum(String valeur) {
        this.valeur = valeur;
    }
}
