package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Updates player usernames.
 */
public class PlayerUpdate implements SONRepresentable {

  private static final String ID_KEY = "id";
  private static final String USERNAME_KEY = "username";
  private static final String CLASS_NAME = PlayerUpdate.class.getSimpleName();

  private final String username;
  private final String id;

  /**
   * Creates a new PlayerUpdate with the ID of the player to be updated and the username to update.
   *
   * @param id the ID of the player to be updated.
   * @param username the username to assign to the player.
   */
  public PlayerUpdate(String id, String username) {
    this.id = id;
    this.username = username;
  }

  /**
   * Creates and returns a new PlayerUpdate from its SON representation.
   *
   * @param son the SON representation of a PlayerUpdate.
   * @return a new PlayerUpdate, converted from its SON representation.
   * @throws SONConversionError if the conversion fails.
   */
  public static PlayerUpdate fromSON(SON son) throws SONConversionError {
    String id = son.getString(ID_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, ID_KEY));
    String username = son.getString(USERNAME_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, USERNAME_KEY));
    return new PlayerUpdate(id, username);
  }

  /**
   * @return the player username the PlayerUpdate is holding.
   */
  public String getUsername() {
    return username;
  }

  /**
   * @return the player ID the PlayerUpdate is holding.
   */
  public String getId() {
    return id;
  }

  /**
   * Creates and returns a SON representation of this PlayerUpdate.
   * @return
   */
  @Override
  public SON asSON() {
    SON update = new SON()
        .put(id, ID_KEY)
        .put(username, USERNAME_KEY);
    return update;
  }
}
