package tech.subluminal.server.logic.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import tech.subluminal.client.logic.Graph;
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
  private static final double LIGHT_SPEED = GlobalSettings.SERVER_LIGHT_SPEED; // it should take roughly 20 second for light to traverse the entire map
  private static final double JUMP_DISTANCE = GlobalSettings.SERVER_JUMP_DISTANCE; // ships can cross the map in roughly 3 jumps
  private static final double SHIP_SPEED = GlobalSettings.SERVER_SHIP_SPEED;
  private static final double MOTHER_SHIP_SPEED = GlobalSettings.SERVER_MOTHER_SHIP_SPEED;
  private static final double DEMAT_RATE = GlobalSettings.SERVER_DEMAT_RATE;
  private static final double GENERATION_RATE = GlobalSettings.SERVER_GENERATION_RATE;
  //private static final double x0 = 0, x1 = 1, y0 = 0, y1 = 1, minDist = 0.1;

  private static NameGenerator ng = new NameGenerator();

  public static GameState getNewGameStateForPlayers(Map<String, String> playerNames,
      String gameID) {
    final Set<Star> stars = new HashSet<>();
    final Set<Player> players = new HashSet<>();
    final Set<String> playerIDs = playerNames.keySet();
    int additionalStars = (int) Math.pow(10 + 3 * playerIDs.size(), 0.75);
    int offset = rand.nextInt(90);
    AtomicInteger playerPosCounter = new AtomicInteger();
    //final UniformPoissonDiskSampler ufpds = new UniformPoissonDiskSampler(x0, y0, x1, y1, minDist, totalStars);
    //List<Coordinates> coordinates = ufpds.sample();

    List<Coordinates> coordinates = new ArrayList<>(additionalStars + playerIDs.size());

    playerNames.forEach((playerID, name) -> {
      Coordinates homeCoords = getHomeCoordinates(playerIDs.size(), 0.45, 0.5, 0.5,
          playerPosCounter.get(), offset, 0.1); // getStarCoordinates(coordinates);
      coordinates.add(homeCoords);
      playerPosCounter.getAndIncrement();

      Star homeStar = new Star(playerID, 1, homeCoords,
          IdUtils.generateId(GlobalSettings.SHARED_UUID_LENGTH),
          true, JUMP_DISTANCE, DEMAT_RATE, DEMAT_RATE, GENERATION_RATE, GENERATION_RATE,
          ng.getName());
      stars.add(homeStar);

      Set<String> otherPlayers = playerIDs.stream()
          .filter(id -> !id.equals(playerID))
          .collect(Collectors.toSet());

      Player player = new Player(playerID, name, otherPlayers,
          new Ship(homeCoords, IdUtils.generateId(GlobalSettings.SHARED_UUID_LENGTH),
              Collections.emptyList(),
              homeStar.getID(), MOTHER_SHIP_SPEED), LIGHT_SPEED
      );

      players.add(player);
    });

    List<Coordinates> homeCoordinates = new ArrayList<>(coordinates);
    Stream
        .generate(() -> {
          Coordinates coords = getStarCoordinates(coordinates); //coordinates.remove(0);
          coordinates.add(coords);

          return new Star(null, 0, coords,
              IdUtils.generateId(8), false, JUMP_DISTANCE, DEMAT_RATE, DEMAT_RATE, GENERATION_RATE,
              GENERATION_RATE, ng.getName());
        })
        .limit(additionalStars)
        .forEach(stars::add);

    final Set<Integer> keys = IntStream.range(0, coordinates.size())
        .mapToObj(i -> i)
        .collect(Collectors.toSet());
    Graph<Integer> graph = new Graph<>(
        keys,
        (i1, i2) -> coordinates.get(i1).getDistanceFrom(coordinates.get(i2)) < JUMP_DISTANCE,
        (i1, i2) -> coordinates.get(i1).getDistanceFrom(coordinates.get(i2)),
        true);

    List<Integer> components = new ArrayList<>(keys);

    while ((components = getComponents(graph, components)).size() > 1) {
      Coordinates coords = getStarCoordinates(coordinates);
      coordinates.add(coords);
      graph.addNode(coordinates.size() - 1);
      stars.add(new Star(null, 0, coords,
          IdUtils.generateId(8), false, JUMP_DISTANCE, DEMAT_RATE, DEMAT_RATE, GENERATION_RATE,
          GENERATION_RATE, ng.getName()));
    }

    return new GameState(gameID, stars, players, LIGHT_SPEED, JUMP_DISTANCE, SHIP_SPEED);
  }

  private static List<Integer> getComponents(Graph<Integer> graph, List<Integer> core) {
    List<Integer> components = new LinkedList<>();
    outer:
    for (final Integer alpha : core) {
      for (final Integer omega : components) {
        if (!graph.findShortestPath(alpha, omega).isEmpty()) {
          continue outer;
        }
      }
      components.add(alpha);
    }
    return components;
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

  private static Coordinates getHomeCoordinates(
      int playerCount, double radius, double xCenter, double yCenter, int counter, double offset,
      double fuzz
  ) {
    final double angle = 360.0 / playerCount * counter + offset;
    final double x = xCenter + radius * Math.cos(Math.toRadians(angle));
    final double y = yCenter + radius * Math.sin(Math.toRadians(angle));
    Coordinates p = new Coordinates(x + fuzz * (Math.random() - 0.5),
        y + fuzz * (Math.random() - 0.5));
    return p;
  }

}
