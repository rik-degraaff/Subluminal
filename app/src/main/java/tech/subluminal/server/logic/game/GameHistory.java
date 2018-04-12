package tech.subluminal.server.logic.game;

import java.util.Optional;
import java.util.Set;
import tech.subluminal.shared.stores.records.game.GameObject;
import tech.subluminal.shared.stores.records.game.Ship;

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
   */
  public Optional<E> getLatestStateForPlayerIfWithinReach(String playerID,
      GameHistoryEntry<Ship> motherShip) {
    return getLatestForKeyIf(playerID,
        entry -> {
          double distance = entry.getState().getDistanceFrom(motherShip.getState());
          double timeDiff = (motherShip.getTime() - entry.getTime()) / 1000;
          return distance / timeDiff < lightSpeed;
        })
        .map(GameHistoryEntry::getState);
  }
}
