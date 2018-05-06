package tech.subluminal.client.logic;

import static tech.subluminal.shared.util.function.IfPresent.ifPresent;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import tech.subluminal.client.presentation.GamePresenter;
import tech.subluminal.client.stores.GameStore;
import tech.subluminal.client.stores.records.game.OwnerPair;
import tech.subluminal.shared.messages.EndGameRes;
import tech.subluminal.shared.messages.FleetMoveReq;
import tech.subluminal.shared.messages.GameLeaveReq;
import tech.subluminal.shared.messages.GameLeaveRes;
import tech.subluminal.shared.messages.GameStartRes;
import tech.subluminal.shared.messages.GameStateDelta;
import tech.subluminal.shared.messages.LoginRes;
import tech.subluminal.shared.messages.MotherShipMoveReq;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.son.SONRepresentable;
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

    connection.registerHandler(
        GameStateDelta.class, GameStateDelta::fromSON, this::onGameStateDeltaReceived);
    connection.registerHandler(
        LoginRes.class, LoginRes::fromSON, this::onLoginRes);
    connection.registerHandler(
        GameStartRes.class, GameStartRes::fromSON, this::onGameStart);
    connection.registerHandler(
        EndGameRes.class, EndGameRes::fromSON, this::onEndGameRes);
    connection.registerHandler(
        GameLeaveRes.class, GameLeaveRes::fromSON, req -> onGameLeave());
  }


  private void onEndGameRes(EndGameRes res) {
    gamePresenter.onEndGame(res.getGameID(), res.getWinnerID());
  }

  private void onGameStart(GameStartRes res) {
    gamePresenter.clearMap();
    gameStore.inGame().set(true);
    gamePresenter.setPlayerColors(res.getPlayerColor());
    gamePresenter.setGameID(res.getGameID());
    gamePresenter.setGameStore(gameStore);
  }

  private void onLoginRes(LoginRes res) {
    gamePresenter.setUserID();
  }

  private void onGameStateDeltaReceived(GameStateDelta delta) {
    delta.getRemovedMotherShips().forEach(gameStore.motherShips()::removeByID);
    delta.getPlayers().forEach(player -> {
      ifPresent(player.getMotherShip())
          .then(motherShip -> {
            gameStore.motherShips().add(new OwnerPair<>(player.getID(), motherShip));
          })
          .els(() -> {
            gameStore.motherShips().removeByID(player.getID());
          });
      player.getFleets().forEach(fleet -> {
        gameStore.fleets().add(new OwnerPair<>(player.getID(), fleet));
      });
    });

    delta.getRemovedFleets().forEach((playerID, removedFleets) -> {
      removedFleets.forEach(gameStore.fleets()::removeByID);
    });

    delta.getStars().forEach(star -> {
      Optional<Synchronized<Star>> optStar = gameStore.stars().getByID(star.getID());
      if (!optStar.isPresent()) {
        gameStore.stars().add(star);
      } else {
        optStar.get().update(s -> star);
      }
    });
    if (gameStore.inGame().get().use(Function.identity()).orElse(false)) {
      gamePresenter.removeMotherShips(delta.getRemovedMotherShips());
      gamePresenter.removeFleets(delta.getRemovedFleets().values().stream().flatMap(List::stream)
          .collect(Collectors.toList()));
      gamePresenter.update();
    }
  }

  @Override
  public void sendShips(List<String> stars, int amount) {
    connection.sendMessage(new FleetMoveReq(stars.get(0), amount, stars));
  }

  @Override
  public void sendMothership(List<String> stars) {
    connection.sendMessage(new MotherShipMoveReq(stars));
  }

  @Override
  public void leaveGame() {
    connection.sendMessage(new GameLeaveReq());
  }

  private void onGameLeave() {
    gameStore.inGame().set(false);
    gameStore.motherShips().clear();
    gameStore.stars().clear();
    gameStore.fleets().clear();
  }
}
