package tech.subluminal.client.logic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.presentation.GamePresenter;
import tech.subluminal.client.stores.GameStore;
import tech.subluminal.client.stores.records.game.OwnerPair;
import tech.subluminal.shared.messages.FleetMoveReq;
import tech.subluminal.shared.messages.GameStateDelta;
import tech.subluminal.shared.messages.MotherShipMoveReq;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.stores.records.game.Ship;
import tech.subluminal.shared.stores.records.game.Star;
import tech.subluminal.shared.util.Synchronized;

/**
 * Listens to game state delta messages and updates the game state accordingly.
 */
public class GameManager implements GamePresenter.Delegate {

  private final GameStore gameStore;
  private final Connection connection;
  private final GamePresenter gamePresenter;

  /**
   * Creates a new game manager with a specified game store and connection.
   *
   * @param gameStore the game store to update.
   * @param connection the connection with which the game manager communicates.
   */
  public GameManager(GameStore gameStore, Connection connection, GamePresenter gamePresenter) {
    this.gameStore = gameStore;
    this.connection = connection;
    this.gamePresenter = gamePresenter;

    gamePresenter.setGameDelegate(this);

    connection.registerHandler(GameStateDelta.class, GameStateDelta::fromSON,
        this::onGameStateDeltaReceived);
  }

  private void onGameStateDeltaReceived(GameStateDelta delta) {
    //delta.getRemovedPlayers().forEach(gameStore.players()::removeByID);
    delta.getPlayers().forEach(player -> {
      Ship motherShip = player.getMotherShip();
      gameStore.motherShips().add(new OwnerPair<>(player.getID(), motherShip));
      player.getFleets().forEach(fleet -> {
        gameStore.fleets().add(new OwnerPair<>(player.getID(), fleet));
      });
    });

    delta.getRemovedFleets().forEach((playerID, removedFleets) -> {
      removedFleets.forEach(gameStore.fleets()::removeByID);
    });

    Logger.debug("Delta: " + delta.asSON().asString());
    // TODO: removed players

    delta.getStars().forEach(star -> {
      Optional<Synchronized<Star>> optStar = gameStore.stars().getByID(star.getID());
      if (!optStar.isPresent()) {
        gameStore.stars().add(star);
      } else {
        optStar.get().update(s -> star);
      }
    });
    gamePresenter.update();
  }

  @Override
  public void sendShips(List<Star> stars, int amount) {
    connection.sendMessage(new FleetMoveReq(stars.get(0).getID(), amount,
        stars.stream().map(Star::getID).collect(Collectors.toList())));
  }

  @Override
  public void sendMothership(List<Star> star) {
    connection.sendMessage(
        new MotherShipMoveReq(star.stream().map(Star::getID).collect(Collectors.toList())));
  }
}
