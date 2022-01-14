package com.mowitnow.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class TondeuseDTO {
  private Long id;
  private int posX;
  private int posY;
  private OrientationEnum orientation;
  private GrilleDTO grilleDTO;
}
