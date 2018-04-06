package tech.subluminal.client.stores.records.game;

import java.util.List;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.stores.records.Identifiable;
import tech.subluminal.shared.stores.records.game.Fleet;
import tech.subluminal.shared.stores.records.game.Ship;

public class Player extends Identifiable {
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
}
