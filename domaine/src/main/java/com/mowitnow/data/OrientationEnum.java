package com.mowitnow.data;

import lombok.Getter;

@Getter
public enum OrientationEnum {
  WEST("W"),
  EAST("E"),
  NORTH("N"),
  SOUTH("S");

  private String orientation;

  OrientationEnum(String valeur) {
    this.orientation = valeur;
  }
}
