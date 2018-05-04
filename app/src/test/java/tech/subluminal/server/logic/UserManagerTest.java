package tech.subluminal.server.logic;

import static junit.framework.TestCase.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnitRunner;
import tech.subluminal.server.stores.InMemoryUserStore;
import tech.subluminal.server.stores.UserStore;
import tech.subluminal.shared.messages.LoginReq;
import tech.subluminal.shared.messages.LoginRes;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONConverter;

@RunWith(MockitoJUnitRunner.class)
public class UserManagerTest {

  @Captor
  private ArgumentCaptor<BiConsumer<String, Connection>> onOpenedCaptor;

  @Captor
  private ArgumentCaptor<SONConverter<LoginReq>> loginConverterCaptor;

  @Captor
  private ArgumentCaptor<Consumer<LoginReq>> onLoginCaptor;

  @Test
  public void test() {
    Connection c = mock(Connection.class);
    UserStore userStore = new InMemoryUserStore();
    MessageDistributor messageDistributor = mock(MessageDistributor.class);

    UserManager userManager = new UserManager(userStore, messageDistributor);

    System.out.println(onOpenedCaptor);

    verify(messageDistributor).addConnectionOpenedListener(onOpenedCaptor.capture());

    onOpenedCaptor.getValue().accept("1", c);

    verify(c, times(1))
        .registerHandler(eq(LoginReq.class), loginConverterCaptor.capture(), onLoginCaptor.capture());
    //verify(c, times(2)).registerHandler(any(), any(), any());

    LoginReq loginReq = null;
    try {
      loginReq = loginConverterCaptor.getValue().convert(new LoginReq("test").asSON());
    } catch (SONConversionError sonConversionError) {
      fail("UserManager failed to parse LoginReq correctly");
    }
    onLoginCaptor.getValue().accept(loginReq);

    verify(c).sendMessage(any(LoginRes.class));
  }
}
