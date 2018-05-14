package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a lobby create request from the client to the server. This message, when converted to
 * SON and then to string, might look like this:
 * <pre>
 * {
 *   "name":s"greatest lobby ever"
 * }
 * </pre>
 */
public class LobbyCreateReq implements SONRepresentable {

  private static final String CLASS_NAME = LobbyCreateReq.class.getSimpleName();
  private static final String NAME_KEY = "name";

  private String name;

  /**
   * Creates a new lobby create request and assigns a name to it.
   *
   * @param name the name to assign to the lobby create request.
   */
  public LobbyCreateReq(String name) {
    this.name = name;
  }

  /**
   * Creates and Returns a lobby create request, converted from its SON representation.
   *
   * @param son the SON representation of a lobby create request.
   * @return a new lobby create request, converted from its SON representation.
   * @throws SONConversionError if the conversion fails.
   */
  public static LobbyCreateReq fromSON(SON son) throws SONConversionError {
    String name = son.getString(NAME_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, NAME_KEY));
    ;
    return new LobbyCreateReq(name);
  }

  /**
   * @return the name.
   */
  public String getName() {
    return name;
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON()
        .put(name, NAME_KEY);
  }
}
