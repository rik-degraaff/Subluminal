package tech.subluminal.client.logic;

import tech.subluminal.client.presentation.ChatPresenter;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.messages.ChatMessageOut;
import tech.subluminal.shared.net.Connection;

public class ChatManager implements ChatPresenter.Delegate{

  private final UserStore userStore;
  private final ChatPresenter chatPresenter;
  private final Connection connection;

  public ChatManager(UserStore userStore, ChatPresenter chatPresenter, Connection connection){
    this.userStore = userStore;
    this.chatPresenter = chatPresenter;
    this.connection = connection;

    chatPresenter.setChatDelegate(this);
  }

  @Override
  public void sendGlobalMessage(String message) {
    connection.sendMessage(new ChatMessageOut(message, null, true);
  }
    //TODO: implement whole class
  @Override
  public void sendGameMessage(String message) {
    connection.sendMessage(new ChatMessageOut(message, null, false));
  }

  @Override
  public void sendWhisperMessage(String message, String username) {
    //TODO: handle error
    connection.sendMessage(new ChatMessageOut(message, userStore.getUserByUsername(username).getUsername(), false));
  }
}
