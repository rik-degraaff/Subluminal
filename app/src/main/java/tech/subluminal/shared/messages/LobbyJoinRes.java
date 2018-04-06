package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

  /**
   * Represents a response from server to client with the joined lobby
   * and sends the users already in the lobby.
   */
  public class LobbyJoinRes implements SONRepresentable {

    private static final String LOBBY_USERS_KEY = "lobbyusers";
    private LobbyUserStore store;

  /**
   * Creates a lobby join request with a given id.
   *
   * @param id the id of the lobby.
   */
  public LobbyJoinRes(LobbyUserStore store) {
    this.store = store;
  }

  /**
   * Creates a ping from a SON object.
   *
   * @param son the SON object to be converted to a lobby join response.
   * @return the created response.
   * @throws SONConversionError when the conversion fails.
   */
  public static LobbyJoinRes fromSON(SON son) throws SONConversionError {
    LobbyUserStore store = son.getObject(LOBBY_USERS_KEY)
        .orElseThrow(() -> new SONConversionError(
            "Response did contain a valid lobby user store " + store + "."));
    return new LobbyJoinRes(store);
  }

   /**
   * Returns the user store for the lobby.
   *
   * @return the user store of the lobby.
   */
  public LobbyUserStore getStore() {
    return store;
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON()
        .put(id, ID_KEY)
        .put(store.asSON(), LOBBY_USERS_KEY);
  }
}
