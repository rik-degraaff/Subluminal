package tech.subluminal.shared.stores.records;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

/**
 * Record that represents a user.
 */
public class User extends Identifiable implements SONRepresentable {

    private static final String IDENTIFIABLE_KEY = "identifiable";
    private static final String USERNAME_KEY = "username";
    private String username;

    /**
     * Creates a user.
     *
     * @param username to record for a specified user.
     * @param id       to record for a specified user
     */
    public User(String username, String id) {
        super(id);
        this.username = username;
    }

    public static User fromSON(SON son) throws SONConversionError {
        String username = son.getString(USERNAME_KEY)
                .orElseThrow(() -> new SONConversionError("User did not contain a valid " + USERNAME_KEY + "."));

        User user = new User(username, null);
        SON identifiable = son.getObject(IDENTIFIABLE_KEY).orElseThrow(() -> SONRepresentable.error("User", IDENTIFIABLE_KEY));
        user.loadFromSON(identifiable);

        return user;
    }

    /**
     * Gets the username.
     *
     * @return the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username to be set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public SON asSON() {
        return new SON()
                .put(username, USERNAME_KEY)
                .put(super.asSON(), IDENTIFIABLE_KEY);
    }
}
