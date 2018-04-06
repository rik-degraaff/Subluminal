package tech.subluminal.client.stores.records.game;

import java.util.List;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.Identifiable;
import tech.subluminal.shared.stores.records.game.Fleet;
import tech.subluminal.shared.stores.records.game.Ship;
import tech.subluminal.shared.stores.records.game.Star;

public class Player extends Identifiable implements SONRepresentable {

  private static final String CLASS_NAME = Player.class.getSimpleName();
  private static final String IDENTIFIABLE_KEY = "identifiable";

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
    return null;
  }

  public static Player fromSON(SON son) throws SONConversionError {


    Player player = new Player(null, null, null);

    SON identifiable = son.getObject(IDENTIFIABLE_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, IDENTIFIABLE_KEY));

    player.loadFromSON(identifiable);

    return player;
  }

  }
