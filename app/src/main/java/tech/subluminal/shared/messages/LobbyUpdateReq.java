package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.Lobby;
import tech.subluminal.shared.stores.records.LobbySettings;
import tech.subluminal.shared.stores.records.SlimLobby;

public class LobbyUpdateReq implements SONRepresentable {

  private static final String CLASS_NAME = LobbyUpdateReq.class.getSimpleName();
  private static final String SETTINGS_KEY = "settings";

  private LobbySettings settings;

  public LobbyUpdateReq(LobbySettings settings) {
    this.settings = settings;
  }

  public static LobbyUpdateReq fromSON(SON son) throws SONConversionError{
     SON settings = son.getObject(SETTINGS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, SETTINGS_KEY));

     return new LobbyUpdateReq(LobbySettings.fromSON(settings));

  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON()
        .put(settings.asSON(), SETTINGS_KEY);
  }
}

