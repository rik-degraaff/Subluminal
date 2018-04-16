package tech.subluminal.shared.messages;

import java.util.LinkedList;
import java.util.List;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONList;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.User;

/**
 * Represents the initial users connected to the server.
 */
public class InitialUsers implements SONRepresentable {

  private static final String USERS_KEY = "users";
  private static final String CLASS_NAME = InitialUsers.class.getSimpleName();
  private final List<User> users = new LinkedList<>();

  /**
   * Creates an InitialUsers object from its SON representation.
   *
   * @param son the SON representation of an InitialUsers object.
   * @return the InitialUsers object, converted from its SON representation.
   * @throws SONConversionError if the conversion fails.
   */
  public static InitialUsers fromSON(SON son) throws SONConversionError {
    InitialUsers initialUsers = new InitialUsers();

    SONList list = son.getList(USERS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, USERS_KEY));
    for (SON object : list.objects()) {
      initialUsers.addUser(User.fromSON(object));
    }
    return initialUsers;
  }

  /**
   * @return the users.
   */
  public List<User> getUsers() {
    return users;
  }

  /**
   * @param user a user to add.
   */
  public void addUser(User user) {
    users.add(user);
  }

  /**
   * @return the SON representation of this object.
   */
  @Override
  public SON asSON() {
    SONList list = new SONList();
    users.stream().map(User::asSON).forEach(list::add);
    return new SON().put(list, USERS_KEY);
  }
}
