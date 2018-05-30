package tech.subluminal.server.logic.game;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import tech.subluminal.server.logic.MessageDistributor;
import tech.subluminal.shared.messages.ClearGame;
import tech.subluminal.shared.messages.EndGameRes;
import tech.subluminal.shared.messages.GameLeaveReq;
import tech.subluminal.shared.messages.StartTutorialReq;
import tech.subluminal.shared.messages.Toast;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.stores.records.game.Coordinates;

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
    connection.registerHandler(GameLeaveReq.class, GameLeaveReq::fromSON,
        req -> onEndTutorial(id));
  }

  private void onEndTutorial(String id) {
    starter.stopGame(id);
  }

  private void nextStage(String id, Runnable nextStage) {
    starter.stopGame(id);
    new Thread(() -> {
      try {
        Thread.sleep(200);
        distributor.sendMessage(new ClearGame(), id);
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

  private void sendToast(String message, String id, int delay) {
    new Thread(() -> {
      try {
        Thread.sleep(delay);
      } catch (InterruptedException e) {
        throw new RuntimeException(e); // this literally cannot happen.
      }
      distributor.sendMessage(new Toast(message), id);
    }).start();

  }

  private void startFirstStage(String id) {
    final String start = "Alderaan";
    final String target = "Tatooine";

    sendToast(
        "Welcome to the tutorial my dear AI.\n"
            + "Try sending your mothership from " + start + " to " + target
            + " by first clicking " + start + ", then " + target + " and then hitting enter.",
        id);

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
    sendToast(
        "Now try sending your fleet instead of your mother ship by entering a number before hitting enter",
        id);

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
    sendToast("There are a few new things this time.", id);

    final String start = "Sol";
    final String middle = "Proxima Centauri";
    final String target = "Tau Ceti";

    final String opponent = "opponent";

    final Map<String, Coordinates> stars = new HashMap<>();
    stars.put(start, new Coordinates(0.82, 0.7));
    stars.put(middle, new Coordinates(0.5, 0.55));
    stars.put(target, new Coordinates(0.3, 0.8));

    final Map<String, String> starPlayers = new HashMap<>();
    starPlayers.put(start, id);
    starPlayers.put(target, opponent);

    final TutorialLevel tutorialLevel = new TutorialLevel(
        stars, id, starPlayers, JUMP_DISTANCE, LIGHT_SPEED, MOTHER_SHIP_SPEED, SHIP_SPEED,
        DEMAT_RATE, GENERATION_RATE,
        lp -> {
          if (lp.stream().anyMatch(p -> p.getID().equals(id))) {
            endTutorial(id);
          } else {
            sendToast("You're lucky this is a simulation, or that would have been the end of you",
                id);
            nextStage(id, () -> startThirdStage(id));
          }
        }
    );

    tutorialLevel.addTrigger(
        state -> !state.getPlayers().get(id).getFleets().isEmpty(),
        () -> {
          sendToast("For one thing, colonized stars generate ships.", id, 200);
          sendToast("You also have an opponent now.", id, 7000);
          sendToast("You should take " + middle
              + " first and gather your forces there before you face the other AI", id, 10000);
        }
    );

    tutorialLevel.addTrigger(
        state -> state.getSignals().stream().anyMatch(s -> s.getTimeToArrive() > 0.2),
        () -> {
          sendToast("Have you noticed that some commands have a delay?", id);
          sendToast(
              "The further away your mother ship is from the star you're sending a command to," +
                  " the longer it will take for you to see the results.", id, 3000);
        }
    );

    Map<String, Color> colors = new HashMap<>();
    colors.put(id, Color.RED);
    colors.put(opponent, Color.RED.invert());
    tutorialLevel.startGame(starter, distributor, colors);
  }

  private void endTutorial(String id) {
    sendToast("I think you're ready for the real deal.", id);
    new Thread(() -> {
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e); // this literally cannot happen.
      }
      distributor.sendMessage(new EndGameRes(id, id), id);
    }).start();
  }
}
