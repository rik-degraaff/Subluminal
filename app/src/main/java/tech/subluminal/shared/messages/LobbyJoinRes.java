package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.Lobby;

/**
 * Represents a response from server to client with the joined lobby and sends the users that are
 * already in the lobby. This message, converted to SON and then to string, might look like this:
 * <pre>
 * {
 *   "lobby":o{
 *     "playerCount":i0,
 *     "status":s"FULL",
 *     "settings":o{
 *       "maxPlayers":i8,
 *       "minPlayers":i2,
 *       "mapSize":d2.0,
 *       "gameSpeed":d1.0,
 *       "name":s"Batman",
 *       "adminID":s"9000"
 *     },
 *     "identifiable":o{
 *       "id":s"1729"
 *     },
 *     "users":l[
 *     ]
 *   }
 * }
 * </pre>
 */
public class LobbyJoinRes implements SONRepresentable {

  private static final String CLASS_NAME = LobbyJoinRes.class.getSimpleName();
  private static final String LOBBY_KEY = "lobby";
  private Lobby lobby;

  /**
   * Creates a lobby join request with a given lobby.
   *
   * @param lobby the lobby object.
   */
  public LobbyJoinRes(Lobby lobby) {
    this.lobby = lobby;
  }

  /**
   * Creates a ping from a SON object.
   *
   * @param son the SON object to be converted to a lobby join response.
   * @return the created response.
   * @throws SONConversionError when the conversion fails.
   */
  public static LobbyJoinRes fromSON(SON son) throws SONConversionError {
    SON lobby = son.getObject(LOBBY_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, LOBBY_KEY));
    return new LobbyJoinRes(Lobby.fromSON(lobby));
  }

  /**
   * Returns the user store for the lobby.
   *
   * @return the user store of the lobby.
   */
  public Lobby getLobby() {
    return lobby;
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON()
        .put(lobby.asSON(), LOBBY_KEY);
  }
}
