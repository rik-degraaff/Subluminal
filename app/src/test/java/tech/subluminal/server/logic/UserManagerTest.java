package tech.subluminal.server.logic;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import tech.subluminal.server.stores.InMemoryUserStore;
import tech.subluminal.shared.messages.LoginReq;
import tech.subluminal.shared.messages.LoginRes;
import tech.subluminal.shared.messages.LogoutReq;
import tech.subluminal.shared.messages.UsernameReq;
import tech.subluminal.shared.messages.UsernameRes;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.test.DistributorTester;
import tech.subluminal.test.MessageHandlerTester;

@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {

  @Mock
  private MessageDistributor distributor;
  private UserManager userManager;
  private InMemoryUserStore userStore;

  @Before
  public void setup() {
    this.userStore = new InMemoryUserStore();
    userManager = new UserManager(userStore, distributor);
  }

  @Test
  public void testLogin() {
    Connection connection = mock(Connection.class);

    new DistributorTester(distributor, connection);
    new MessageHandlerTester<>(connection, LoginReq.class, new LoginReq("test"));

    verify(connection).sendMessage(refEq(new LoginRes("test", "0")));
  }

  @Test
  public void testNameChange() {
    Connection connection = mock(Connection.class);

    new DistributorTester(distributor, connection);

    new MessageHandlerTester<>(connection, LoginReq.class, new LoginReq("test"));
    MessageHandlerTester<UsernameReq> messageHandlerTester = new MessageHandlerTester<>(connection,
        UsernameReq.class, new UsernameReq("Bob"));

    verify(connection, atLeast(0)).sendMessage(not(isA(UsernameRes.class)));
    verify(connection).sendMessage(refEq(new UsernameRes("Bob")));

    messageHandlerTester.sendMessage(new UsernameReq("Bob"));

    verify(connection, atLeast(0)).sendMessage(not(isA(UsernameRes.class)));
    verify(connection).sendMessage(refEq(new UsernameRes("Bob1")));
  }

  @Test
  public void testLogout() {
    Connection connection = mock(Connection.class);

    new DistributorTester(distributor, connection);
    new MessageHandlerTester<>(connection, LoginReq.class, new LoginReq("test"));
    new MessageHandlerTester<>(connection, LogoutReq.class, new LogoutReq());
    try {
      verify(connection).close();
    } catch (IOException e) {
      fail("Verifying connection close was called inexplicably threw an error");
    }
  }
}
