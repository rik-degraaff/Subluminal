package tech.subluminal.server.logic.game;

import static tech.subluminal.shared.util.IdUtils.generateId;
import static tech.subluminal.shared.util.function.IfPresent.ifPresent;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.pmw.tinylog.Logger;
import tech.subluminal.server.stores.records.Signal;
import tech.subluminal.server.stores.records.Star;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.Fleet;
import tech.subluminal.shared.stores.records.game.Movable;
import tech.subluminal.shared.stores.records.game.Ship;

public class IntermediateGameState {

  private static final double DISTANCE_THRESHOLD = 0.00000001;

  private final double deltaTime;
  private final Map<String, Double> dematerializedShips;
  private final Map<String, Double> dematerializedEnemyShips;
  private final Map<String, List<Fleet>> destroyedFleets;
  private final Set<String> destroyedPlayers = new HashSet<>();
  private final Map<String, Map<String, Optional<Fleet>>> fleetsOnStars;
  private final Map<String, Map<String, Optional<Ship>>> motherShipsOnStars;
  private final Map<String, Map<String, Fleet>> fleetsUnderway;
  private final Map<String, Optional<Ship>> motherShipsUnderway;
  private final Map<String, Star> stars;
  private final Set<String> players;
  private Set<Signal> signals;
  private final PriorityQueue<PriorityRunnable> tasks = new PriorityQueue<>(
      Comparator.reverseOrder());
  private final double shipSpeed;

  public IntermediateGameState(double deltaTime, Map<String, Star> stars, Set<String> players,
      double shipSpeed, Set<Signal> signals) {
    this.deltaTime = deltaTime;
    this.stars = stars;
    this.players = players;
    this.shipSpeed = shipSpeed;
    this.signals = signals;

    fleetsOnStars = createMapWithKeys(stars.keySet(),
        () -> createMapWithKeys(players, Optional::empty));
    motherShipsOnStars = createMapWithKeys(stars.keySet(),
        () -> createMapWithKeys(players, Optional::empty));

    fleetsUnderway = createMapWithKeys(players, HashMap::new);

    destroyedFleets = createMapWithKeys(players, LinkedList::new);
    motherShipsUnderway = createMapWithKeys(players, Optional::empty);
    dematerializedEnemyShips = createMapWithKeys(players, () -> 0.0);
    dematerializedShips = createMapWithKeys(players, () -> 0.0);
  }

  private static <E> Map<String, E> createMapWithKeys(Set<String> keys, Supplier<E> supplier) {
    return keys.stream().collect(Collectors.toMap(id -> id, id -> supplier.get()));
  }

  public void advance() {
    stars.keySet().forEach(starID -> colonisationTick(starID, deltaTime));

    stars.replaceAll((starID, star) ->
        star.advancedBy(deltaTime,
            time -> tasks.add(new PriorityRunnable(time, () -> generateShips(starID))),
            time -> tasks.add(new PriorityRunnable(time, () -> dematerializeTick(starID))))
    );

    signals = signals.stream()
        .map(signal -> signal.advanced(deltaTime,
            time -> tasks.add(new PriorityRunnable(time, () ->
                splitFleetOnStar(signal.getStarID(), signal.getPlayerID(), signal.getAmount(),
                    signal.getTargets())
                    .ifPresent(fleet -> {
                      setFleetUnderway(fleet, signal.getPlayerID());
                      moveFleet(time, signal.getPlayerID(), fleet.getID(), deltaTime - time);
                    })
            ))
        ))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toSet());

    fleetsUnderway.forEach((playerID, fleetMap) -> {
      fleetMap.keySet().forEach(fleetID -> moveFleet(0.0, playerID, fleetID, deltaTime));
    });

    motherShipsUnderway.forEach((playerID, optShip) -> {
      optShip.ifPresent(ship -> moveMotherShip(0.0, playerID, ship.getID(), deltaTime));
    });

    while (!tasks.isEmpty()) {
      tasks.poll().run();
    }
  }

  private Optional<Fleet> splitFleetOnStar(String starID, String playerID, int amount,
      List<String> targets) {
    return fleetsOnStars.get(starID)
        .get(playerID)
        .map(fleet -> {
          if (amount >= fleet.getNumberOfShips()) {
            fleetsOnStars.get(starID).put(playerID, Optional.empty());
            return new Fleet(fleet.getCoordinates(), fleet.getNumberOfShips(), fleet.getID(),
                targets, targets.get(targets.size() - 1), fleet.getSpeed());
          }
          fleetsOnStars.get(starID).put(playerID, Optional.of(
              new Fleet(fleet.getCoordinates(), fleet.getNumberOfShips() - amount, fleet.getID(),
                  fleet.getTargetIDs(), fleet.getEndTarget(), fleet.getSpeed())
          ));
          return new Fleet(fleet.getCoordinates(), amount, generateId(8),
              targets, targets.get(targets.size() - 1), fleet.getSpeed());
        });
  }

  private void moveFleet(double start, String playerID, String fleetID, double deltaTimeLeft) {
    if (fleetsUnderway.get(playerID).get(fleetID) == null) {
      return;
    }

    Fleet fleet = fleetsUnderway.get(playerID).get(fleetID);
    Star star = stars.get(fleet.getTargetIDs().get(0));

    double timeToArrive = fleet.getTimeToReach(star.getCoordinates());
    if (deltaTimeLeft < timeToArrive) {
      Fleet newFleet = new Fleet(
          fleet.getPositionMovingTowards(star.getCoordinates(), deltaTimeLeft),
          fleet.getNumberOfShips(), fleetID, fleet.getTargetIDs(), fleet.getEndTarget(),
          fleet.getSpeed());
      fleetsUnderway.get(playerID).put(fleetID, newFleet);
    } else {
      fleet.setCoordinates(
          new Coordinates(star.getCoordinates().getX(), star.getCoordinates().getY()));
      double newStart = start + timeToArrive;
      if (fleet.getTargetIDs().size() == 1) {
        tasks.add(new PriorityRunnable(newStart, () -> {
          fleetsUnderway.get(playerID).remove(fleetID);
          addFleetToStar(fleet, playerID, star.getID());
        }));
      } else {
        fleet.setTargetIDs(fleet.getTargetIDs().subList(1, fleet.getTargetIDs().size()));
        tasks.add(new PriorityRunnable(newStart,
            () -> {
              passFleetByStar(playerID, fleetID, star.getID());
              moveFleet(newStart, playerID, fleetID, deltaTimeLeft - timeToArrive);
            }));
      }
    }
  }

  private void moveMotherShip(double start, String playerID, String shipID, double deltaTimeLeft) {
    if (!motherShipsUnderway.get(playerID).isPresent()) {
      return;
    }

    Ship ship = motherShipsUnderway.get(playerID).get();
    Star star = stars.get(ship.getTargetIDs().get(0));

    double timeToArrive = ship.getTimeToReach(star.getCoordinates());
    Logger.debug("TIME TO ARRIVE: " + timeToArrive);
    Logger.debug("DELTA TIME TO ARIVE: " + deltaTimeLeft);
    if (deltaTimeLeft < timeToArrive) {
      Logger.debug(
          "old x: " + ship.getCoordinates().getX() + " old y: " + ship.getCoordinates().getY());
      Logger.debug(
          "new x: " + ship.getPositionMovingTowards(star.getCoordinates(), deltaTimeLeft).getX()
              + " new y: " + ship.getPositionMovingTowards(star.getCoordinates(), deltaTimeLeft)
              .getY());
      Ship newShip = new Ship(
          ship.getPositionMovingTowards(star.getCoordinates(), deltaTimeLeft), shipID,
          ship.getTargetIDs(), ship.getEndTarget(), ship.getSpeed());
      motherShipsUnderway.put(playerID, Optional.of(newShip));
    } else {
      ship.setCoordinates(
          new Coordinates(star.getCoordinates().getX(), star.getCoordinates().getY()));
      double newStart = start + timeToArrive;
      if (ship.getTargetIDs().size() == 1) {
        tasks.add(new PriorityRunnable(newStart, () -> {
          motherShipsUnderway.remove(playerID);
          addMotherShipToStar(ship, playerID, star.getID());
        }));
      } else {
        ship.setTargetIDs(ship.getTargetIDs().subList(1, ship.getTargetIDs().size()));
        tasks.add(new PriorityRunnable(newStart,
            () -> {
              passMotherShipByStar(playerID, shipID, star.getID());
              moveMotherShip(newStart, playerID, shipID, deltaTimeLeft - timeToArrive);
            }));
      }
    }
  }

  private void passMotherShipByStar(String playerID, String shipID, String starID) {
    final double dematStrength = getPassingDematStrength(playerID, starID);

    if (dematStrength >= 5) {
      shipsDematerialized(playerID, 5, getPlayerStrengths(starID, playerID));
      getMotherShipsUnderway().remove(playerID);
      getDestroyedPlayers().add(playerID);
    }
  }

  private void passFleetByStar(String playerID, String fleetID, String starID) {
    final double dematStrength = getPassingDematStrength(playerID, starID);

    if (dematStrength >= 1) {
      final Fleet fleet = fleetsUnderway.get(playerID).get(fleetID);
      final int oldCount = fleet.getNumberOfShips();
      final int maxLoss = Math.max(1, oldCount - (int) (0.85 * oldCount));
      final int loss = Math.min(maxLoss, (int) dematStrength);
      shipsDematerialized(playerID, Math.min(oldCount, loss), getPlayerStrengths(starID, playerID));
      if (loss >= oldCount) {
        fleetsUnderway.get(playerID).remove(fleetID);
        destroyedFleets.get(playerID).add(fleet);
      } else {
        dematerializedShips.compute(playerID, (key, old) -> old + loss);
        fleetsUnderway.get(playerID).remove(fleetID);
        fleetsUnderway.get(playerID).put(fleetID, fleet.expanded(-loss));
      }
    }
  }

  private double getPassingDematStrength(String playerID, String starID) {
    final Map<String, Integer> playerStrengths = getPlayerStrengths(starID);
    final int opponentsStrength = playerStrengths.keySet()
        .stream()
        .filter(opponentID -> !opponentID.equals(playerID))
        .mapToInt(playerStrengths::get)
        .sum();
    final int strengthDiff = opponentsStrength - playerStrengths.getOrDefault(playerID, 0);

    return getDematStrength(strengthDiff) / 2;
  }

  private void colonisationTick(String starID, double deltaTime) {
    String highestID = null;
    int highest = 0;
    int secondHighest = 0;
    for (String playerID : players) {
      int score = fleetsOnStars.get(starID).get(playerID).map(Fleet::getNumberOfShips).orElse(0)
          + motherShipsOnStars.get(starID).get(playerID).map(s -> 2).orElse(0);
      if (score >= highest) {
        secondHighest = highest;
        highest = score;
        highestID = playerID;
      }
    }

    if (highestID != null) {
      Star star = stars.get(starID);
      double colStrength = getColonizationStrength(highest - secondHighest);
      double diff = highestID.equals(star.getOwnerID())
          ? colStrength * deltaTime * 0.1
          : -colStrength * deltaTime * 0.1;

      double possession = star.getPossession() + diff;
      String ownerID = star.getOwnerID();
      boolean isGenerating = star.isGenerating();

      if (possession < 0) {
        possession = -possession;
        ownerID = highestID;
        isGenerating = false;
      }

      isGenerating |= possession > 0.999;

      stars.put(starID,
          new Star(ownerID, Math.min(possession, 1.0), star.getCoordinates(), starID, isGenerating,
              star.getJump(), star.getDematRate(), star.getNextDemat(), star.getGenerationRate(),
              star.getNextShipgen(), star.getName()));
    }
  }

  private Map<String, Integer> getPlayerStrengths(String starID) {
    return getPlayerStrengths(starID, "");
  }

  private Map<String, Integer> getPlayerStrengths(String starID, String excluding) {
    final Map<String, Optional<Fleet>> fleets = fleetsOnStars.get(starID);
    final Map<String, Optional<Ship>> motherShips = motherShipsOnStars.get(starID);

    return getStrengths(fleets, motherShips, excluding);
  }

  private Map<String, Integer> getStrengths(
      Map<String, Optional<Fleet>> fleets,
      Map<String, Optional<Ship>> motherShips
  ) {
    return getStrengths(fleets, motherShips, "");
  }

  private Map<String, Integer> getStrengths(
      Map<String, Optional<Fleet>> fleets,
      Map<String, Optional<Ship>> motherShips,
      String excluding
  ) {
    return players.stream()
        .filter(id -> !id.equals(excluding))
        .filter(player -> fleets.get(player).isPresent()
            || motherShips.get(player).isPresent()
        )
        .collect(Collectors.toMap(Function.identity(), player -> getPlayerStrength(
            fleets.get(player).map(Fleet::getNumberOfShips).orElse(0),
            motherShips.get(player).isPresent()
        )));
  }

  private void shipsDematerialized(String playerID, int amount, Map<String, Integer> opponents) {
    dematerializedShips.compute(playerID,
        (key, old) -> old + amount);
    final int totalStrength = opponents.values().stream().mapToInt(s -> s).sum();
    opponents.forEach((opponent, strength) -> {
      dematerializedEnemyShips.compute(opponent,
          (key, old) -> old + amount * (strength / (double) totalStrength));
    });
  }

  private void dematerializeTick(String starID) {
    final Map<String, Optional<Fleet>> fleets = fleetsOnStars.get(starID);
    final Map<String, Optional<Ship>> motherShips = motherShipsOnStars.get(starID);

    final Map<String, Integer> playerStrengths = getStrengths(fleets, motherShips);

    final Map<String, Integer> newPlayerStrengths = new HashMap<>(playerStrengths);

    final Map<String, Map<String, Integer>> opponentStrengths = new HashMap<>();
    playerStrengths.forEach((player, strength) -> {
      final Map<String, Integer> strengths = playerStrengths.keySet()
          .stream()
          .filter(id -> !id.equals(player))
          .collect(Collectors.toMap(Function.identity(), playerStrengths::get));
      opponentStrengths.put(player, strengths);
    });

    playerStrengths.forEach((playerID, strength) -> {
      final int totalOpponentDefense = playerStrengths.keySet().stream()
          .filter(p -> !p.equals(playerID))
          .mapToInt(playerStrengths::get)
          .sum();

      final double dematStrength = getDematStrength(strength);
      final double dematPercentage = dematStrength / totalOpponentDefense;

      playerStrengths.keySet().stream()
          .filter(p -> !p.equals(playerID))
          .forEach(opponent -> {
            newPlayerStrengths.compute(opponent,
                (o, str) -> str - (int) (playerStrengths.get(opponent) * dematPercentage));
          });
    });

    newPlayerStrengths.forEach((player, newStrength) -> {
      if (newStrength <= 0) {
        motherShips.get(player).ifPresent(ship -> {
          shipsDematerialized(player, 5, opponentStrengths.get(player));
          destroyedPlayers.add(player);
          motherShips.put(player, Optional.empty());
        });
      }

      final int shipsLeft = newStrength - (motherShips.get(player).isPresent() ? 5 : 0);

      if (shipsLeft <= 0) {
        fleets.get(player).ifPresent(fleet -> {
          shipsDematerialized(player, fleet.getNumberOfShips(), opponentStrengths.get(player));
          destroyedFleets.get(player).add(fleet);
          fleets.put(player, Optional.empty());
        });
      } else {
        fleets.put(player,
            fleets.get(player).map(fleet -> {
              shipsDematerialized(player, fleet.getNumberOfShips() - shipsLeft, opponentStrengths.get(player));
              return fleet.expanded(shipsLeft - fleet.getNumberOfShips());
            }));
      }
    });
  }

  private double getDematStrength(int strength) {
    if (strength <= 6) {
      return strength;
    }
    return 5 + Math.sqrt(strength - 5);
  }

  private double getOriginalStrength(double dematStrength) {
    if (dematStrength <= 5) {
      return dematStrength;
    }
    return 5 + Math.pow(dematStrength - 5, 2);
  }

  private double getColonizationStrength(int strength) {
    return Math.sqrt(strength);
  }

  private int getPlayerStrength(int ships, boolean motherShip) {
    return ships + (motherShip ? 5 : 0);
  }

  private void generateShips(String starID) {
    Star star = stars.get(starID);
    if (star.isGenerating()) {
      final Optional<Fleet> optionalFleet = fleetsOnStars.get(starID).get(star.getOwnerID());
      final Fleet fleet = optionalFleet
          .map(f -> f.expanded(1))
          .orElseGet(() ->
              new Fleet(star.getCoordinates(), 1, generateId(8), Collections.emptyList(), starID,
                  shipSpeed));
      addFleetToStar(fleet, star.getOwnerID(), starID);
    }
  }

  public void addFleet(Fleet fleet, String playerID) {
    if (isOnStar(fleet)) {
      addFleetToStar(fleet, playerID, fleet.getEndTarget());
    } else {
      setFleetUnderway(fleet, playerID);
    }
  }

  private void setFleetUnderway(Fleet fleet, String playerID) {
    fleetsUnderway.get(playerID).put(fleet.getID(), fleet);
  }

  public void addMotherShip(Ship ship, String playerID) {
    if (isOnStar(ship)) {
      addMotherShipToStar(ship, playerID, ship.getEndTarget());
    } else {
      setMotherShipUnderway(ship, playerID);
    }
  }

  private void setMotherShipUnderway(Ship ship, String playerID) {
    motherShipsUnderway.put(playerID, Optional.of(ship));
  }

  private void addMotherShipToStar(Ship ship, String playerID, String starID) {
    Ship newShip = ship.getTargetIDs().size() == 0
        ? ship
        : new Ship(ship.getCoordinates(), ship.getID(), Collections.emptyList(), starID,
            ship.getSpeed());
    motherShipsOnStars.get(starID).put(playerID, Optional.of(newShip));
  }

  private boolean isOnStar(Movable movable) {
    return movable.getTargetIDs().size() == 0
        || movable.getTargetIDs().size() == 1
        && movable.getDistanceFrom(stars.get(movable.getTargetIDs().get(0))) < DISTANCE_THRESHOLD;
  }

  private void addFleetToStar(Fleet fleet, String playerID, String starID) {
    fleetsUnderway.remove(fleet.getID());
    Optional<Fleet> optionalFleet = fleetsOnStars.get(starID).get(playerID);
    ifPresent(optionalFleet.filter(f -> !f.getID().equals(fleet.getID())))
        .then(oldFleet -> {
          fleetsOnStars.get(starID)
              .put(playerID, Optional.of(oldFleet.expanded(fleet.getNumberOfShips())));
          destroyedFleets.get(playerID).add(fleet);
        })
        .els(() -> {
          Fleet newFleet = fleet.getTargetIDs().size() == 0 && fleet.getEndTarget().equals(starID)
              ? fleet
              : new Fleet(fleet.getCoordinates(), fleet.getNumberOfShips(), fleet.getID(),
                  Collections.emptyList(), starID, fleet.getSpeed());

          fleetsOnStars.get(starID)
              .put(playerID, Optional.of(newFleet));
        });
  }

  public Map<String, List<Fleet>> getDestroyedFleets() {
    return destroyedFleets;
  }

  public Set<String> getDestroyedPlayers() {
    return destroyedPlayers;
  }

  public Map<String, Map<String, Optional<Fleet>>> getFleetsOnStars() {
    return fleetsOnStars;
  }

  public Map<String, Map<String, Optional<Ship>>> getMotherShipsOnStars() {
    return motherShipsOnStars;
  }

  public Map<String, Map<String, Fleet>> getFleetsUnderway() {
    return fleetsUnderway;
  }

  public Map<String, Optional<Ship>> getMotherShipsUnderway() {
    return motherShipsUnderway;
  }

  public Map<String, Star> getStars() {
    return stars;
  }

  public Set<Signal> getSignals() {
    return signals;
  }

  public Map<String, Double> getDematerializedEnemyShips() {
    return dematerializedEnemyShips;
  }

  public Map<String, Double> getDematerializedShips() {
    return dematerializedShips;
  }
}
