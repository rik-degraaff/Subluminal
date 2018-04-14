package tech.subluminal.shared.stores.records.game;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

public class Star extends GameObject implements SONRepresentable {

  private static final String OWNER_ID_KEY = "ownerID";
  private static final String POSSESSION_KEY = "possession";
  private static final String CLASS_NAME = Star.class.getSimpleName();
  private static final String GAME_OBJECT_KEY = "gameObject";
  private String ownerID;
  private double possession;


  public Star(String ownerID, double possession,
      Coordinates coordinates, String id) {
    super(coordinates, id);
    this.ownerID = ownerID;
    this.possession = possession;
  }

  public static Star fromSON(SON son) throws SONConversionError {
    String ownerID = son.getString(OWNER_ID_KEY).orElse(null);

    double possession = son.getDouble(POSSESSION_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, POSSESSION_KEY));

    Star star = new Star(ownerID, possession, null, null);

    SON gameObject = son.getObject(GAME_OBJECT_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, GAME_OBJECT_KEY));

    star.loadFromSON(gameObject);

    return star;
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
    SON son = new SON()
        .put(possession, POSSESSION_KEY)
        .put(super.asSON(), GAME_OBJECT_KEY);

    if (ownerID != null) {
      son.put(ownerID, OWNER_ID_KEY);
    }

    return son;
  }
}
