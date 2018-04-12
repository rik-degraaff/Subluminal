package tech.subluminal.server.logic.game;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import tech.subluminal.shared.stores.records.game.GameObject;
import tech.subluminal.shared.stores.records.game.Ship;
import tech.subluminal.shared.util.function.Either;
import tech.subluminal.shared.util.function.Left;
import tech.subluminal.shared.util.function.Right;

public class GameHistory<E extends GameObject> extends MultiHistory<String, GameHistoryEntry<E>> {

  private final double lightSpeed;

  /**
   * @param playerIDs a set of ids identifying players that can later be used to access the
   * history.
   * @param initial the initial state of the game object.
   * @param lightSpeed the speed of light in units per second.
   */
  public GameHistory(Set<String> playerIDs, GameHistoryEntry<E> initial, double lightSpeed) {
    super(playerIDs, initial);
    this.lightSpeed = lightSpeed;
  }

  /**
   * @param playerID the id of the player for whom the state should be read.
   * @param motherShip the mother ship entry of the user.
   * @return the latest state if the information could have reached the player's mother ship by now.
   * If this function returns a Right (Void) this means that the object was destroyed and that
   * information has reached the player.
   */
  public Optional<Either<E, Void>> getLatestStateForPlayerIfWithinReach(String playerID,
      GameHistoryEntry<Ship> motherShip) {
    return getLatestForKeyIf(playerID,
        entry -> {
          double distance = entry.getState().getDistanceFrom(motherShip.getState());
          double timeDiff = (motherShip.getTime() - entry.getTime()) / 1000;
          return distance / timeDiff < lightSpeed;
        })
        .map(e -> e.isDestroyed() ? new Left<>(e.getState()) : new Right<>(null));
  }

  /**
   * @return true if this object has been destroyed and that information has reached all players.
   */
  public boolean canBeRemoved() {
    return getCurrent().isDestroyed() && history.values().stream().allMatch(Collection::isEmpty);
  }
}
