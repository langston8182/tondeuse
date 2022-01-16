package com.mowitnow.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class TondeuseDTO {
  private Long id;
  private int posX;
  private int posY;
  private OrientationEnum orientation;
  private GrilleDTO grilleDTO;
}
