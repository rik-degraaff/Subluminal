package tech.subluminal.shared.stores.records;

import java.util.function.Supplier;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONRepresentable;

public class SlimLobby extends Identifiable implements SONRepresentable {

  private static final String IDENTIFIABLE_KEY = "identifiable";
  private static final String CLASS_NAME = SlimLobby.class.getSimpleName();
  private static final String NAME_KEY = "name";
  private static final String ADMIN_ID_KEY = "adminID";
  private static final String MIN_PLAYERS_KEY = "minPlayers";
  private static final String MAX_PLAYERS_KEY = "maxPlayers";
  private static final String PLAYER_COUNT_KEY = "playerCount";
  // Lobby properties
  private String name;
  private String adminID;
  private int minPlayers = 2;
  private int maxPlayers = 8;
  private int playerCount;

  public SlimLobby(
      String id, String name, String adminID) {
    super(id);
    this.name = name;
    this.adminID = adminID;
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

  public int getPlayerCount() {
    return playerCount;
  }

  public void setPlayerCount(int playerCount) {
    this.playerCount = playerCount;
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    return new SON()
        .put(super.asSON(), IDENTIFIABLE_KEY)
        .put(getName(), NAME_KEY)
        .put(getAdminID(), ADMIN_ID_KEY)
        .put(getMinPlayers(), MIN_PLAYERS_KEY)
        .put(getMaxPlayers(), MAX_PLAYERS_KEY)
        .put(getPlayerCount(), PLAYER_COUNT_KEY);
  }

  public static <E extends SlimLobby> E fromSON(SON son, Supplier<E> lobbySupplier)
      throws SONConversionError {
    E lobby = lobbySupplier.get();

    SON identifiable = son.getObject(IDENTIFIABLE_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, IDENTIFIABLE_KEY));
    lobby.loadFromSON(identifiable);

    lobby.setName(son.getString(NAME_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, NAME_KEY)));

    lobby.setAdminID(son.getString(ADMIN_ID_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, ADMIN_ID_KEY)));

    lobby.setMinPlayers(son.getInt(MIN_PLAYERS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, MIN_PLAYERS_KEY)));

    lobby.setMaxPlayers(son.getInt(MAX_PLAYERS_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, MAX_PLAYERS_KEY)));

    lobby.setPlayerCount(son.getInt(PLAYER_COUNT_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, PLAYER_COUNT_KEY)));

    return lobby;
  }

  public static SlimLobby fromSON(SON son) throws SONConversionError {
    return fromSON(son, () -> new SlimLobby(null, null, null));
  }
}
