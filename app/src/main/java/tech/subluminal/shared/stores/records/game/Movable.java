package tech.subluminal.shared.stores.records.game;

import java.util.List;
import org.pmw.tinylog.Logger;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONList;
import tech.subluminal.shared.son.SONRepresentable;

public abstract class Movable extends GameObject {

  private static final String CLASS_NAME = Movable.class.getSimpleName();
  private static final String GAME_OBJECT_KEY = "gameObject";
  private static final String TARGETS_KEY = "targets";
  private static final String END_TARGET_KEY = "target";
  private static final String SPEED_KEY = "speed";

  private List<String> targetIDs;
  private String endTarget;
  private double speed;

  public Movable(Coordinates coordinates, String id, List<String> targetIDs, String endTarget,
      double speed) {
    super(coordinates, id);
    this.targetIDs = targetIDs;
    this.endTarget = endTarget;
    this.speed = speed;
  }

  public List<String> getTargetIDs() {
    return targetIDs;
  }

  public void setTargetIDs(List<String> targetIDs) {
    this.targetIDs = targetIDs;
  }

  public String getEndTarget() {
    return endTarget;
  }

  public void setEndTarget(String endTarget) {
    this.endTarget = endTarget;
  }

  public boolean isOnStar(Star star) {
    return targetIDs.size() == 1
        && targetIDs.contains(star.getID())
        && getDistanceFrom(star) < 0.00001;
  }

  /**
   * @return the movement speed of this movable.
   */
  public double getSpeed() {
    return speed;
  }

  /**
   * Calculates the time this movable would need to reach a target.
   *
   * @param target the coordinates the movable should calculate its traveling time to.
   * @return the time required to reach the target in seconds.
   */
  public double getTimeToReach(Coordinates target) {
    return getCoordinates().getDistanceFrom(target) / speed;
  }

  /**
   * Calculates the position of the movable after moving in a direction.
   *
   * @param target the target to move towards.
   * @param travelTime the time to travel towards
   * @return the new position.
   */
  public Coordinates getPositionMovingTowards(Coordinates target, double travelTime) {
    double travelDistance = travelTime * speed;
    Logger.debug("travelDistance: " + travelDistance);
    double distance = getCoordinates().getDistanceFrom(target);
    Logger.debug("distance: " + distance);

    double x = getCoordinates().getX()
        + travelDistance * (target.getX() - getCoordinates().getX()) / distance;
    double y = getCoordinates().getY()
        + travelDistance * (target.getY() - getCoordinates().getY()) / distance;

    return new Coordinates(x, y);
  }

  protected void loadFromSON(SON son) throws SONConversionError {
    SON gameObject = son.getObject(GAME_OBJECT_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, GAME_OBJECT_KEY));
    super.loadFromSON(gameObject);

    SONList targets = son.getList(TARGETS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, TARGETS_KEY));

    this.speed = son.getDouble(SPEED_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, SPEED_KEY));

    this.endTarget = son.getString(END_TARGET_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, END_TARGET_KEY));

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
        .put(targets, TARGETS_KEY)
        .put(endTarget, END_TARGET_KEY)
        .put(speed, SPEED_KEY);
  }
}
