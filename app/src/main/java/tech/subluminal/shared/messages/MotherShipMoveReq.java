package tech.subluminal.shared.messages;

import java.util.LinkedList;
import java.util.List;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;

public class MotherShipMoveReq extends MoveReq {

  public MotherShipMoveReq(List<String> stars) {
    super(stars);
  }

  public static MotherShipMoveReq fromSON(SON son) throws SONConversionError {
    return MoveReq.fromSON(son, () -> new MotherShipMoveReq(new LinkedList<>()));
  }

  @Override
  public SON asSON() {
    return super.asSON();
  }
}
