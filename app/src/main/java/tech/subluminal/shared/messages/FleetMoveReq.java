package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

public class FleetMoveReq extends MoveReq {

  private static final String ORIGIN_ID_KEY = "originID";
  private static final String AMOUNT_KEY = "amount";
  private static final String CLASS_NAME = FleetMoveReq.class.getSimpleName();

  private String originID;
  private int amount;

  public FleetMoveReq(String originID, int amount) {
    this.originID = originID;
    this.amount = amount;
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

  public static FleetMoveReq fromSON(SON son) throws SONConversionError {
    String originID = son.getString(ORIGIN_ID_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, ORIGIN_ID_KEY));
    int amount = son.getInt(AMOUNT_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, ORIGIN_ID_KEY));

    return MoveReq.fromSON(son, () -> new FleetMoveReq(originID, amount));
  }

  @Override
  public SON asSON() {
    return super.asSON()
        .put(originID, ORIGIN_ID_KEY)
        .put(amount, AMOUNT_KEY);
  }
}
