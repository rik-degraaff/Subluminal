package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents the message that a player left.
 */
public class PlayerLeave implements SONRepresentable {

  private static final String CLASS_NAME = PlayerLeave.class.getSimpleName();
  private static String USER_ID_KEY = "id";
  private String id;

  /**
   * Creates a new PlayerLeave message with the ID of the leaving player.
   *
   * @param id the ID of the leaving player.
   */
  public PlayerLeave(String id) {
    this.id = id;
  }

  /**
   * Creates and returns a new PlayerLeave message from its SON representation.
   *
   * @param son the SON representation of a PlayerLeave message.
   * @return a new PlayerLeave message.
   * @throws SONConversionError if the conversion fails.
   */
  public static PlayerLeave fromSON(SON son) throws SONConversionError {
    String id = son.getString(USER_ID_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, USER_ID_KEY));

    return new PlayerLeave(id);
  }

  /**
   * @return the ID of the leaving player.
   */
  public String getId() {
    return id;
  }

  /**
   * @return a SON representation of this PlayerLeave message.
   */
  @Override
  public SON asSON() {
    return new SON().put(id, USER_ID_KEY);
  }
}
