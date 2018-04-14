package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.Lobby;

public class LobbyCreateRes implements SONRepresentable {

  private static final String CLASS_NAME = LobbyCreateRes.class.getSimpleName();
  private static final String ID_KEY = "id";

  private String id;

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

  public static LobbyCreateRes fromSON(SON son) throws SONConversionError {
    String id = son.getString(ID_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, ID_KEY));
    return new LobbyCreateRes(id);
  }
}
