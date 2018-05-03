package tech.subluminal.server.logic.game;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.subluminal.server.stores.records.GameState;
import tech.subluminal.server.stores.records.Player;
import tech.subluminal.server.stores.records.Star;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.Ship;
import tech.subluminal.shared.util.IdUtils;
import tech.subluminal.shared.util.NameGenerator;

public class MapGeneration {

  // it should take roughly 20 second for light to traverse the entire map
  private static final double LIGHT_SPEED = 1 / 20.0;
  // ships can cross the map in roughly 3 jumps
  private static final double JUMP_DISTANCE = 1 / 2.5;
  private static final double SHIP_SPEED = LIGHT_SPEED * 0.5;
  private static final double MOTHER_SHIP_SPEED = LIGHT_SPEED * 0.4;
  private static final double DEMAT_RATE = 0.5;
  private static final double GENERATION_RATE = 5.0;
  private static NameGenerator ng = new NameGenerator();

  public static GameState getNewGameStateForPlayers(Set<String> playerIDs, String gameID) {
    final Set<Star> stars = new HashSet<>();
    final Set<Player> players = new HashSet<>();

    playerIDs.forEach(playerID -> {
      Coordinates homeCoords = new Coordinates(Math.random(), Math.random());
      Star homeStar = new Star(playerID, 1, homeCoords, IdUtils.generateId(8),
          true, JUMP_DISTANCE, DEMAT_RATE, DEMAT_RATE, GENERATION_RATE, GENERATION_RATE, ng.getName());
      stars.add(homeStar);

      Set<String> otherPlayers = playerIDs.stream()
          .filter(id -> !id.equals(playerID))
          .collect(Collectors.toSet());

      Player player = new Player(playerID, otherPlayers,
          new Ship(homeCoords, IdUtils.generateId(8),
              Collections.emptyList(), homeStar.getID(), MOTHER_SHIP_SPEED), LIGHT_SPEED);

      players.add(player);
    });

    int additionalStars = (int) Math.pow(10 + 3 * playerIDs.size(), 0.75);
    Stream.generate(() -> new Star(null, 0, new Coordinates(Math.random(), Math.random()),
        IdUtils.generateId(8), false, JUMP_DISTANCE, DEMAT_RATE, DEMAT_RATE, GENERATION_RATE,
        GENERATION_RATE, ng.getName()))
        .limit(additionalStars)
        .forEach(stars::add);

    return new GameState(gameID, stars, players, LIGHT_SPEED, JUMP_DISTANCE, SHIP_SPEED);
  }
}
