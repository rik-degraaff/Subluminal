package tech.subluminal.shared.stores.records.game;

import java.util.ArrayList;
import java.util.List;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

public class Fleet extends Movable implements SONRepresentable {

  private static final String CLASS_NAME = Fleet.class.getSimpleName();
  private static final String MOVABLE_KEY = "movable";
  private static final String AMOUNT_KEY = "amount";

  private int numberOfShips;

  public Fleet(
      Coordinates coordinates, int numberOfShips, String id, List<String> targetIDs,
      String endTarget, double speed
  ) {
    super(coordinates, id, targetIDs, endTarget, speed);
    this.numberOfShips = numberOfShips;
  }

  public static Fleet fromSON(SON son) throws SONConversionError {
    int numberOfShips = son.getInt(AMOUNT_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, AMOUNT_KEY));

    Fleet fleet = new Fleet(null, numberOfShips, null, new ArrayList<>(), null, 0.0);

    SON movable = son.getObject(MOVABLE_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, MOVABLE_KEY));

    fleet.loadFromSON(movable);

    return fleet;
  }

  /**
   * @return the number of ships this fleet contains.
   */
  public int getNumberOfShips() {
    return numberOfShips;
  }

  /**
   * @param numberOfShips the number of ships this fleet contains.
   */
  public void setNumberOfShips(int numberOfShips) {
    this.numberOfShips = numberOfShips;
  }

  /**
   * Adds a number of ships to this fleet.
   *
   * @param newShips the amount of ships to add.
   * @return a new instance of Fleets with the added amount of ships.
   */
  public Fleet expanded(int newShips) {
    return new Fleet(getCoordinates(), getNumberOfShips() + newShips, getID(), getTargetIDs(),
        getEndTarget(), getSpeed());
  }

  public SON asSON() {
    return new SON()
        .put(super.asSON(), MOVABLE_KEY)
        .put(numberOfShips, AMOUNT_KEY);
  }
}
