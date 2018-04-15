package tech.subluminal.client.logic;

import java.util.List;
import java.util.Optional;
import tech.subluminal.client.stores.GameStore;
import tech.subluminal.client.stores.records.game.Player;
import tech.subluminal.shared.messages.GameStateDelta;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.stores.records.game.Star;
import tech.subluminal.shared.util.Synchronized;

/**
 * Listens to game state delta messages and updates the game state accordingly.
 */
public class GameManager {

  private final GameStore gameStore;
  private final Connection connection;

  /**
   * Creates a new game manager with a specified game store and connection.
   *
   * @param gameStore the game store to update.
   * @param connection the connection with which the game manager communicates.
   */
  public GameManager(GameStore gameStore, Connection connection) {
    this.gameStore = gameStore;
    this.connection = connection;

    connection.registerHandler(GameStateDelta.class, GameStateDelta::fromSON,
        this::onGameStateDeltaReceived);
  }

  private void onGameStateDeltaReceived(GameStateDelta delta) {
    delta.getRemovedPlayers().forEach(gameStore.players()::removeByID);
    gameStore.players().getAll().consume(players -> {
      players.forEach(syncPlayer -> syncPlayer.consume(player -> {
        List<String> removedFleets = delta.getRemovedFleets().get(player.getID());
        player.getFleets().removeIf(fleet -> removedFleets.contains(fleet.getID()));
      }));
    });

    delta.getPlayers().forEach(playerDelta -> {
      Optional<Synchronized<Player>> optPlayer = gameStore.players().getByID(playerDelta.getID());
      if (!optPlayer.isPresent()) {
        gameStore.players().add(
            new Player(playerDelta.getID(), playerDelta.getMotherShip(), playerDelta.getFleets()));
      } else {
        optPlayer.get().consume(player -> {
          playerDelta.getFleets().forEach(player::updateFleet);
          player.setMotherShip(playerDelta.getMotherShip());
        });
      }
    });

    delta.getStars().forEach(star -> {
      Optional<Synchronized<Star>> optStar = gameStore.stars().getByID(star.getID());
      if (!optStar.isPresent()) {
        gameStore.stars().add(star);
      } else {
        optStar.get().update(s -> star);
      }
    });
  }
}
