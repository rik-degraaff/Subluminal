package tech.subluminal.client.logic;

import tech.subluminal.client.presentation.ChatPresenter;
import tech.subluminal.client.stores.UserStore;
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
    //TODO: implement whole class
  }

  @Override
  public void sendGameMessage(String message) {

  }

  @Override
  public void sendWhisperMessage(String message, String username) {

  }
}
