package tech.subluminal.shared.records;

import java.util.Random;
import tech.subluminal.shared.util.Export;

/**
 * Store global constants.
 */
public final class GlobalSettings {

  private GlobalSettings() {
  }

  // Manual boxing of constants is necessary in order for reflections (on private vars) to work.
  // ======== SHARED SETTINGS ========
  @Export
  public static final String SHARED_DEFAULT_HOST = new String("164.132.199.58");
  @Export
  public static final int SHARED_DEFAULT_PORT = new Integer(1729);
  @Export
  public static final int SHARED_UUID_LENGTH = new Integer(8);
  @Export
  public static final long SHARED_SEED = new Integer(0);

  public static final Random SHARED_RANDOM = new Random();

  // ======== SERVER SETTINGS ========
  @Export
  public static final int SERVER_TARGET_TICKS = new Integer(15);
  @Export
  public static final double SERVER_LIGHT_SPEED = new Double(0.05);
  @Export
  public static final double SERVER_JUMP_DISTANCE = new Double(0.4);
  @Export
  public static final double SERVER_SHIP_SPEED_MULTIPLIER = new Double(0.5);
  @Export
  public static final double SERVER_MOTHER_SHIP_SPEED_MULTIPLIER = new Double(0.4);
  @Export
  public static final double SERVER_DEMAT_RATE = new Double(1.0);
  @Export
  public static final double SERVER_GENERATION_RATE = new Double(5.0);
  @Export
  public static final int SERVER_PING_TIMEOUT = new Integer(6000);

  public static final double SERVER_MOTHER_SHIP_SPEED = SERVER_LIGHT_SPEED * SERVER_MOTHER_SHIP_SPEED_MULTIPLIER;
  public static final double SERVER_SHIP_SPEED = SERVER_LIGHT_SPEED * SERVER_SHIP_SPEED_MULTIPLIER;


  // ======== CLIENT SETTINGS ========


  // ======== PATH + FILE SETTINGS ========
  public static String PATH_JAR = new String(""); // Is set by the main class on application start.
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
