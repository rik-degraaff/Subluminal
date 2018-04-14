package tech.subluminal.server.stores.records;

import static tech.subluminal.shared.util.function.FunctionalUtils.takeWhile;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.util.DeltaTimeUtils;

public class Star extends tech.subluminal.shared.stores.records.game.Star implements Cloneable {

  private final double dematRate;
  private final double nextDemat;
  private final double generationRate;
  private final double nextShipgen;

  public Star(
      String ownerID, double possession, Coordinates coordinates, String id, boolean generating,
      double dematRate, double nextDemat, double generationRate, double nextShipgen
  ) {
    super(ownerID, possession, coordinates, id, generating);
    this.dematRate = dematRate;
    this.nextDemat = nextDemat;
    this.generationRate = generationRate;
    this.nextShipgen = nextShipgen;
  }

  /**
   * Advances time for this star, calculating when ship generation and dematerialiZation take place.
   *
   * @param deltaTime the amount fo time to advance the state by.
   * @param shipGenHandler what should be done when a new ship is generated at the given time.
   * @param dematHandler what should be done when a new dematerialization tick occurs at the given time.
   * @return the state of the star after the time delta.
   */
  public Star advancedBy(
      double deltaTime,
      Consumer<Double> shipGenHandler, Consumer<Double> dematHandler
  ) {
    double newDemat = DeltaTimeUtils
        .advanceBy(deltaTime, nextShipgen, generationRate, shipGenHandler);
    double newGen = DeltaTimeUtils.advanceBy(deltaTime, nextDemat, dematRate, dematHandler);

    return new Star(getOwnerID(), getPossession(), getCoordinates(), getID(), isGenerating(),
        dematRate, newDemat, generationRate, newGen);
  }
}
