package tech.subluminal.client.stores;

import tech.subluminal.shared.stores.ReadOnlySingleEntity;
import tech.subluminal.shared.stores.records.User;

public interface ReadOnlyUserStore {

    ReadOnlySingleEntity<User> currentUser();

    UserCollection users();
}
