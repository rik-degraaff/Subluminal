package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;

/**
 * Represents the move request of a mothership.
 */
public class MotherShipMoveReq extends MoveReq {

  /**
   * Creates a new MotherShipMoveReq from its SON representation.
   *
   * @param son the SON representation of a MotherShipMoveReq
   * @return the MotherShipMoveReq from its SON representation.
   * @throws SONConversionError if the conversion fails.
   */
  public static MotherShipMoveReq fromSON(SON son) throws SONConversionError {
    return MoveReq.fromSON(son, MotherShipMoveReq::new);
  }

  /**
   * Creates and returns a SON representation of this MotherShipMoveReq.
   */
  @Override
  public SON asSON() {
    return super.asSON();
  }
}
