package tech.subluminal.shared.messages;

import tech.subluminal.shared.records.Channel;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * ChatMessage which is received by the client from the server. A ChatMessageIn message converted to
 * SON and then to string might look like this:
 * <pre>
 * {
 *    "channel":s"CRITICAL",
 *    "message":s"!Hello!",
 *    "username":s"Luke"
 * }
 * </pre>
 */
public class ChatMessageIn implements SONRepresentable {

  private static final String MESSAGE_KEY = "message";
  private static final String USERNAME_KEY = "username";
  private static final String CHANNEL_KEY = "channel";
  private static final String CLASS_NAME = ChatMessageIn.class.getSimpleName();
  private String message;
  private String username;
  private Channel channel;

  /**
   * The ChatMessage object which is build and send by the server as a response.
   *
   * @param message is the message of the object.
   * @param username of the sender.
   * @param channel is the channel which the message should be send in.
   */
  public ChatMessageIn(String message, String username, Channel channel) {
    this.message = message;
    this.username = username;
    this.channel = channel;
  }

  /**
   * Returns a ChatMessageIn object from a SON object.
   *
   * @param son to be converted to the ChatMessageIn.
   * @return the converted ChatMessageIn object.
   * @throws SONConversionError if the object is not in the correct syntax.
   */
  public static ChatMessageIn fromSON(SON son) throws SONConversionError {
    String message = son.getString(MESSAGE_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, MESSAGE_KEY));

    String username = son.getString(USERNAME_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, USERNAME_KEY));

    //Has to be initialized in this scope
    Channel channel;

    try {
      channel = son.getString(CHANNEL_KEY).map(Channel::valueOf)
          .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, CHANNEL_KEY));
    } catch (IllegalArgumentException e) {
      throw new SONConversionError("Message was sent with an invalid channel.");
    }

    return new ChatMessageIn(message, username, channel);
  }

  /**
   * Get the message body.
   *
   * @return the message.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Gets the username of the sender.
   *
   * @return the username of the sender.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Returns the channel of the message.
   *
   * @return the used channel.
   */
  public Channel getChannel() {
    return channel;
  }

  /**
   * @return the SON representation of this object.
   */
  @Override
  public SON asSON() {
    return new SON()
        .put(message, MESSAGE_KEY)
        .put(username, USERNAME_KEY)
        .put(channel.toString(), CHANNEL_KEY);
  }

}
