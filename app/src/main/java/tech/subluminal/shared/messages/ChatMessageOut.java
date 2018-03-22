package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * ChatMessage object which was build by the client for the server.
 */
public class ChatMessageOut implements SONRepresentable {

  private static final String MESSAGE_KEY = "message";
  private static final String GLOBAL_KEY = "global";
  private static final String RECEIVER_ID_KEY = "receiverID";
  private static final String CLASS_NAME = ChatMessageIn.class.getSimpleName();
  private String message;
  private String receiverID;
  private boolean global;

  /**
   * ChatMessage object which is build by the sender.
   *
   * @param message is the text to send.
   * @param global defines if a message is for the whole server or ingame.
   */
  public ChatMessageOut(String message, String receiverID, boolean global) {
    this.message = message;
    this.global = global;
    this.receiverID = receiverID;
  }

  public String getMessage() {
    return message;
  }

  public String getReceiverID() {
    return receiverID;
  }

  public boolean isGlobal() {
    return global;
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
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, MESSAGE_KEY));

    String receiverID = son.getString(RECEIVER_ID_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, RECEIVER_ID_KEY));

    boolean global = receiverID == null ? false : son.getBoolean(GLOBAL_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, GLOBAL_KEY));

    return new ChatMessageOut(message, receiverID, global);

  }
}
