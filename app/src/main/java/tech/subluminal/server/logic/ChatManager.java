package tech.subluminal.server.logic;

import tech.subluminal.server.stores.ReadOnlyUserStore;
import tech.subluminal.shared.messages.ChatMessageIn;
import tech.subluminal.shared.messages.ChatMessageIn.Channel;
import tech.subluminal.shared.messages.ChatMessageOut;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.stores.records.User;

/**
 * Manages the chat messages that are sent to and received from the clients.
 */
public class ChatManager {

  private final ReadOnlyUserStore userStore;
  private final MessageDistributor distributor;

  /**
   * Manages the Messages from the clients and sends them as needed.
   *
   * @param distributor to send new messages.
   * @param userStore to check for active users.
   */
  public ChatManager(MessageDistributor distributor, ReadOnlyUserStore userStore) {
    this.userStore = userStore;
    this.distributor = distributor;

    distributor.addConnectionOpenedListener(this::attachHandlers);
  }

  private void attachHandlers(String id, Connection connection) {
    connection.registerHandler(ChatMessageOut.class, ChatMessageOut::fromSON,
        msg -> chatMessageReceived(msg, id));
  }

  private void chatMessageReceived(ChatMessageOut msg, String userID) {
    String receiver = msg.getReceiverID();
    userStore.connectedUsers().getByID(userID).ifPresent(syncUser -> {
      syncUser.consume(sender -> {
        if (receiver == null) {
          sendMessage(sender, msg.getMessage(), msg.isGlobal());
        } else {
          sendDirectMessage(sender, msg.getMessage(), receiver);
        }
      });
    });
  }

  private void sendDirectMessage(User sender, String message, String receiver) {
    distributor
        .sendMessage(new ChatMessageIn(message, sender.getUsername(), Channel.WHISPER), receiver);
  }

  private void sendMessage(User sender, String message, boolean global) {
    if (global) {
      distributor
          .sendMessageToAllExcept(new ChatMessageIn(message, sender.getUsername(), Channel.GLOBAL),
              sender.getID());
    } else {
      // TODO: implement game channel messaging
    }
  }
}
