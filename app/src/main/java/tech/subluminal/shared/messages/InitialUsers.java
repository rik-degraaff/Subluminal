package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONList;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.User;

import java.util.LinkedList;
import java.util.List;

public class InitialUsers implements SONRepresentable {

    private static final String USERS_KEY = "users";
    private static final String CLASS_NAME = InitialUsers.class.getSimpleName();
    private final List<User> users = new LinkedList<>();

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user){
        users.add(user);
    }

    @Override
    public SON asSON() {
        SONList list = new SONList();
        users.stream().map(User::asSON).forEach(list::add);
        return new SON().put(list, USERS_KEY);
    }

    public static InitialUsers fromSON(SON son) throws SONConversionError {
        InitialUsers initialUsers = new InitialUsers();

        SONList list = son.getList(USERS_KEY)
                .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, USERS_KEY));
        for(SON object : list.objects()){
            initialUsers.addUser(User.fromSON(object));
        }
        return initialUsers;
    }
}
