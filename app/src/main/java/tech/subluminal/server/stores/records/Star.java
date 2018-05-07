package tech.subluminal.server.stores.records;

import java.util.function.Consumer;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.util.DeltaTimeUtils;

public class Star extends tech.subluminal.shared.stores.records.game.Star implements Cloneable {

  private final double dematRate;
  private final double nextDemat;
  private final double generationRate;
  private final double nextShipgen;

  public Star(
      String ownerID, double possession, Coordinates coordinates, String id, boolean generating,
      double jump, double dematRate, double nextDemat, double generationRate, double nextShipgen, String name
  ) {
    super(ownerID, possession, coordinates, id, generating, jump, name);
    this.dematRate = dematRate;
    this.nextDemat = nextDemat;
    this.generationRate = generationRate;
    this.nextShipgen = nextShipgen;
  }

  public double getDematRate() {
    return dematRate;
  }

  public double getNextDemat() {
    return nextDemat;
  }

  public double getGenerationRate() {
    return generationRate;
  }

  public double getNextShipgen() {
    return nextShipgen;
  }

  /**
   * Advances time for this star, calculating when ship generation and dematerialiZation take
   * place.
   *
   * @param deltaTime the amount fo time to advance the state by.
   * @param shipGenHandler what should be done when a new ship is generated at the given time.
   * @param dematHandler what should be done when a new dematerialization tick occurs at the given
   * time.
   * @return the state of the star after the time delta.
   */
  public Star advancedBy(
      double deltaTime,
      Consumer<Double> shipGenHandler, Consumer<Double> dematHandler
  ) {
    double newGen = DeltaTimeUtils
        .advanceBy(deltaTime, nextShipgen, generationRate, shipGenHandler);
    double newDemat = DeltaTimeUtils.advanceBy(deltaTime, nextDemat, dematRate, dematHandler);

    return new Star(getOwnerID(), getPossession(), getCoordinates(), getID(), isGenerating(),
        getJump(), dematRate, newDemat, generationRate, newGen, getName());
  }
}
