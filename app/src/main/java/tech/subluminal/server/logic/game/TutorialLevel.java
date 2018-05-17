package tech.subluminal.server.logic.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javafx.scene.paint.Color;
import tech.subluminal.server.logic.MessageDistributor;
import tech.subluminal.server.stores.records.GameState;
import tech.subluminal.server.stores.records.Player;
import tech.subluminal.server.stores.records.Star;
import tech.subluminal.shared.messages.GameStartRes;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.Fleet;
import tech.subluminal.shared.stores.records.game.Ship;

class TutorialLevel {

  private final GameState state;
  private final double lightSpeed;
  private final double fleetSpeed;
  private final String player;
  private final Map<String, Set<String>> otherPlayers = new HashMap<>();
  private final List<Function<GameState, Boolean>> triggers = new LinkedList<>();
  private final Consumer<List<Player>> onEnd;

  TutorialLevel(
      Map<String, Coordinates> starCoordinates, String player, Map<String, String> starPlayers,
      double jump, double lightSpeed, double shipSpeed, double fleetSpeed,
      double dematRate, double genRate, Consumer<List<Player>> onEnd
  ) {
    this.player = player;
    this.lightSpeed = lightSpeed;
    this.fleetSpeed = fleetSpeed;
    this.onEnd = onEnd;
    Set<Star> stars = new HashSet<>();
    Set<Player> players = new HashSet<>();

    starCoordinates.forEach((starName, coords) -> {
      String p = starPlayers.get(starName);
      if (p != null) {
        stars.add(new Star(
            p, 1, coords, starName, true, jump,
            dematRate, dematRate, genRate, genRate, starName
        ));

        otherPlayers.put(p, starPlayers.values().stream()
            .filter(id -> !id.equals(p))
            .collect(Collectors.toSet()));
        final Ship motherShip = new Ship(coords, p, Collections.emptyList(), starName, shipSpeed);
        players.add(new Player(p, "", otherPlayers.get(p), motherShip, lightSpeed));
      } else {
        stars.add(new Star(
            null, 0, coords, starName, false, jump,
            dematRate, dematRate, genRate, genRate, starName
        ));
      }
    });

    this.state = new GameState(player, stars, players, lightSpeed, jump, shipSpeed);
  }

  public void addFleet(String ownerID, String id, String starID, int amount) {
    Coordinates coords = state.getStars().get(starID).getCurrent().getState().getCoordinates();
    Fleet fleet = new Fleet(coords, amount, id, Collections.emptyList(), starID, fleetSpeed);
    state.getPlayers().get(ownerID).getFleets().put(id,
        new GameHistory<>(otherPlayers.keySet(), GameHistoryEntry.foreverAgo(fleet), lightSpeed)
    );
  }

  public void addTrigger(Predicate<GameState> condition, Runnable action) {
    addGenericTrigger(condition, () -> {
      action.run();
      return false;
    });
  }

  public void addTerminatingTrigger(Predicate<GameState> condition, Runnable action) {
    addGenericTrigger(condition, () -> {
      action.run();
      return true;
    });
  }

  private void addGenericTrigger(Predicate<GameState> condition, Supplier<Boolean> action) {
    AtomicBoolean hasBeenTriggered = new AtomicBoolean(false);
    triggers.add(state -> {
      if (!hasBeenTriggered.get() && condition.test(state)) {
        hasBeenTriggered.set(true);
        return action.get();
      }
      return false;
    });
  }

  public void startGame(
      GameStarter starter, MessageDistributor distributor, Map<String, Color> playerColors
  ) {
    distributor.sendMessage(new GameStartRes(player, playerColors), player);
    Map<String, String> playerMap = new HashMap<>();
    playerMap.put(player, player);
    starter.startGame(player, playerMap, state,
        stop -> triggers.stream().anyMatch(trigger -> trigger.apply(state)) || stop,
        onEnd);
  }
}
