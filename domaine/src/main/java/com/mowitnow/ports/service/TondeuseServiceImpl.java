package com.mowitnow.ports.service;

import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.ports.api.TondeuseService;
import com.mowitnow.ports.spi.TondeusePersistence;

public class TondeuseServiceImpl implements TondeuseService {
  private final TondeusePersistence tondeusePersistence;

  public TondeuseServiceImpl(TondeusePersistence tondeusePersistence) {
    this.tondeusePersistence = tondeusePersistence;
  }

  @Override
  public TondeuseDTO initialiserTondeuse(TondeuseDTO tondeuseDTO) {
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
}
