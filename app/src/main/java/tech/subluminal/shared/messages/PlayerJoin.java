package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.User;

public class PlayerJoin implements SONRepresentable {

  private static final String USER_KEY = "user";
  private User user;

  public PlayerJoin(User user) {
    this.user = user;
  }

  public static PlayerJoin fromSON(SON son) throws SONConversionError {
    SON user = son.getObject(USER_KEY)
        .orElseThrow(() -> new SONConversionError(
            "UserJoin did not contain a valid " + USER_KEY + "."));
    return new PlayerJoin(User.fromSON(user));
  }

  public User getUser() {
    return user;
  }

  @Override
  public SON asSON() {
    return new SON().put(user.asSON(), USER_KEY);
  }
}
