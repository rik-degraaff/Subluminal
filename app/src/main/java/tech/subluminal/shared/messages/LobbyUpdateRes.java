package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.Lobby;

public class LobbyUpdateRes implements SONRepresentable {

  public static final String LOBBY_KEY = "lobby";

  private Lobby lobby;

  public LobbyUpdateRes(Lobby lobby) {
    this.lobby = lobby;
  }

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

  public static LobbyUpdateRes fromSON(SON son) throws SONConversionError {
    SON lobby = son.getObject(LOBBY_KEY)
        .orElseThrow(() -> new SONConversionError(
            "Login Response did not contain valid " + LOBBY_KEY + "."));

    return new LobbyUpdateRes(Lobby.fromSON(lobby));
  }
}
