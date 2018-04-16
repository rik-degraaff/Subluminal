package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

public class PlayerLeave implements SONRepresentable {

  private static final String CLASS_NAME = PlayerLeave.class.getSimpleName();
  private static String USER_ID_KEY = "id";
  private String id;

  public PlayerLeave(String id) {
    this.id = id;
  }

  public static PlayerLeave fromSON(SON son) throws SONConversionError {
    String id = son.getString(USER_ID_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, USER_ID_KEY));

    return new PlayerLeave(id);
  }

  public String getId() {
    return id;
  }

  @Override
  public SON asSON() {
    return new SON().put(id, USER_ID_KEY);
  }
}
