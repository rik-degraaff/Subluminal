package tech.subluminal.client.stores;

import tech.subluminal.shared.stores.records.SentPing;

public class InMemoryPingStore implements PingStore {

  private SentPing ping;

  @Override
  public SentPing getPing() {
    return ping;
  }

  @Override
  public void setPing(SentPing ping) {
    this.ping = ping;
  }
}
