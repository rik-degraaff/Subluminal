package tech.subluminal.shared.stores.records;

import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

public class LobbySettings implements SONRepresentable {

  private static final String CLASS_NAME = LobbySettings.class.getSimpleName();
  private static final String NAME_KEY = "name";
  private static final String ADMIN_ID_KEY = "adminID";
  private static final String MIN_PLAYERS_KEY = "minPlayers";
  private static final String MAX_PLAYERS_KEY = "maxPlayers";
  private static final String PLAYER_COUNT_KEY = "playerCount";
  private static final String GAME_SPEED_KEY = "gameSpeed";
  private static final String MAP_SIZE_KEY = "mapSize";

  // Lobby properties
  private String name;
  private String adminID;
  private int minPlayers = 2;
  private int maxPlayers = 8;
  //TODO: Make lobbies password protected
  //private boolean protected;
  //private String password;

  // Game Settings
  private double gameSpeed = 1.0;
  private double mapSize = 2.0;

  public LobbySettings(String name, String adminID) {
    this.name = name;
    this.adminID = adminID;
  }

  public LobbySettings(String name, String adminID, int minPlayers, int maxPlayers,
      double gameSpeed, double mapSize) {
    this.name = name;
    this.adminID = adminID;
    this.minPlayers = minPlayers;
    this.maxPlayers = maxPlayers;
    this.gameSpeed = gameSpeed;
    this.mapSize = mapSize;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAdminID() {
    return adminID;
  }

  public void setAdminID(String adminID) {
    this.adminID = adminID;
  }

  public int getMinPlayers() {
    return minPlayers;
  }

  public void setMinPlayers(int minPlayers) {
    this.minPlayers = minPlayers;
  }

  public int getMaxPlayers() {
    return maxPlayers;
  }

  public void setMaxPlayers(int maxPlayers) {
    this.maxPlayers = maxPlayers;
  }

  public double getGameSpeed() {
    return gameSpeed;
  }

  public void setGameSpeed(double gameSpeed) {
    this.gameSpeed = gameSpeed;
  }

  public double getMapSize() {
    return mapSize;
  }

  public void setMapSize(double mapSize) {
    this.mapSize = mapSize;
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON()
        .put(getName(), NAME_KEY)
        .put(getAdminID(), ADMIN_ID_KEY)
        .put(getMinPlayers(), MIN_PLAYERS_KEY)
        .put(getMaxPlayers(), MAX_PLAYERS_KEY)
        .put(getGameSpeed(), GAME_SPEED_KEY)
        .put(getMapSize(), MAP_SIZE_KEY);
  }

  public static LobbySettings fromSON(SON son) throws SONConversionError {
    LobbySettings settings = new LobbySettings(null, null);
    settings.setName(son.getString(NAME_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, NAME_KEY)));
    settings.setAdminID(son.getString(ADMIN_ID_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, ADMIN_ID_KEY)));
    settings.setMinPlayers(son.getInt(MIN_PLAYERS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, MIN_PLAYERS_KEY)));
    settings.setMaxPlayers(son.getInt(MAX_PLAYERS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, MAX_PLAYERS_KEY)));
    settings.setGameSpeed(son.getDouble(GAME_SPEED_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, GAME_SPEED_KEY)));
    settings.setMapSize(son.getDouble(MAP_SIZE_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, MAP_SIZE_KEY)));
    return settings;
  }
}
