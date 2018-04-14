package tech.subluminal.shared.stores.records;

import java.util.function.Supplier;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

public class SlimLobby extends Identifiable implements SONRepresentable {

  private static final String CLASS_NAME = SlimLobby.class.getSimpleName();
  private static final String IDENTIFIABLE_KEY = "identifiable";
  private static final String SETTINGS_KEY = "settings";

  private LobbySettings settings;

  public SlimLobby(String id, LobbySettings settings) {
    super(id);
    this.settings = settings;
  }

  public LobbySettings getSettings() {
    return settings;
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
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, IDENTIFIABLE_KEY));
    lobby.loadFromSON(settings);

    return lobby;
  }

  public static SlimLobby fromSON(SON son) throws SONConversionError {
    return fromSON(son, () -> new SlimLobby(null, null));
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
        .put(settings.asSON(), SETTINGS_KEY);
  }
}
