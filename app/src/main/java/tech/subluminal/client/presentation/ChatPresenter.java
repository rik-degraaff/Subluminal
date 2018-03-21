package tech.subluminal.client.presentation;

import java.util.Optional;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Handles the presentation of the chat.
 */
public interface ChatPresenter {

  /**
   * Gets called when the User wants to send a new message to everyone.
   *
   * @param message is the text to be send.
   */
  void sendGlobalMessage(String message);

  /**
   * Send a message to all players in the same game.
   */
  void sendGameMessage(String message);

  /**
   * Send a message to a single player.
   *
   * @param message The actual message.
   */
  void sendWhisperMessage(String message, String username);

  /**
   * Fired when a someone sends a message to all users on the server.
   *
   * @param username from the sender of the message.
   * @param message is the text of the message.
   */
  void GlobalMessageReceived(String message, String username);

  /**
   * Fired when a personal message is received.
   *
   * @param message of the received whisper.
   * @param username of the sender.
   */
  void WhisperMessageReceived(String message, String username);

  /**
   * Fired when a message from the same game is received.
   *
   * @param message of the game message.
   * @param username fo the sender.
   */
  void GameMessageReceived(String message, String username);
}
