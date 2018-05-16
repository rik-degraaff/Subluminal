package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a reconnect request from client to server.
 */
public class ReconnectReq implements SONRepresentable {

  public static final String USERNAME_KEY = "username";
  public static final String ID_KEY = "id";

  private String username;
  private String id;

  /**
   * Creates a reconnect request for the specified user.
   *
   * @param username for the reconnect request.
   * @param id for the reconnect request.
   */
  public ReconnectReq(String username, String id) {
    this.username = username;
    this.id = id;
  }

  /**
   * Creates a reconnect request from a SON object.
   *
   * @param son the SON object to be converted to a login request.
   * @return the created reconnect request.
   * @throws SONConversionError when the conversion fails.
   */
  public static ReconnectReq fromSON(SON son) throws SONConversionError {
    String username = son.getString(USERNAME_KEY)
        .orElseThrow(() -> new SONConversionError(
            "Reconnect Request did not contain valid " + USERNAME_KEY + "."));

    String id = son.getString(ID_KEY)
        .orElseThrow(() -> new SONConversionError(
            "Reconnect Request did not contain valid " + ID_KEY + "."));

    return new ReconnectReq(username, id);
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
   * Gets the id from the request.
   *
   * @return the requested client id.
   */
  public String getID() {
    return id;
  }

  /**
   * Creates a SON object representing this login request.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON()
        .put(username, USERNAME_KEY)
        .put(id, ID_KEY);
  }
}
