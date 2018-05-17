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

    final String start = "Alderaan";
    final String target = "Tatooine";

    final Map<String, Coordinates> stars = new HashMap<>();
    stars.put(start, new Coordinates(0.4, 0.5));
    stars.put(target, new Coordinates(0.7, 0.7));

    final Map<String, String> starPlayers = new HashMap<>();
    starPlayers.put(start, id);

    final TutorialLevel tutorialLevel = new TutorialLevel(
        stars, id, starPlayers, JUMP_DISTANCE, LIGHT_SPEED, MOTHER_SHIP_SPEED, SHIP_SPEED,
        Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, lp -> {
    });

    tutorialLevel.addTrigger(
        state -> target.equals(
            state.getPlayers().get(id).getMotherShip().getCurrent().getState().getEndTarget()),
        () -> {
          sendToast("Space is big; this might take a while...", id);
        });

    tutorialLevel.addTrigger(
        state -> state.getStars().get(target).getCurrent().getState().getPossession() > 0.01,
        () -> {
          sendToast("This star should be fully colonized in a few years at this rate.", id);
        });

    tutorialLevel.addTerminatingTrigger(
        state -> state.getStars().get(target).getCurrent().getState().getPossession() > 0.99,
        () -> {
          sendToast("You have done well.", id);
          nextStage(id, () -> startSecondStage(id));
        });

    Map<String, Color> colors = new HashMap<>();
    colors.put(id, Color.RED);
    tutorialLevel.startGame(starter, distributor, colors);
  }

  private void startSecondStage(String id) {
    sendToast("Now try sending your fleet instead of your mother ship by entering a number before hitting enter", id);

    final String start = "Acamar";
    final String target = "Gamma Hydrae";

    final Map<String, Coordinates> stars = new HashMap<>();
    stars.put(start, new Coordinates(0.6, 0.3));
    stars.put(target, new Coordinates(0.3, 0.4));

    final Map<String, String> starPlayers = new HashMap<>();
    starPlayers.put(start, id);

    final TutorialLevel tutorialLevel = new TutorialLevel(
        stars, id, starPlayers, JUMP_DISTANCE, LIGHT_SPEED, MOTHER_SHIP_SPEED, SHIP_SPEED,
        Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, lp -> {
    });

    final String fleet = "fleet";
    tutorialLevel.addFleet(id, fleet, start, 1);

    tutorialLevel.addTrigger(
        state ->
            state.getPlayers().get(id).getFleets().get(fleet).getCurrent().getState()
                .getEndTarget()
                .equals(start)
            && state.getPlayers().get(id).getMotherShip().getCurrent().getState()
                .getEndTarget()
                .equals(target),
        () -> sendToast("Make sure you send your fleet, not your mother ship.\n"
            + "Remember to enter a number before hitting enter", id)
    );

    tutorialLevel.addTrigger(
        state -> state.getPlayers().get(id).getFleets().get(fleet).getLastForPlayer(id)
            .map(
                f -> f.getEndTarget().equals(target),
                v -> false
            ),
        () -> sendToast("Well done, now all you have to do is wait.", id)
    );

    tutorialLevel.addTerminatingTrigger(
        state -> state.getPlayers().get(id).getFleets().get(fleet).getLastForPlayer(id)
            .map(
                f -> f.getEndTarget().equals(target) && f.getTargetIDs().isEmpty(),
                v -> false
            ),
        () -> {
          sendToast("Nice one!", id);
          nextStage(id, () -> startThirdStage(id));
        }
    );

    Map<String, Color> colors = new HashMap<>();
    colors.put(id, Color.RED);
    tutorialLevel.startGame(starter, distributor, colors);
  }

  private void startThirdStage(String id) {
    
  }
}
