package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * ChatMessage which is received by the client from the server.
 */
public class ChatMessageIn implements SONRepresentable {

  public static final String MESSAGE_KEY = "message";
  private static final String CLASS_NAME = ChatMessageIn.class.getSimpleName();
  public static final String SENDER_ID_KEY = "senderID";
  public static final String CHANNEL_KEY = "channel";
  private String message;
  private String senderID;
  private Channel channel;


  public static enum Channel {
    WHISPER, GAME, GLOBAL
  }


  public ChatMessageIn(String message, String senderID, Channel channel) {
    this.message = message;
    this.senderID = senderID;
    this.channel = channel;
  }

  public String getMessage() {
    return message;
  }

  public String getSenderID() {
    return senderID;
  }

  public Channel getChannel() {
    return channel;
  }

  @Override
  public SON asSON() {
    return new SON()
        .put(message, MESSAGE_KEY)
        .put(senderID, SENDER_ID_KEY)
        .put(channel.toString(), CHANNEL_KEY);
  }

  public static ChatMessageIn fromSON(SON son) throws SONConversionError {
    String message = son.getString(MESSAGE_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, MESSAGE_KEY));

    String senderID = son.getString(SENDER_ID_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, SENDER_ID_KEY));

    //Has to be initialized in this scope
    Channel channel;

    try {
      channel = son.getString(CHANNEL_KEY).map(Channel::valueOf)
          .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, CHANNEL_KEY));
    } catch (IllegalArgumentException e) {
      throw new SONConversionError("Message was send with an invalid channel.");
    }

    return new ChatMessageIn(message, senderID, channel);
  }
}
