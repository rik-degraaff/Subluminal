package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * ChatMessage object which was build by the sender.
 */
public class ChatMessageOut implements SONRepresentable {

  private static final String MESSAGE_KEY = "message";
  private static final String GLOBAL_KEY = "global";
  private static final String RECEIVER_ID_KEY = "receiverID";
  private static final String CHAT_MESSAGE_ERROR = "ChatMessage did not contain a valid ";
  private String message;
  private boolean global;
  private String receiverID;

  public String getMessage() {
    return message;
  }

  public boolean isGlobal() {
    return global;
  }

  public String getReceiverID() {
    return receiverID;
  }

  /**
   * ChatMessage object which is build by the sender.
   * @param message is the text to send.
   * @param global defines if a message is for the whole server or ingame.
   * @param receiverID
   */
  public ChatMessageOut(String message, boolean global, String receiverID) {
    this.message = message;
    this.global = global;
    this.receiverID = receiverID;
  }


  /**
   * Creates a SON object representing the ChatMessageOut.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return null;
  }

  /**
   * Creates a ChatMessageOut from a SON object.
   *
   * @param son which gets converted to the ChatMessageOut.
   * @return the ChatMessageOut object.
   * @throws SONConversionError when conversion fails.
   */
  public static ChatMessageOut fromSON(SON son) throws SONConversionError {
    String message = son.getString(MESSAGE_KEY)
        .orElseThrow(() -> new SONConversionError(
            CHAT_MESSAGE_ERROR + MESSAGE_KEY + "."));
    boolean global = son.getBoolean(GLOBAL_KEY)
        .orElseThrow(() -> new SONConversionError(
            CHAT_MESSAGE_ERROR + GLOBAL_KEY + "."));
    String receiverID = global ? null : son.getString(RECEIVER_ID_KEY)
        .orElseThrow(() -> new SONConversionError(
            CHAT_MESSAGE_ERROR + RECEIVER_ID_KEY + "."));

    return new ChatMessageOut(message, global, receiverID);

  }
}
