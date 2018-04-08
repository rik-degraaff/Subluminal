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

  public Fleet(Coordinates coordinates, int numberOfShips, String id, List<String> targetIDs) {
    super(coordinates, id, targetIDs);
    this.numberOfShips = numberOfShips;
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

  public SON asSON() {
    return new SON()
        .put(super.asSON(), MOVABLE_KEY)
        .put(numberOfShips, AMOUNT_KEY);
  }

  public static Fleet fromSON(SON son) throws SONConversionError {
    int numberOfShips = son.getInt(AMOUNT_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, AMOUNT_KEY));

    Fleet fleet = new Fleet(null, numberOfShips,null, new ArrayList<>());

    SON movable = son.getObject(MOVABLE_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, MOVABLE_KEY));

    fleet.loadFromSON(movable);

    return fleet;
  }
}
