package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.User;

/**
 * Represents the message that a player joined. This message, when converted to SON and then to
 * string, might look like this:
 * <pre>
 * {
 *   "user":o{
 *     "username":s"Luc",
 *     "identifiable":o{
 *       "id":s"4107"
 *     }
 *   }
 * }
 * </pre>
 */
public class PlayerJoin implements SONRepresentable {

  private static final String USER_KEY = "user";
  private User user;

  /**
   * Creates a new PlayerJoin message with the user who joined.
   *
   * @param user the user who joined.
   */
  public PlayerJoin(User user) {
    this.user = user;
  }

  /**
   * Creates and returns a new PlayerJoin message from its SON representation.
   *
   * @param son the SON representation of a PlayerJoin message.
   * @return a new PlayerJoin message.
   * @throws SONConversionError if the conversion fails.
   */
  public static PlayerJoin fromSON(SON son) throws SONConversionError {
    SON user = son.getObject(USER_KEY)
        .orElseThrow(() -> new SONConversionError(
            "UserJoin did not contain a valid " + USER_KEY + "."));
    return new PlayerJoin(User.fromSON(user));
  }

  /**
   * @return the name of the user who joined.
   */
  public User getUser() {
    return user;
  }

  /**
   * Creates and returns the SON representation of this PlayerJoin message.
   *
   * @return the SON representation of this PlayerJoin message.
   */
  @Override
  public SON asSON() {
    return new SON().put(user.asSON(), USER_KEY);
  }
}
