package tech.subluminal.shared.stores.records;

import java.util.function.Supplier;
import tech.subluminal.shared.records.LobbyStatus;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

public class SlimLobby extends Identifiable implements SONRepresentable {

  private static final String CLASS_NAME = SlimLobby.class.getSimpleName();
  private static final String IDENTIFIABLE_KEY = "identifiable";
  private static final String SETTINGS_KEY = "settings";
  private static final String STATUS_KEY = "status";

  private LobbySettings settings;
  private LobbyStatus status;

  public SlimLobby(String id, LobbySettings settings, LobbyStatus status) {
    super(id);
    this.settings = settings;
    this.status = status;
  }



  public LobbySettings getSettings() {
    return settings;
  }

  public LobbyStatus getStatus() {
    return status;
  }

  public void setStatus(LobbyStatus status) {
    this.status = status;
  }

  public void setSettings(LobbySettings settings) {
    this.settings = settings;
  }

  public static <E extends SlimLobby> E fromSON(SON son, Supplier<E> lobbySupplier)
      throws SONConversionError {
    E lobby = lobbySupplier.get();

    SON identifiable = son.getObject(IDENTIFIABLE_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, IDENTIFIABLE_KEY));
    lobby.loadFromSON(identifiable);

    SON settings = son.getObject(SETTINGS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, SETTINGS_KEY));

    lobby.setSettings(LobbySettings.fromSON(settings));

    String statusString = son.getString(STATUS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, STATUS_KEY));

    LobbyStatus status = LobbyStatus.valueOf(LobbyStatus.class, statusString);

    if (status == null) {
      throw SONRepresentable.error(CLASS_NAME, STATUS_KEY);
    }

    lobby.setStatus(status);

    return lobby;
  }

  public static SlimLobby fromSON(SON son) throws SONConversionError {
    return fromSON(son, () -> new SlimLobby(null, null, null));
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON()
        .put(super.asSON(), IDENTIFIABLE_KEY)
        .put(settings.asSON(), SETTINGS_KEY)
        .put(status.toString(), STATUS_KEY);
  }
}
