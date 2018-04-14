package tech.subluminal.shared.records;

/**
 * All channels a ChatMessage can be sent in.
 */
public enum Channel {
  //INFO can not be muted and is only used by the server.
  WHISPER, GAME, GLOBAL, INFO, CRITICAL
}
