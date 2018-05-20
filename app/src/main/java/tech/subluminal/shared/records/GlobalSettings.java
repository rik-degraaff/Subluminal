package tech.subluminal.shared.records;

import tech.subluminal.shared.util.Export;

/**
 * Store global constants.
 */
public final class GlobalSettings {

  private GlobalSettings() {
  }

  // ======== SHARED SETTINGS ========
  @Export
  public static final String SHARED_DEFAULT_HOST = new String("164.132.199.58");
  @Export
  public static final int SHARED_DEFAULT_PORT = new Integer(1729);
  @Export
  public static final int SHARED_UUID_LENGTH = new Integer(8);

  // ======== SERVER SETTINGS ========
  @Export
  public static final int SERVER_TARGET_TICKS = new Integer(15);


  // ======== CLIENT SETTINGS ========
  @Export
  public static final boolean TESTBOOL = new Boolean(true);

  // ======== PATH + FILE SETTINGS ========
  public static String PATH_JAR; // Is set by the main class on application start.
  public static final String PATH_CONFIG = new String("config");
  public static final String FILE_SETTINGS = new String("constants.properties");
  @Export
  public static final String FILE_HIGHSCORE = new String("highscore.son");

  // ======== GAME SETTINGS ========
  @Export
  public static final double GAME_SPEED = new Double(1.0);
  @Export
  public static final int GAME_MIN_PLAYERS = new Integer(2);
  @Export
  public static final int GAME_MAX_PLAYERS = new Integer(8);
  @Export
  public static final int GAME_MAX_LOBBIES = new Integer(10);
}
