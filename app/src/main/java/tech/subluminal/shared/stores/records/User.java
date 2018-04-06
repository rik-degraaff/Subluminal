package tech.subluminal.shared.stores.records;

/**
 * Record that represents a user.
 */
public class User extends Identifiable {

  private String username;

  /**
   * Creates a user.
   *
   * @param username to record for a specified user.
   * @param id to record for a specified user
   */
  public User(String username, String id) {
    super(id);
    this.username = username;
  }

  /**
   * Gets the username.
   *
   * @return the user.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username of the user.
   *
   * @param username to be set.
   */
  public void setUsername(String username) {
    this.username = username;
  }
}
