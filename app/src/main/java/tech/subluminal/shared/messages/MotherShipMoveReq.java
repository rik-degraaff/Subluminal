package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;

public class MotherShipMoveReq extends MoveReq {

  public static MotherShipMoveReq fromSON(SON son) throws SONConversionError {
    return MoveReq.fromSON(son, MotherShipMoveReq::new);
  }

  @Override
  public SON asSON() {
    return super.asSON();
  }
}
