package tech.subluminal.client.stores.records.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONList;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.Identifiable;
import tech.subluminal.shared.stores.records.game.Fleet;
import tech.subluminal.shared.stores.records.game.Ship;

/**
 * Represents a player participating in the game.
 */
public class Player extends Identifiable implements SONRepresentable {

  private static final String CLASS_NAME = Player.class.getSimpleName();
  private static final String IDENTIFIABLE_KEY = "identifiable";
  private static final String MOTHERS_SHIP_KEY = "motherShip";
  private static final String FLEETS_KEY = "fleets";

  private Optional<Ship> motherShip;
  private List<Fleet> fleets;

  /**
   * @param id the ID of the player.
   * @param motherShip the mother ship of the player.
   * @param fleets a list containing all the fleets a player possesses.
   */
  public Player(String id, Optional<Ship> motherShip, List<Fleet> fleets) {
    super(id);
    this.motherShip = motherShip;
    this.fleets = fleets;
  }

  /**
   * @param son the SON representation of a player.
   * @return a player.
   * @throws SONConversionError when the conversion fails.
   */
  public static Player fromSON(SON son) throws SONConversionError {
    Optional<SON> motherShip = son.getObject(MOTHERS_SHIP_KEY);

    SONList fleets = son.getList(FLEETS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, FLEETS_KEY));

    List<Fleet> fleetList = new ArrayList<>(fleets.size());

    for (int i = 0; i < fleets.size(); i++) {
      int ii = i;
      fleetList.add(Fleet.fromSON(fleets.getObject(i)
          .orElseThrow(() -> SONRepresentable.error(FLEETS_KEY, Integer.toString(ii)))));
    }

    Optional<Ship> ship;
    if (motherShip.isPresent()) {
      ship = Optional.ofNullable(Ship.fromSON(motherShip.get()));
    } else {
      ship = Optional.empty();
    }

    Player player = new Player(null, ship, fleetList);

    SON identifiable = son.getObject(IDENTIFIABLE_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, IDENTIFIABLE_KEY));

    player.loadFromSON(identifiable);

    return player;
  }

  /**
   * @return the mother ship of the player.
   */
  public Optional<Ship> getMotherShip() {
    return motherShip;
  }

  /**
   * @param motherShip the mother ship of the player.
   */
  public void setMotherShip(Ship motherShip) {
    this.motherShip = Optional.ofNullable(motherShip);
  }

  /**
   * @return a list containing all the fleets of a player.
   */
  public List<Fleet> getFleets() {
    return fleets;
  }

  /**
   * @param fleets a list containing all the fleets of a player.
   */
  public void setFleets(List<Fleet> fleets) {
    this.fleets = fleets;
  }

  /**
   * Produces the SON representation of a player.
   *
   * @return the SON representation of a player.
   */
  public SON asSON() {
    SONList fleetList = new SONList();
    fleets.stream().map(Fleet::asSON).forEach(fleetList::add);

    final SON son = new SON()
        .put(super.asSON(), IDENTIFIABLE_KEY)
        .put(fleetList, FLEETS_KEY);

    motherShip.ifPresent(ship -> son.put(ship.asSON(), MOTHERS_SHIP_KEY));

    return son;
  }

  /**
   * Refreshes the status of a fleet of a player.
   *
   * @param fleet the fleet to be updated.
   */
  public void updateFleet(Fleet fleet) {
    fleets.removeIf(f -> f.getID().equals(fleet.getID()));
    fleets.add(fleet);
  }
}
