package tech.subluminal.shared.messages;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import tech.subluminal.client.stores.records.game.Player;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONList;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.game.Star;

public class GameStateDelta implements SONRepresentable {

  public static final String PLAYERS_KEY = "players";
  public static final String STARS_KEY = "stars";
  public static final String REMOVED_PLAYERS_KEY = "removedPlayers";
  public static final String REMOVED_FLEETS_KEY = "removedFleets";
  private static final String CLASS_NAME = GameStateDelta.class.getSimpleName();
  public static final String KEY = "key";
  public static final String VALUE = "value";


  private List<Player> players = new LinkedList<>();
  private List<Star> stars = new LinkedList<>();
  private List<String> removedPlayers = new LinkedList<>();
  private Map<String, List<String>> removedFleets = new HashMap<>();

  public List<Player> getPlayers() {
    return players;
  }

  public List<Star> getStars() {
    return stars;
  }

  public List<String> getRemovedPlayers() {
    return removedPlayers;
  }

  public void addPlayer(Player player) {
    players.add(player);
  }

  public void addStar(Star star) {
    stars.add(star);
  }

  public void addRemovedPlayer(String id) {
    removedPlayers.add(id);
  }

  public Map<String, List<String>> getRemovedFleets() {
    return removedFleets;
  }

  public void addRemovedFleet(String playerID, String fleetID) {
    List<String> fleets = removedFleets.get(playerID);
    if (fleets == null) {
      fleets = new LinkedList<>();
      removedFleets.put(playerID, fleets);
    }
    fleets.add(fleetID);
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    SONList playerList = new SONList();
    players.stream().map(Player::asSON).forEach(playerList::add);

    SONList starList = new SONList();
    stars.stream().map(Star::asSON).forEach(starList::add);

    SONList removedPlayersList = new SONList();
    removedPlayers.forEach(removedPlayersList::add);

    SONList removedFleetsList = new SONList();
    removedFleets.keySet().forEach(k -> {
      SONList fleets = new SONList();
      removedFleets.get(k).forEach(fleets::add);
      removedFleetsList.add(
          new SON()
              .put(k, KEY)
              .put(fleets, VALUE)
      );
    });

    return new SON()
        .put(playerList, PLAYERS_KEY)
        .put(starList, STARS_KEY)
        .put(removedPlayersList, REMOVED_PLAYERS_KEY)
        .put(removedFleetsList, REMOVED_FLEETS_KEY);
  }

  public static GameStateDelta fromSON(SON son) throws SONConversionError {
    GameStateDelta delta = new GameStateDelta();

    SONList starList = son.getList(STARS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, STARS_KEY));

    for (int i = 0; i < starList.size(); i++) {
      int ii = i;
      SON star = starList.getObject(i)
          .orElseThrow(() -> SONRepresentable.error(STARS_KEY, Integer.toString(ii)));

      delta.addStar(Star.fromSON(star));
    }

    SONList playerList = son.getList(PLAYERS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, PLAYERS_KEY));

    for (int i = 0; i < playerList.size(); i++) {
      int ii = i;
      SON player = playerList.getObject(i)
          .orElseThrow(() -> SONRepresentable.error(PLAYERS_KEY, Integer.toString(ii)));

      delta.addPlayer(Player.fromSON(player));
    }

    SONList removedPlayersList = son.getList(REMOVED_PLAYERS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, REMOVED_PLAYERS_KEY));

    for (int i = 0; i < removedPlayersList.size(); i++) {
      int ii = i;
      String player = removedPlayersList.getString(i)
          .orElseThrow(() -> SONRepresentable.error(REMOVED_PLAYERS_KEY, Integer.toString(ii)));

      delta.addRemovedPlayer(player);
    }

    SONList removedFleetsList = son.getList(REMOVED_FLEETS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, REMOVED_FLEETS_KEY));

    for (int i = 0; i < removedFleetsList.size(); i++) {
      int ii = i;
      SON playerFleets = removedFleetsList.getObject(i)
          .orElseThrow(() -> SONRepresentable.error(REMOVED_FLEETS_KEY, Integer.toString(ii)));
      String id = playerFleets.getString(KEY)
          .orElseThrow(() -> SONRepresentable.error(REMOVED_FLEETS_KEY + "[" + ii + "]", KEY));
      SONList fleets = playerFleets.getList(VALUE)
          .orElseThrow(() -> SONRepresentable.error(REMOVED_FLEETS_KEY + "[" + ii + "]", VALUE));
      for (int j = 0; j < fleets.size(); j++) {
        int jj = j;
        String fleet = fleets.getString(j)
            .orElseThrow(() -> SONRepresentable
                .error(REMOVED_FLEETS_KEY + "[" + ii + "]." + VALUE, Integer.toString(jj)));
        delta.addRemovedFleet(id, fleet);
      }
    }

    return delta;
  }
}
