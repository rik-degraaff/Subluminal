package tech.subluminal.client.stores;

import tech.subluminal.shared.stores.records.SentPing;

public interface PingStore {

  /**
   * Returns the last Ping.
   */
  SentPing getPing();

  /**
   * Sets the ping.
   *
   * @param ping is the ping to be set.
   */
  void setPing(SentPing ping);

}
