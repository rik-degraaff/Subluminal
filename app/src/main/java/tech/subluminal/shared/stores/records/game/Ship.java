package tech.subluminal.shared.stores.records.game;

import java.util.ArrayList;
import java.util.List;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

public class Ship extends Movable implements SONRepresentable {

  private static final String CLASS_NAME = Movable.class.getSimpleName();
  private static final String MOVABLE_KEY = "movable";

  public Ship(Coordinates coordinates, String id, List<String> targetIDs, String endTarget, double speed) {
    super(coordinates, id, targetIDs, endTarget, speed);
  }

  public static Ship fromSON(SON son) throws SONConversionError {
    Ship ship = new Ship(null, null, new ArrayList<>(), null, 0.0);

    SON movable = son.getObject(MOVABLE_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, MOVABLE_KEY));

    ship.loadFromSON(movable);

    return ship;
  }

  public SON asSON() {
    return new SON()
        .put(super.asSON(), MOVABLE_KEY);
  }
}
