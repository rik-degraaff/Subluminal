package tech.subluminal.server.logic;

import java.util.Optional;
import tech.subluminal.server.stores.ReadOnlyUserStore;
import tech.subluminal.server.stores.UserStore;
import tech.subluminal.shared.messages.ChatMessageIn;
import tech.subluminal.shared.messages.ChatMessageIn.Channel;
import tech.subluminal.shared.messages.ChatMessageOut;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.records.User;

/**
 * Manages the chat messages that are sent to and received from the clients
 */
public class ChatManager {

  private final ReadOnlyUserStore userStore;
  private final MessageDistributor distributor;

  public ChatManager(MessageDistributor distributor, ReadOnlyUserStore userStore) {
    this.userStore = userStore;
    this.distributor = distributor;

    distributor.addConnectionOpenedHandler(this::attachHandlers);
  }

  private void attachHandlers(String id, Connection connection) {
    connection.registerHandler(ChatMessageOut.class, ChatMessageOut::fromSON, msg -> chatMessageReceived(msg, id));
  }

  private void chatMessageReceived(ChatMessageOut msg, String userID) {
    String receiver = msg.getReceiverID();
    Optional<User> sender = Optional.empty();
    synchronized (userStore) {
      sender = userStore.getUserByID(userID);
    }
    sender.ifPresent(s -> {
      if (receiver == null) {
        sendMessage(s, msg.getMessage(), msg.isGlobal());
      } else {
        sendDirectMessage(s, msg.getMessage(), receiver);
      }
    });
  }

  private void sendDirectMessage(User sender, String message, String receiver) {
    distributor.sendMessage(new ChatMessageIn(message, sender.getId(), Channel.WHISPER), receiver);
  }

  private void sendMessage(User sender, String message, boolean global) {
    if (global) {
      distributor.sendMessageToAllExcept(new ChatMessageIn(message, sender.getId(), Channel.GLOBAL), sender.getId());
    } else {
      // TODO: implement game channel messaging
    }
  }
}
