package tech.subluminal.shared.messages;

import java.util.LinkedList;
import java.util.List;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a fleet move request from a client to the server. A FleetMoveReq message converted to
 * SON and then to string might look like this:
 * <pre>
 * {
 *   "amount":i3,
 *   "originID":s"1234",
 *   "starList":l[
 *     s"1234",
 *     s"2345",
 *     s"3456"
 *   ]
 * }
 * </pre>
 */
public class FleetMoveReq extends MoveReq {

  private static final String ORIGIN_ID_KEY = "originID";
  private static final String AMOUNT_KEY = "amount";
  private static final String CLASS_NAME = FleetMoveReq.class.getSimpleName();

  private String originID;
  private int amount;

  /**
   * Creates a new fleet move request and assigns its origin ID and the amount of ships in this
   * fleet to it.
   *
   * @param originID the ID of the origin of the fleet.
   * @param amount the amount of ships the fleet should have.
   */
  public FleetMoveReq(String originID, int amount, List<String> stars) {
    super(stars);
    this.originID = originID;
    this.amount = amount;
  }

  /**
   * Returns a new fleet move request, converted from its SON representation.
   *
   * @param son the SON representation of a fleet move request.
   * @return a new fleet move request object, converted from its SON representation.
   * @throws SONConversionError if the conversion fails.
   */
  public static FleetMoveReq fromSON(SON son) throws SONConversionError {
    String originID = son.getString(ORIGIN_ID_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, ORIGIN_ID_KEY));
    int amount = son.getInt(AMOUNT_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, ORIGIN_ID_KEY));

    return MoveReq.fromSON(son, () -> new FleetMoveReq(originID, amount, new LinkedList<>()));
  }

  /**
   * @return the id of the star this move should start from.
   */
  public String getOriginID() {
    return originID;
  }

  /**
   * @return the amount of ships that should be sent out.
   */
  public int getAmount() {
    return amount;
  }

  /**
   * @return the SON representation of this object.
   */
  @Override
  public SON asSON() {
    return super.asSON()
        .put(originID, ORIGIN_ID_KEY)
        .put(amount, AMOUNT_KEY);
  }
}
