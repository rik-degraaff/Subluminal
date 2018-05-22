package tech.subluminal.shared.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;
import tech.subluminal.shared.stores.records.User;

public class InitialUsersTest {

  @Test
  public void testStringifyAndParsing() throws SONParsingError, SONConversionError {
    User user1 = new User("sölkdfa", "2917");
    User user2 = new User("asdöflkj", "049851");
    InitialUsers iu = new InitialUsers();
    iu.addUser(user1);
    iu.addUser(user2);
    List<User> users = iu.getUsers();
    String iuStringified = iu.asSON().asString();
    InitialUsers parsedIU = null;
    List<User> parsedUsers = null;
    parsedIU = InitialUsers.fromSON(SON.parse(iuStringified));
    parsedUsers = parsedIU.getUsers();

    boolean containsName = false;
    boolean scoreIsRight = false;
    System.out.println(iuStringified);


    for (int i = 0; i < users.size(); i++) {
      containsName = false;
      scoreIsRight = false;
      for (int j = 0; j < parsedUsers.size(); j++) {
        if (users.get(i).getUsername().equals(parsedUsers.get(j).getUsername())) {
          containsName = true;
          if (users.get(i).getID().equals(parsedUsers.get(j).getID())) {
            scoreIsRight = true;
          }
        }
      }
    }

    assertTrue(containsName);
    assertTrue(scoreIsRight);
    assertEquals(users.size(), parsedUsers.size());
  }
}
