package tech.subluminal.server.logic.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.scene.paint.Color;
import tech.subluminal.server.logic.MessageDistributor;
import tech.subluminal.server.stores.records.GameState;
import tech.subluminal.server.stores.records.Player;
import tech.subluminal.server.stores.records.Star;
import tech.subluminal.shared.messages.ClearGame;
import tech.subluminal.shared.messages.EndGameRes;
import tech.subluminal.shared.messages.GameStartRes;
import tech.subluminal.shared.messages.StartTutorialReq;
import tech.subluminal.shared.messages.Toast;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.Fleet;
import tech.subluminal.shared.stores.records.game.Ship;
import tech.subluminal.shared.util.function.Either;

public class TutorialManager {

  // it should take roughly 20 second for light to traverse the entire map
  private static final double LIGHT_SPEED = 1 / 20.0;
  // ships can cross the map in roughly 3 jumps
  private static final double JUMP_DISTANCE = 1 / 2.5;
  private static final double SHIP_SPEED = LIGHT_SPEED * 0.5;
  private static final double MOTHER_SHIP_SPEED = LIGHT_SPEED * 0.4;
  private static final double DEMAT_RATE = 1.0;
  private static final double GENERATION_RATE = 5.0;

  private final GameStarter starter;
  private final MessageDistributor distributor;

  public TutorialManager(GameStarter starter, MessageDistributor distributor) {
    this.starter = starter;
    this.distributor = distributor;

    distributor.addConnectionOpenedListener(this::attachListeners);
  }

  private void attachListeners(String id, Connection connection) {
    connection.registerHandler(StartTutorialReq.class, StartTutorialReq::fromSON,
        req -> onStartTutorial(id));
  }

  private void nextStage(String id, Runnable nextStage) {
    distributor.sendMessage(new ClearGame(), id);
    new Thread(() -> {
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e); // This should never happen
      }
      nextStage.run();
    }).start();
  }

  private void onStartTutorial(String id) {
    startFirstStage(id);
  }

  private void sendToast(String message, String id) {
    distributor.sendMessage(new Toast(message), id);
  }

  private void startFirstStage(String id) {
    sendToast(
        "Welcome to the tutorial my dear AI.\n"
            + "Try sending your mothership from Alderaan to Tatooine by first clicking Alderaan, then Tatooine and then hitting enter.",
        id);

    Map<String, String> players = new HashMap<>();
    players.put(id, "");

    final Coordinates startCoords = new Coordinates(0.4, 0.5);
    final Coordinates targetCoords = new Coordinates(0.7, 0.7);

    Ship motherShip = new Ship(startCoords, "ship", Collections.emptyList(), "start",
        MOTHER_SHIP_SPEED);
    Player player = new Player(id, "", Collections.emptySet(), motherShip, LIGHT_SPEED);

    Set<Star> stars = new HashSet<>();
    stars.add(new Star(id, 1, startCoords, "start", true,
        JUMP_DISTANCE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
        Double.POSITIVE_INFINITY, "Alderaan"));
    stars.add(new Star(null, 0, targetCoords, "target", false,
        JUMP_DISTANCE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
        Double.POSITIVE_INFINITY, "Tatooine"));

    GameState state = new GameState(id, stars, Collections.singleton(player), LIGHT_SPEED,
        JUMP_DISTANCE, SHIP_SPEED);
    Map<String, Color> colors = new HashMap<>();
    colors.put(id, Color.RED);
    distributor.sendMessage(new GameStartRes(id, colors), id);
    starter.startGame(id, players, state,
        stop -> {
          if (state.getStars().get("target").getCurrent().getState().getPossession() > 0.99) {
            sendToast("You have done well!", id);
            nextStage(id, () -> startSecondStage(id));
            return true;
          }
          return false;
        },
        lp -> {
        });
  }

  private void startSecondStage(String id) {
    sendToast("Now try sending your fleet instead of your mother ship by entering a number before hitting enter", id);

    Map<String, String> players = new HashMap<>();
    players.put(id, "");

    final Coordinates startCoords = new Coordinates(0.6, 0.3);
    final Coordinates targetCoords = new Coordinates(0.3, 0.4);

    Ship motherShip = new Ship(startCoords, "ship", Collections.emptyList(), "start",
        MOTHER_SHIP_SPEED);
    Fleet fleet = new Fleet(startCoords, 1, "fleet0", Collections.emptyList(),
        "start", SHIP_SPEED);
    Player player = new Player(id, "", Collections.emptySet(), motherShip, LIGHT_SPEED);
    player.updateFleet(fleet);

    Set<Star> stars = new HashSet<>();
    stars.add(new Star(id, 1, startCoords, "start", true,
        JUMP_DISTANCE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
        Double.POSITIVE_INFINITY, "Acamar"));
    stars.add(new Star(null, 0, targetCoords, "target", false,
        JUMP_DISTANCE, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY,
        Double.POSITIVE_INFINITY, "Gamma Hydrae"));

    GameState state = new GameState(id, stars, Collections.singleton(player), LIGHT_SPEED,
        JUMP_DISTANCE, SHIP_SPEED);
    Map<String, Color> colors = new HashMap<>();
    colors.put(id, Color.RED);
    distributor.sendMessage(new GameStartRes(id, colors), id);
    AtomicBoolean fleetSent = new AtomicBoolean(false);
    starter.startGame(id, players, state,
        stop -> {
          final Either<Fleet, Void> eitherFleet = player.getFleets()
              .get("fleet0")
              .getLastForPlayer(id);

          if (!fleetSent.get()) {
            boolean fleetHasLeft = eitherFleet.map(
                f -> f.getEndTarget().equals("target"),
                v -> false
            );

            if (fleetHasLeft) {
              fleetSent.set(true);
              sendToast("Well done, now all you have to do is wait.", id);
            }
          }

          boolean fleetHasArrived = eitherFleet.map(
              f -> f.getEndTarget().equals("target") && f.getTargetIDs().isEmpty(),
              v -> false
          );

          if (fleetHasArrived) {
            sendToast("Nice one!", id);
            nextStage(id, () -> startThirdStage(id));
            return true;
          }
          return false;
        },
        lp -> {
        });
  }

  private void startThirdStage(String id) {
    distributor.sendMessage(new EndGameRes(id, id), id);
  }
}
