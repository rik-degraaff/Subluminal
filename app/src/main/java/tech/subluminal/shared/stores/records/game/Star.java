package tech.subluminal.shared.stores.records.game;

import tech.subluminal.shared.son.SON;

public class Star extends GameObject {

  private String ownerID;
  private double possession;

  private static final String OWNER_ID_KEY = "ownerID";
  private static final String POSSESSION_KEY = "possession";

  public Star(String ownerID, double possession,
      Coordinates coordinates, String id) {
    super(coordinates, id);
    this.ownerID = ownerID;
    this.possession = possession;
  }

  /**
   * @return the ID of the owner of this star. Null if the star has no owner
   */
  public String getOwnerID() {
    return ownerID;
  }

  /**
   * @param ownerID the ID of the star's owner.
   */
  public void setOwnerID(String ownerID) {
    this.ownerID = ownerID;
  }

  /**
   * @return a number from zero to one representing the degree of colonization by a player.
   */
  public double getPossession() {
    return possession;
  }

  /**
   * @param possession the degree of colonization by a player.
   */
  public void setPossession(double possession) {
    this.possession = possession;
  }

  public SON asSON() {
    return null;
  }
}
