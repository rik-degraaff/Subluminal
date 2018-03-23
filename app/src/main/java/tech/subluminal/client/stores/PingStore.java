package tech.subluminal.client.stores;

import tech.subluminal.shared.records.SentPing;

public interface PingStore {

  SentPing getPing();

  void setPing(SentPing ping);

}
