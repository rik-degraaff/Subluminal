package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.Lobby;

/**
 * Represents a lobby create response from the server to the client.
 */
public class LobbyCreateRes implements SONRepresentable {

  private static final String CLASS_NAME = LobbyCreateRes.class.getSimpleName();
  private static final String ID_KEY = "id";

  private String id;

  /**
   * Creates a new lobby create response with an assigned ID.
   *
   * @param id the ID to assign to the response.
   */
  public LobbyCreateRes(String id) {
    this.id = id;
  }


  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON()
        .put(id, ID_KEY);
  }

  /**
   * Creates and returns a new lobby create response, converted from its SON representation.
   *
   * @param son the SON representation of a lobby create response.
   * @return a new lobby create response, converted from its SON representation.
   * @throws SONConversionError if the conversion fails.
   */
  public static LobbyCreateRes fromSON(SON son) throws SONConversionError {
    String id = son.getString(ID_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, ID_KEY));
    return new LobbyCreateRes(id);
  }
}
