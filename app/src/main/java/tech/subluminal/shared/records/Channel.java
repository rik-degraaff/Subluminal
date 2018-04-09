package tech.subluminal.shared.records;

/**
 * All channels a ChatMessage can be sent in.
 */
public enum Channel {
  //SERVER can not be muted and is only used by the server.
  WHISPER, GAME, GLOBAL, SERVER, SYSTEM
}
