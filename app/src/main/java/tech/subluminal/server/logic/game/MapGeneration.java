package tech.subluminal.server.logic.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.subluminal.server.stores.records.GameState;
import tech.subluminal.server.stores.records.Player;
import tech.subluminal.server.stores.records.Star;
import tech.subluminal.shared.records.GlobalSettings;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.Ship;
import tech.subluminal.shared.util.IdUtils;
import tech.subluminal.shared.util.NameGenerator;

public class MapGeneration {

  private static Random rand = new Random(GlobalSettings.SHARED_RANDOM.nextLong());
  // it should take roughly 20 second for light to traverse the entire map
  private static final double LIGHT_SPEED = GlobalSettings.SERVER_LIGHT_SPEED;
  // ships can cross the map in roughly 3 jumps
  private static final double JUMP_DISTANCE = GlobalSettings.SERVER_JUMP_DISTANCE;
  private static final double SHIP_SPEED = GlobalSettings.SERVER_SHIP_SPEED;
  private static final double MOTHER_SHIP_SPEED = GlobalSettings.SERVER_MOTHER_SHIP_SPEED;
  private static final double DEMAT_RATE = GlobalSettings.SERVER_DEMAT_RATE;
  private static final double GENERATION_RATE = GlobalSettings.SERVER_GENERATION_RATE;
  private static NameGenerator ng = new NameGenerator();

  public static GameState getNewGameStateForPlayers(Map<String, String> playerNames, String gameID) {
    final Set<Star> stars = new HashSet<>();
    final Set<Player> players = new HashSet<>();
    final Set<String> playerIDs = playerNames.keySet();

    int additionalStars = (int) Math.pow(10 + 3 * playerIDs.size(), 0.75);
    List<Coordinates> coordinates = new ArrayList<>(additionalStars + playerIDs.size());
    Stream
        .generate(() -> {
          Coordinates coords = getStarCoordinates(coordinates);
          coordinates.add(coords);
          return new Star(null, 0, coords,
              IdUtils.generateId(8), false, JUMP_DISTANCE, DEMAT_RATE, DEMAT_RATE, GENERATION_RATE,
              GENERATION_RATE, ng.getName());
        })
        .limit(additionalStars)
        .forEach(stars::add);

    playerNames.forEach((playerID, name) -> {
      Coordinates homeCoords = getStarCoordinates(coordinates);
      coordinates.add(homeCoords);
      Star homeStar = new Star(playerID, 1, homeCoords, IdUtils.generateId(GlobalSettings.SHARED_UUID_LENGTH),
          true, JUMP_DISTANCE, DEMAT_RATE, DEMAT_RATE, GENERATION_RATE, GENERATION_RATE,
          ng.getName());
      stars.add(homeStar);

      Set<String> otherPlayers = playerIDs.stream()
          .filter(id -> !id.equals(playerID))
          .collect(Collectors.toSet());

      Player player = new Player(playerID, name, otherPlayers,
          new Ship(homeCoords, IdUtils.generateId(GlobalSettings.SHARED_UUID_LENGTH), Collections.emptyList(),
              homeStar.getID(), MOTHER_SHIP_SPEED),
          LIGHT_SPEED
      );

      players.add(player);
    });

    return new GameState(gameID, stars, players, LIGHT_SPEED, JUMP_DISTANCE, SHIP_SPEED);
  }

  private static Coordinates getStarCoordinates(List<Coordinates> existingCoordinates) {
    Coordinates coordinates = new Coordinates(rand.nextDouble(), rand.nextDouble());
    while (!(existingCoordinates.isEmpty()
        || existingCoordinates.stream()
        .anyMatch(c -> coordinates.getDistanceFrom(c) <= JUMP_DISTANCE)
        && existingCoordinates.stream()
        .allMatch(c -> coordinates.getDistanceFrom(c) > JUMP_DISTANCE / 5))) {
      coordinates.setX(rand.nextDouble());
      coordinates.setY(rand.nextDouble());
    }
    return coordinates;
  }
}
