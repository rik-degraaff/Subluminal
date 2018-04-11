package tech.subluminal.shared.messages;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

public class PlayerUpdate implements SONRepresentable {
    private static final String ID_KEY = "id";
    private static final String USERNAME_KEY = "username";
    private static final String CLASS_NAME = PlayerUpdate.class.getSimpleName();
    private final String username;
    private final String id;

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public PlayerUpdate(String id, String username){
        this.id = id;
        this.username = username;
    }

    @Override
    public SON asSON() {
        SON update = new SON()
                .put(id, ID_KEY)
                .put(username, USERNAME_KEY);
        return update;
    }

    public static PlayerUpdate fromSON(SON son) throws SONConversionError {
        String id = son.getString(ID_KEY)
                .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, ID_KEY));
        String username = son.getString(USERNAME_KEY)
                .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, USERNAME_KEY));
        return new PlayerUpdate(id, username);
    }
}
