package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a login request from client to server.
 */
public class LoginReq implements SONRepresentable {

  public static final String USERNAME_KEY = "username";
  private String username;

  /**
   * Creates a login request for the specified user.
   *
   * @param username for the login request.
   */
  public LoginReq(String username) {
    this.username = username;
  }

  /**
   * Creates a login request from a SON object.
   *
   * @param son the SON object to be converted to a login request.
   * @return the created login request.
   * @throws SONConversionError when the conversion fails.
   */
  public static LoginReq fromSON(SON son) throws SONConversionError {
    String username = son.getString(USERNAME_KEY)
        .orElseThrow(() -> new SONConversionError(
            "Login Request did not contain valid " + USERNAME_KEY + "."));

    return new LoginReq(username);
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
   * Creates a SON object representing this login request.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON()
        .put(username, USERNAME_KEY);
  }
}
