package com.mowitnow.entities;

import com.mowitnow.data.OrientationEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Tondeuse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int posX;
  private int posY;
  private OrientationEnum orientation;

  @ManyToOne
  private Grille grille;
}
