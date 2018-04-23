package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.Lobby;

/**
 * Represents a lobby update request message.
 */
public class LobbyUpdateRes implements SONRepresentable {

  private static final String LOBBY_KEY = "lobby";

  private Lobby lobby;

  /**
   * Creates a new LobbyUpdateRes with the lobby that was updated.
   *
   * @param lobby the lobby that was updated.
   */
  public LobbyUpdateRes(Lobby lobby) {
    this.lobby = lobby;
  }

  /**
   * Creates a new LobbyUpdateRes from its SON representation.
   *
   * @param son the SON representation of a LobbyUpdateRes.
   * @return the LobbyUpdateRes converted from its SON representation.
   * @throws SONConversionError if the conversion fails.
   */
  public static LobbyUpdateRes fromSON(SON son) throws SONConversionError {
    SON lobby = son.getObject(LOBBY_KEY)
        .orElseThrow(() -> new SONConversionError(
            "Login Response did not contain valid " + LOBBY_KEY + "."));

    return new LobbyUpdateRes(Lobby.fromSON(lobby));
  }

  /**
   * @return the lobby that was updated.
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
