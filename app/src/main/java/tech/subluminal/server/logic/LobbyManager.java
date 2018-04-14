package tech.subluminal.server.logic;

import static tech.subluminal.shared.util.IdUtils.generateId;
import static tech.subluminal.shared.util.FunctionalUtils.ifPresent;
import java.util.Optional;
import tech.subluminal.server.stores.LobbyStore;
import tech.subluminal.server.stores.ReadOnlyUserStore;
import tech.subluminal.shared.messages.GameStartReq;
import tech.subluminal.shared.messages.LobbyCreateReq;
import tech.subluminal.shared.messages.LobbyJoinReq;
import tech.subluminal.shared.messages.LobbyJoinRes;
import tech.subluminal.shared.messages.LobbyLeaveReq;
import tech.subluminal.shared.messages.LobbyListReq;
import tech.subluminal.shared.messages.LobbyUpdateReq;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.Lobby;
import tech.subluminal.shared.stores.records.LobbySettings;
import tech.subluminal.shared.util.Synchronized;

/**
 * Manages the chat/game lobbies.
 */
public class LobbyManager {

  private final LobbyStore lobbyStore;
  private final ReadOnlyUserStore userStore;
  private final MessageDistributor distributor;

  public LobbyManager(LobbyStore lobbyStore, ReadOnlyUserStore userStore,
      MessageDistributor distributor) {
    this.lobbyStore = lobbyStore;
    this.userStore = userStore;
    this.distributor = distributor;

    distributor.addConnectionOpenedListener(this::attachHandlers);
    distributor.addConnectionClosedListener(this::onConnectionClosed);
  }

  private void onConnectionClosed(String id) {

  }

  private void attachHandlers(String id, Connection connection) {
    connection
        .registerHandler(LobbyJoinReq.class, LobbyJoinReq::fromSON, req -> onLobbyJoin(id, req));
    connection
        .registerHandler(LobbyLeaveReq.class, LobbyLeaveReq::fromSON, req -> onLobbyLeave(id, req));
    connection
        .registerHandler(LobbyListReq.class, LobbyListReq::fromSON, this::onLobbyList);
    connection
        .registerHandler(LobbyCreateReq.class, LobbyCreateReq::fromSON, req -> onLobbyCreate(id, req, connection));
    connection
        .registerHandler(LobbyUpdateReq.class, LobbyUpdateReq::fromSON, req -> onLobbyUpdate(id, req, connection));
    connection
        .registerHandler(GameStartReq.class, GameStartReq::fromSON, req -> onGameStart(id, req));
  }

  private void onGameStart(String userID, GameStartReq req) {
    lobbyStore.lobbies().getLobbiesWithUser(userID).consume(l -> l.stream().findFirst().ifPresent(u -> u.));
    //if
  }

  private void onLobbyUpdate(String userID, LobbyUpdateReq req, Connection connection) {
    LobbySettings settings = req.getSettings();
    lobbyStore.lobbies().getLobbiesWithUser(userID)
        .consume(l -> l.stream().map(syncLobby -> syncLobby
            .update(u -> new Lobby(syncLobby.map((Lobby::getID), settings))));
        //TODO: How to store LobbySettings from message to lobby?
        //TODO: Send new settings to all other players
  }

  private void onLobbyCreate(String userID, LobbyCreateReq req, Connection connection) {
    String lobbyID = generateId(6);
    String name = req.getName();
    Lobby lobby = new Lobby(lobbyID, new LobbySettings(name, userID));
    lobbyStore.lobbies().add(lobby);
    connection.sendMessage(new LobbyJoinRes(lobby));
  }

  private void onLobbyJoin(String userID, LobbyJoinReq req) {
    Optional<Synchronized<Lobby>> lobby = lobbyStore.lobbies().getByID(req.getId());
    if (!lobby.isPresent()) {
      //TODO: Send message to client: Lobby doesn't exist
      return;
    }
    //TODO: Check if player is already in a lobby
    lobby.get().update(l -> {
      if (l.getPlayerCount() >= l.getSettings().getMaxPlayers()) {
        //TODO: Send message to client: Lobby full
        return l;
      }
      l.addPlayer(userID, false);
      //TODO: Send message to client: Successfully joined lobby
      return l;
    });
  }

  private void onLobbyLeave(String userID, LobbyLeaveReq req) {
    lobbyStore.lobbies()
        .getLobbiesWithUser(userID)
        .consume(lobbies ->
            lobbies.forEach(syncLobby -> syncLobby.consume(lobby -> lobby.removePlayer(userID)))
        );
    //TODO: Send message to client: Successfully left lobby
  }

  private void onLobbyList(LobbyListReq req) {

  }

  /**
   * Creates a new lobby and adds it to the
   *
   * @param name is the common name of the lobby.
   * @param adminID references the user who created the lobby.
   */
  public void createLobby(String name, String adminID) {
    String id = generateId(6);
    lobbyStore.lobbies().add(new Lobby(id, new LobbySettings(name, adminID)));
  }

  /**
   * Removes a lobby from the lobby store and removes all player association.
   *
   * @param id is the lobby to be destroyed.
   */
  public void destroyLobby(String id) {
    lobbyStore.lobbies().removeByID(id);
  }
}
