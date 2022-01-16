package com.mowitnow.ports.service;

import com.mowitnow.data.DirectionEnum;
import com.mowitnow.data.OrientationEnum;
import com.mowitnow.data.TondeuseDTO;
import com.mowitnow.exceptions.DirectionNonTrouveeException;
import com.mowitnow.exceptions.TondeuseLimiteException;
import com.mowitnow.exceptions.TondeuseNonTrouveeException;
import com.mowitnow.exceptions.UtilisationException;
import com.mowitnow.ports.api.TondeuseService;
import com.mowitnow.ports.spi.TondeusePersistence;

import static com.mowitnow.data.OrientationEnum.*;
import static java.util.Optional.ofNullable;

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
  public TondeuseDTO avancerTondeuse(TondeuseDTO tondeuseDTO, int nombreCase) throws TondeuseLimiteException {
    int nouvellePosX = tondeuseDTO.getPosX();
    int nouvellePosY = tondeuseDTO.getPosY();

    switch (tondeuseDTO.getOrientation()) {
      case NORTH:
        nouvellePosY += nombreCase;
        break;

      case SOUTH:
        nouvellePosY -= nombreCase;
        break;

      case EAST:
        nouvellePosX += nombreCase;
        break;

      case WEST:
        nouvellePosX -= nombreCase;
        break;
    }

    if (estHorsLimite(tondeuseDTO, nouvellePosX, nouvellePosY)) {
      throw new TondeuseLimiteException("La tondeuse est hors limite.");
    }

    tondeuseDTO.setPosX(nouvellePosX);
    tondeuseDTO.setPosY(nouvellePosY);
    return tondeusePersistence.modifierTondeuse(tondeuseDTO);
  }

  @Override
  public TondeuseDTO pivoterTondeuse(TondeuseDTO tondeuseDTO, DirectionEnum direction) {
    OrientationEnum orientationEnum = tondeuseDTO.getOrientation();
    int nouvelleValeur = recupererNouvelleValeur(direction, orientationEnum);
    TondeuseDTO tondeuseDtoModifiee = tondeuseDTO
            .setOrientation(recupererOrientationParValeur(nouvelleValeur));
    return tondeusePersistence.modifierTondeuse(tondeuseDtoModifiee);
  }

  @Override
  public TondeuseDTO recupererTondeuse(Long id) {
    return ofNullable(tondeusePersistence.recupererTondeuse(id))
            .orElseThrow(() -> new TondeuseNonTrouveeException("Tondeuse non trouvée"));
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

  private boolean estHorsLimite(TondeuseDTO tondeuseDTO, int nouvellePosX, int nouvellePosY) {
    return nouvellePosX > tondeuseDTO.getGrilleDTO().getDimX() - 1 ||
            nouvellePosY > tondeuseDTO.getGrilleDTO().getDimY() -1 ||
            nouvellePosY < 0 ||
            nouvellePosX < 0;
  }

  private int recupererNouvelleValeur(DirectionEnum direction, OrientationEnum orientationEnum) {
    int nouvelleValeur = 0;
    switch (direction) {
      case GAUCHE:
        nouvelleValeur = (orientationEnum.getValeur()) - 90 % 360;
        break;

      case DROITE:
        nouvelleValeur = (orientationEnum.getValeur() + 90) % 360;
        break;

      default:
        throw new DirectionNonTrouveeException("Direction inexistante");
    }
    return nouvelleValeur == 0 ? 360 : nouvelleValeur;
  }
}
