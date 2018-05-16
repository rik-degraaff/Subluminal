package tech.subluminal.shared.messages;

import java.util.LinkedList;
import java.util.List;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;

/**
 * Represents the request from a client to the server to move the mothership. This message, when
 * converted to SON and then to string, might look like this:
 * <pre>
 * {
 *   "starList":l[
 *     s"9876",
 *     s"8765",
 *     s"7654"
 *   ]
 * }
 * </pre>
 */
public class MotherShipMoveReq extends MoveReq {

  public MotherShipMoveReq(List<String> stars) {
    super(stars);
  }

  /**
   * Creates a new MotherShipMoveReq from its SON representation.
   *
   * @param son the SON representation of a MotherShipMoveReq
   * @return the MotherShipMoveReq from its SON representation.
   * @throws SONConversionError if the conversion fails.
   */
  public static MotherShipMoveReq fromSON(SON son) throws SONConversionError {
    return MoveReq.fromSON(son, () -> new MotherShipMoveReq(new LinkedList<>()));
  }

  /**
   * Creates and returns a SON representation of this MotherShipMoveReq.
   */
  @Override
  public SON asSON() {
    return super.asSON();
  }
}
