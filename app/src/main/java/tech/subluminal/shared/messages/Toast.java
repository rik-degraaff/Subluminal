package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Represents a login request from server to client. This message, when converted to SON and then to
 * string, might look like this:
 * <pre>
 * {
 *   "message":s"Your message could be here"
 * }
 * </pre>
 */
public class Toast implements SONRepresentable {
  public static final String MESSAGE_KEY = "message";
  public static final String PERMANENT_KEY = "permanent";
  private String message;
  private boolean permanent;

  /**
   * Creates a login request for the specified user.
   *
   * @param message for the login request.
   * @param permanent if the toast is a permanent one.
   */
  public Toast(String message, boolean permanent) {
    this.message = message;
    this.permanent = permanent;
  }

  /**
   * Creates a login request for the specified user.
   *
   * @param message for the login request.
   */
  public Toast(String message) {
    this.message = message;
  }

  /**
   * Creates a login request from a SON object.
   *
   * @param son the SON object to be converted to a login request.
   * @return the created login request.
   * @throws SONConversionError when the conversion fails.
   */
  public static Toast fromSON(SON son) throws SONConversionError {
    String message = son.getString(MESSAGE_KEY)
        .orElseThrow(() -> new SONConversionError(
            "Toast did not contain valid " + MESSAGE_KEY + "."));
    boolean permanent = son.getBoolean(PERMANENT_KEY)
        .orElseThrow(() -> new SONConversionError(
            "Toast did not contain valid " + PERMANENT_KEY + "."));

    return new Toast(message, permanent);
  }

  /**
   * Gets the message from the request.
   *
   * @return the requested client message.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the message for the request.
   *
   * @param message the requested client message.
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * If the message is permanent.
   *
   * @return if it is permanent.
   */
  public boolean isPermanent() {
    return permanent;
  }

  /**
   * Set a new permanent status.
   *
   * @param permanent if it is permanent.
   */
  public void setPermanent(boolean permanent) {
    this.permanent = permanent;
  }

  /**
   * Creates a SON object representing this login request.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON()
        .put(message, MESSAGE_KEY)
        .put(permanent, PERMANENT_KEY);
  }

}
