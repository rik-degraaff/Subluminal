package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.LobbySettings;

/**
 * Represents a lobby update result message.
 */
public class LobbyUpdateReq implements SONRepresentable {

  private static final String CLASS_NAME = LobbyUpdateReq.class.getSimpleName();
  private static final String SETTINGS_KEY = "settings";

  private LobbySettings settings;

  /**
   * Creates a new LobbyUpdateReq with the LobbySettings to be updated.
   *
   * @param settings the {@link LobbySettings} to be updated.
   */
  public LobbyUpdateReq(LobbySettings settings) {
    this.settings = settings;
  }

  /**
   * Creates and returns a new LobbyUpdateReq from its SON representation.
   *
   * @param son the SON representation of a LobbyUpdateReq.
   * @return a new LobbyUpdateReq, converted from its SON representation.
   * @throws SONConversionError if the conversion fails.
   */
  public static LobbyUpdateReq fromSON(SON son) throws SONConversionError {
    SON settings = son.getObject(SETTINGS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, SETTINGS_KEY));

    return new LobbyUpdateReq(LobbySettings.fromSON(settings));
  }

  /**
   * @return the {@link LobbySettings} to be updated.
   */
  public LobbySettings getSettings() {
    return settings;
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

