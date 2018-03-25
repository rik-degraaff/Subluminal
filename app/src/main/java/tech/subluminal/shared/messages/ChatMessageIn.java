package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * ChatMessage which is received by the client from the server.
 */
public class ChatMessageIn implements SONRepresentable {

  public static final String MESSAGE_KEY = "message";
  public static final String USERNAME_KEY = "username";
  public static final String CHANNEL_KEY = "channel";
  private static final String CLASS_NAME = ChatMessageIn.class.getSimpleName();
  private String message;
  private String username;
  private Channel channel;


  public ChatMessageIn(String message, String username, Channel channel) {
    this.message = message;
    this.username = username;
    this.channel = channel;
  }

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
      throw new SONConversionError("Message was send with an invalid channel.");
    }

    return new ChatMessageIn(message, username, channel);
  }

  public String getMessage() {
    return message;
  }

  public String getUsername() {
    return username;
  }

  public Channel getChannel() {
    return channel;
  }

  @Override
  public SON asSON() {
    return new SON()
        .put(message, MESSAGE_KEY)
        .put(username, USERNAME_KEY)
        .put(channel.toString(), CHANNEL_KEY);
  }

  public static enum Channel {
    WHISPER, GAME, GLOBAL
  }
}
