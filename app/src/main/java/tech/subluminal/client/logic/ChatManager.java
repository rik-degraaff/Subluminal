package tech.subluminal.client.logic;

import static tech.subluminal.shared.util.function.IfPresent.ifPresent;

import java.util.Optional;
import tech.subluminal.client.presentation.ChatPresenter;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.messages.ChatMessageIn;
import tech.subluminal.shared.messages.ChatMessageOut;
import tech.subluminal.shared.messages.HighScoreReq;
import tech.subluminal.shared.messages.HighScoreRes;
import tech.subluminal.shared.messages.YouLose;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.stores.records.User;

public class ChatManager implements ChatPresenter.Delegate {

  private final UserStore userStore;
  private final ChatPresenter chatPresenter;
  private final Connection connection;

  /**
   * Holds the connection and coordinates the userStore and the chatPresenter.
   *
   * @param userStore holds all active users.
   * @param chatPresenter handles the representation of the chat.
   * @param connection is the active socket to communicate with the server.
   */
  public ChatManager(UserStore userStore, ChatPresenter chatPresenter, Connection connection) {
    this.userStore = userStore;
    this.chatPresenter = chatPresenter;
    this.connection = connection;

    chatPresenter.setChatDelegate(this);

    connection.registerHandler(
        ChatMessageIn.class, ChatMessageIn::fromSON, this::onMessageReceived);
    connection.registerHandler(
        HighScoreRes.class, HighScoreRes::fromSON, this::onHighScoresReceived);
    connection.registerHandler(
        YouLose.class, YouLose::fromSON, this::onLose);
  }

  private void onLose(YouLose message) {
    chatPresenter.displaySystemMessage("YOU LOSE!");
  }

  private void onHighScoresReceived(HighScoreRes req) {
    System.out.println(req.getHighScores());
    chatPresenter.displaySystemMessage(
        req.getHighScores().stream().map(hs -> hs.getUsername() + ": " + hs.getScore())
            .reduce("", (acc, s) -> acc + System.lineSeparator() + s));
  }

  private void onMessageReceived(ChatMessageIn message) {
    switch (message.getChannel()) {
      case GAME:
        chatPresenter.gameMessageReceived(message.getMessage(), message.getUsername());
        break;
      case GLOBAL:
        chatPresenter.globalMessageReceived(message.getMessage(), message.getUsername());
        break;
      case WHISPER:
        chatPresenter.whisperMessageReceived(message.getMessage(), message.getUsername());
        break;
      default:
        System.err.println("The object did not contain a valid channel.");
    }
  }

  @Override
  public void sendGlobalMessage(String message) {
    connection.sendMessage(new ChatMessageOut(message, null, true));
  }

  @Override
  public void sendGameMessage(String message) {
    connection.sendMessage(new ChatMessageOut(message, null, false));
  }

  @Override
  public void sendWhisperMessage(String message, String username) {
    Optional<String> optID = userStore.users()
        .getByUsername(username)
        .use(us -> us.stream().map(syncUser -> syncUser.use(User::getID)))
        .findFirst();

    ifPresent(optID)
        .then(id -> connection.sendMessage(new ChatMessageOut(message, id, false)))
        .els(() -> chatPresenter.displaySystemMessage(username + " does not exist or is not online."));
  }

  public void requestHighScores() {
    System.out.println("request highscores");
    connection.sendMessage(new HighScoreReq());
  }
}
