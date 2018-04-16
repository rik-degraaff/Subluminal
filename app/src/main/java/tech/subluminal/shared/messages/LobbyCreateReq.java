package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

public class LobbyCreateReq implements SONRepresentable {

  public static final String CLASS_NAME = LobbyCreateReq.class.getSimpleName();
  public static final String NAME_KEY = "name";

  private String name;

  public LobbyCreateReq(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON()
        .put(name, NAME_KEY);
  }

  public static LobbyCreateReq fromSON (SON son) throws SONConversionError {
    String name = son.getString(NAME_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, NAME_KEY));;
    return new LobbyCreateReq(name);
  }
}
