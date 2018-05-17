package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a request from client to server to join a lobby. This message, when converted to SON
 * and then to string, might look like this:
 * <pre>
 * {
 *   "id":s"4053"
 * }
 * </pre>
 */
public class SpectateGameReq implements SONRepresentable {

  private static final String ID_KEY = "id";
  private String id;

  /**
   * Creates a spectate game request with a given id.
   *
   * @param id the id of the game.
   */
  public SpectateGameReq(String id) {
    this.id = id;
  }

  /**
   * Creates a spectate game request from a SON object.
   *
   * @param son the SON object to be converted to a spectate game request.
   * @return the created request.
   * @throws SONConversionError when the conversion fails.
   */
  public static SpectateGameReq fromSON(SON son) throws SONConversionError {
    String id = son.getString(ID_KEY)
        .orElseThrow(() -> new SONConversionError(
            "Request did not contain valid lobby " + ID_KEY + "."));
    return new SpectateGameReq(id);
  }

  /**
   * Returns the id of the ping message.
   *
   * @return the id of the lobby.
   */
  public String getId() {
    return id;
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON().put(id, ID_KEY);
  }
}
