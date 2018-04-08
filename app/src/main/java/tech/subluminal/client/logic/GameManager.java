package tech.subluminal.client.logic;

import java.util.List;
import java.util.Optional;
import tech.subluminal.client.stores.GameStore;
import tech.subluminal.client.stores.records.game.Player;
import tech.subluminal.shared.messages.GameStateDelta;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.stores.records.game.Star;
import tech.subluminal.shared.util.Synchronized;

public class GameManager {

  private final GameStore gameStore;
  private final Connection connection;

  public GameManager(GameStore gameStore, Connection connection) {
    this.gameStore = gameStore;
    this.connection = connection;

    connection.registerHandler(GameStateDelta.class, GameStateDelta::fromSON,
        this::onGameStateDeltaReceived);
  }

  private void onGameStateDeltaReceived(GameStateDelta delta) {
    //delta.getRemovedPlayers().forEach(gameStore::removePlayer);
    gameStore.getPlayers().consume(players -> {
      players.forEach(syncPlayer -> syncPlayer.consume(player -> {
        List<String> removedFleets = delta.getRemovedFleets().get(player.getId());
        player.getFleets().removeAll(removedFleets);
      }));
    });

    delta.getPlayers().forEach(playerDelta -> {
      Optional<Synchronized<Player>> optPlayer = gameStore.getPlayerByID(playerDelta.getId());
      if (!optPlayer.isPresent()) {
        gameStore.addPlayer(
            new Player(playerDelta.getId(), playerDelta.getMotherShip(), playerDelta.getFleets()));
      } else {
        optPlayer.get().consume(player -> {
          playerDelta.getFleets().forEach(player::updateFleet);
          player.setMotherShip(playerDelta.getMotherShip());
        });
      }
    });

    delta.getStars().forEach(star -> {
      Optional<Synchronized<Star>> optStar = gameStore.getStarByID(star.getId());
      if (!optStar.isPresent()) {
        gameStore.addStar(star);
      } else {
        optStar.get().update(s -> star);
      }
    });
  }
}
