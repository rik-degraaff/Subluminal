package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a username change request from the client to the server.
 */
public class UsernameReq implements SONRepresentable {

  public static final String USERNAME_KEY = "username";
  private String username;

  /**
   * Creates a username change request for the specified user.
   *
   * @param username for the username change request.
   */
  public UsernameReq(String username) {
    this.username = username;
  }

  /**
   * Creates a username change request from a SON object.
   *
   * @param son the SON object to be converted to a username change request.
   * @return the created username change request.
   * @throws SONConversionError when the conversion fails.
   */
  public static UsernameReq fromSON(SON son) throws SONConversionError {
    String username = son.getString(USERNAME_KEY)
        .orElseThrow(() -> new SONConversionError(
            "Username change Request did not contain valid " + USERNAME_KEY + "."));

    return new UsernameReq(username);
  }

  /**
   * Gets the username from the request.
   *
   * @return the requested client username.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username for the request.
   *
   * @param username the requested client username.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON()
        .put(username, USERNAME_KEY);
  }
}
