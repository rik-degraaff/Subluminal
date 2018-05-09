package tech.subluminal.server.logic;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tech.subluminal.server.stores.InMemoryLobbyStore;
import tech.subluminal.server.stores.InMemoryUserStore;
import tech.subluminal.server.stores.LobbyStore;
import tech.subluminal.shared.messages.ChatMessageOut;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.test.DistributorTester;

@RunWith(MockitoJUnitRunner.class)
public class ChatManagerTest {

  @Mock
  private MessageDistributor distributor;
  private InMemoryUserStore userStore;
  private LobbyStore lobbystore;
  private ChatManager chatManager;

  @Before
  public void init() {
    this.userStore = new InMemoryUserStore();
    this.lobbystore = new InMemoryLobbyStore();
    this.chatManager = new ChatManager(this.distributor, this.userStore, this.lobbystore);
  }

  @Test
  public void testNullReceiver() {
    Connection connection = mock(Connection.class);

    new DistributorTester(this.distributor, connection);

    ChatMessageOut chatMessageOut = new ChatMessageOut("Hello all.", null, true);
  }
}
