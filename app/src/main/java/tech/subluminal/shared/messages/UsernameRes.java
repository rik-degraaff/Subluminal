package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a username change response from the server to the client
 */
public class UsernameRes implements SONRepresentable {

  public static final String USERNAME_KEY = "username";
  private String username;

  /**
   * Creates a username change response for the specified user.
   *
   * @param username from the username change response.
   */
  public UsernameRes(String username) {
    this.username = username;
  }

  /**
   * Creates a username change response from a SON object.
   *
   * @param son the SON object to be converted to a username change response.
   * @return the created username change response.
   * @throws SONConversionError when the conversion fails.
   */
  public static UsernameRes fromSON(SON son) throws SONConversionError {
    String username = son.getString(USERNAME_KEY)
        .orElseThrow(() -> new SONConversionError(
            "Username change Response did not contain valid " + USERNAME_KEY + "."));

    return new UsernameRes(username);
  }

  /**
   * Gets the username from the response.
   *
   * @return the accepted client username.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the username for the response.
   *
   * @param username the accepted client username.
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
