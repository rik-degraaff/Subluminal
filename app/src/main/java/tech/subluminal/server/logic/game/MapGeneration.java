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

public class MapGeneration {

  // it should take roughly 20 second for light to traverse the entire map
  private static final double LIGHT_SPEED = 1 / 20.0;
  // ships can cross the map in roughly 10 jumps
  private static final double JUMP_DISTANCE = 1 / 10.0;
  private static final double SHIP_SPEED = LIGHT_SPEED * 0.3;
  private static final double MOTHER_SHIP_SPEED = LIGHT_SPEED * 0.2;
  private static final double DEMAT_RATE = 0.5;
  private static final double GENERATION_RATE = 5.0;

  public static GameState getNewGameStateForPlayers(Set<String> playerIDs, String gameID) {
    final Set<Star> stars = new HashSet<>();
    final Set<Player> players = new HashSet<>();

    playerIDs.forEach(playerID -> {
      Coordinates homeCoords = new Coordinates(Math.random(), Math.random());
      Star homeStar = new Star(playerID, 1, homeCoords, IdUtils.generateId(8),
          true, DEMAT_RATE, DEMAT_RATE, GENERATION_RATE, GENERATION_RATE);
      stars.add(homeStar);

      Set<String> otherPlayers = playerIDs.stream()
          .filter(id -> !id.equals(playerID))
          .collect(Collectors.toSet());

      Player player = new Player(playerID, otherPlayers,
          new Ship(homeCoords, IdUtils.generateId(8),
              Collections.emptyList(), homeStar.getID(), MOTHER_SHIP_SPEED), LIGHT_SPEED);

      players.add(player);
    });

    int additionalStars = (int) Math.sqrt(1 + 2 * playerIDs.size());
    Stream.generate(() -> new Star(null, 0, new Coordinates(Math.random(), Math.random()),
        IdUtils.generateId(8), false, DEMAT_RATE, DEMAT_RATE, GENERATION_RATE, GENERATION_RATE))
        .limit(additionalStars)
        .forEach(stars::add);

    return new GameState(gameID, stars, players, LIGHT_SPEED, JUMP_DISTANCE, SHIP_SPEED);
  }
}
