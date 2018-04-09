package tech.subluminal.client.stores;

import tech.subluminal.shared.stores.records.SentPing;
import tech.subluminal.shared.stores.records.SingleEntity;

public class InMemoryPingStore implements PingStore {

    private SingleEntity<SentPing> lastPing = new SingleEntity<>();

    /**
     * @return the last ping that was last sent to the server.
     */
    @Override
    public SingleEntity<SentPing> lastPing() {
        return lastPing;
    }
}
