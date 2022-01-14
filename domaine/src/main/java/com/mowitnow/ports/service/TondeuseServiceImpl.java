package com.mowitnow.ports.service;

import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.exceptions.UtilisationException;
import com.mowitnow.ports.api.TondeuseService;
import com.mowitnow.ports.spi.TondeusePersistence;

public class TondeuseServiceImpl implements TondeuseService {
  private final TondeusePersistence tondeusePersistence;

  public TondeuseServiceImpl(TondeusePersistence tondeusePersistence) {
    this.tondeusePersistence = tondeusePersistence;
  }

  @Override
  public TondeuseDTO initialiserTondeuse(TondeuseDTO tondeuseDTO) {
    verifieSiTondeuseDepasseZone(tondeuseDTO);

    return tondeusePersistence.initialiserTondeuse(tondeuseDTO);
  }

  @Override
  public TondeuseDTO modifierTondeuse(TondeuseDTO tondeuseDTO) {
    return tondeusePersistence.modifierTondeuse(tondeuseDTO);
  }

  @Override
  public TondeuseDTO recupererTondeuse(Long id) {
    return tondeusePersistence.recupererTondeuse(id);
  }

  private void verifieSiTondeuseDepasseZone(TondeuseDTO tondeuseDTO) {
    String message = "La position de la tondeuse dépasse la zone de tonte.";
    if (tondeuseDTO.getPosX() > tondeuseDTO.getGrilleDTO().getDimX()) {
      throw new UtilisationException(message);
    }
    if (tondeuseDTO.getPosY() > tondeuseDTO.getGrilleDTO().getDimY()) {
      throw new UtilisationException(message);
    }
  }

}
