package tech.subluminal.client.stores;

import tech.subluminal.shared.stores.records.SentPing;
import tech.subluminal.shared.stores.SingleEntity;

public interface PingStore {

    /**
     * @return the last ping that was last sent to the server.
     */
    SingleEntity<SentPing> lastPing();
}
