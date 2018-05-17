package tech.subluminal.server.stores.records;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.GameObject;
import tech.subluminal.shared.util.DeltaTimeUtils;

/**
 * Represents a signal a player sent request a fleet to be sent from a star.
 */
public class Signal extends GameObject implements Comparable<Signal> {

  private final String starID;
  private final String playerID;
  private final double timeToArrive;
  private final int amount;
  private final List<String> targets;

  public Signal(Coordinates origin, String id, String starID, List<String> targets, String playerID,
      Coordinates starCoordinates, int amount, double lightSpeed) {
    this(origin, id, starID, playerID, origin.getDistanceFrom(starCoordinates) / lightSpeed, amount,
        targets);
  }

  public Signal(Coordinates origin, String id, String starID, String playerID,
      double timeToArrive, int amount, List<String> targets) {
    super(origin, id);
    this.starID = starID;
    this.playerID = playerID;
    this.timeToArrive = timeToArrive;
    this.amount = amount;
    this.targets = targets;
  }

  /**
   * @return the id of the star this signal is directed at.
   */
  public String getStarID() {
    return starID;
  }

  /**
   * @return a list containing all targets the fleet should fly over.
   */
  public List<String> getTargets() {
    return targets;
  }

  /**
   * @return the id of the player this signal was sent by.
   */
  public String getPlayerID() {
    return playerID;
  }

  /**
   * @return the maximum amount of ships that should be sent.
   */
  public int getAmount() {
    return amount;
  }

  /**
   * @return the time needed for the signal to arrive at the target.
   */
  public double getTimeToArrive() {
    return timeToArrive;
  }

  /**
   * Returns a copy of this signal from the future.
   *
   * @param deltaTime the time in seconds to skip ahead by.
   * @param signalArrivedHandler the handler that will be called when the signal arrives.
   * @return the updated signal if the signal doesn't arrive, empty otherwise.
   */
  public Optional<Signal> advanced(double deltaTime, Consumer<Double> signalArrivedHandler) {
    double newArrival = DeltaTimeUtils
        .advanceBy(deltaTime, timeToArrive, 10_000_000.00, signalArrivedHandler);

    return newArrival < 1_000_000.00
        ? Optional.of(
            new Signal(getCoordinates(), getID(), getStarID(), getPlayerID(),
            newArrival, getAmount(), getTargets()))
        : Optional.empty();
  }

  /**
   * @return a value greater than 0 if this signal will arrive before the other.
   */
  @Override
  public int compareTo(Signal o) {
    return Double.compare(o.timeToArrive, timeToArrive);
  }
}
