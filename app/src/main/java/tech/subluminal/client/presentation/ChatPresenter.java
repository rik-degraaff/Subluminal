package tech.subluminal.client.presentation;

import java.util.Optional;

/**
 * Handles the presentation of the chat.
 */
public interface ChatPresenter {

  /**
   * Gets called when the User wants to send a new message.
   * @param message is the text to be send.
   * @param type defines for who the message is.
   * @param receiver if the message is private. /TODO make optional
   */
  void sendMessage( String type, String message, String receiver);

  /**
   *
   * @param username from the sender of the message.
   * @param message is the text of the message.
   * @param type specifies the type of the message.
   */
  void receiveMessage(String message, String type, String username);
}
