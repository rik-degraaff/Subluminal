package tech.subluminal.shared.records;

/**
 * Record that represents a user.
 */
public class User {

  private String username;
  private String id;

  /**
   * Creates a user.
   *
   * @param username to record for a specified user.
   * @param id to record for a specified user
   */
  public User(String username, String id) {
    this.username = username;
    this.id = id;
  }

  /**
   * @return the user.
   */
  public String getUsername() {
    return username;
  }

  /**
   * @param username to be set.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * @return the user id.
   */
  public String getId() {
    return id;
  }

  /**
   * 
   *
   * @param id to be set as user id.
   */
  public void setId(String id) {
    this.id = id;
  }
}
