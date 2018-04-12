package tech.subluminal.shared.stores.records.game;

import java.util.List;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONList;
import tech.subluminal.shared.son.SONRepresentable;

public abstract class Movable extends GameObject {

  private static final String CLASS_NAME = Movable.class.getSimpleName();
  private static final String GAME_OBJECT_KEY = "gameObject";
  private static final String TARGETS_KEY = "targets";

  private List<String> targetIDs;

  public Movable(Coordinates coordinates, String id, List<String> targetIDs) {
    super(coordinates, id);
    this.targetIDs = targetIDs;
  }

  public List<String> getTargetIDs() {
    return targetIDs;
  }

  public void setTargetIDs(List<String> targetIDs) {
    this.targetIDs = targetIDs;
  }

  public boolean isOnStar(Star star) {
    return targetIDs.size() == 1
        && targetIDs.contains(star.getID())
        && getDistanceFrom(star) < 0.00001;
  }

  protected void loadFromSON(SON son) throws SONConversionError {
    SON gameObject = son.getObject(GAME_OBJECT_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, GAME_OBJECT_KEY));
    super.loadFromSON(gameObject);

    SONList targets = son.getList(TARGETS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, TARGETS_KEY));

    for (int i = 0; i < targets.size(); i++) {
      int ii = i;
      targetIDs.add(targets.getString(i)
          .orElseThrow(() -> SONRepresentable.error(TARGETS_KEY, Integer.toString(ii))));
    }
  }

  protected SON asSON() {
    SONList targets = new SONList();
    targetIDs.forEach(targets::add);

    return new SON()
        .put(super.asSON(), GAME_OBJECT_KEY)
        .put(targets, TARGETS_KEY);
  }
}
