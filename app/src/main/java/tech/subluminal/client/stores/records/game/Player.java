package tech.subluminal.client.stores.records.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONList;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.Identifiable;
import tech.subluminal.shared.stores.records.game.Fleet;
import tech.subluminal.shared.stores.records.game.Ship;
import tech.subluminal.shared.stores.records.game.Star;

public class Player extends Identifiable implements SONRepresentable {

  private static final String CLASS_NAME = Player.class.getSimpleName();
  private static final String IDENTIFIABLE_KEY = "identifiable";
  private static final String MOTHERS_SHIP_KEY = "motherShip";
  private static final String FLEETS_KEY = "fleets";

  private Ship motherShip;
  private List<Fleet> fleets;

  public Player(String id, Ship motherShip, List<Fleet> fleets) {
    super(id);
    this.motherShip = motherShip;
    this.fleets = fleets;
  }

  public Ship getMotherShip() {
    return motherShip;
  }

  public void setMotherShip(Ship motherShip) {
    this.motherShip = motherShip;
  }

  public List<Fleet> getFleets() {
    return fleets;
  }

  public void setFleets(List<Fleet> fleets) {
    this.fleets = fleets;
  }

  public SON asSON() {
    SONList fleetList = new SONList();
    fleets.stream().map(Fleet::asSON).forEach(fleetList::add);

    return new SON()
        .put(super.asSON(), IDENTIFIABLE_KEY)
        .put(motherShip.asSON(), MOTHERS_SHIP_KEY)
        .put(fleetList, FLEETS_KEY);
  }

  public static Player fromSON(SON son) throws SONConversionError {
    SON motherShip = son.getObject(MOTHERS_SHIP_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, MOTHERS_SHIP_KEY));

    SONList fleets = son.getList(FLEETS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, FLEETS_KEY));

    List<Fleet> fleetList = new ArrayList<>(fleets.size());

    for (int i = 0; i < fleets.size(); i++) {
      int ii = i;
      fleetList.add(Fleet.fromSON(fleets.getObject(i)
          .orElseThrow(() -> SONRepresentable.error(FLEETS_KEY, Integer.toString(ii)))));
    }

    Player player = new Player(null, Ship.fromSON(motherShip), fleetList);

    SON identifiable = son.getObject(IDENTIFIABLE_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, IDENTIFIABLE_KEY));

    player.loadFromSON(identifiable);

    return player;
  }

  public void updateFleet(Fleet fleet) {
    fleets.removeIf(f -> f.getId().equals(fleet.getId()));
    fleets.add(fleet);
  }
}
