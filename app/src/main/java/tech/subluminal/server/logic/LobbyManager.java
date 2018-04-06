package tech.subluminal.server.logic;

import static tech.subluminal.shared.util.IdUtils.generateId;

import java.util.Optional;
import tech.subluminal.server.stores.LobbyStore;
import tech.subluminal.server.stores.ReadOnlyUserStore;
import tech.subluminal.shared.messages.LobbyJoinReq;
import tech.subluminal.shared.messages.LobbyLeaveReq;
import tech.subluminal.shared.net.Connection;
import tech.subluminal.shared.records.Lobby;
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
  }

  private void onLobbyJoin(String userID, LobbyJoinReq req) {
    Optional<Synchronized<Lobby>> lobby = lobbyStore.lobbies().getByID(req.getId());
    if (!lobby.isPresent()) {
      //TODO: Send message to client: Lobby doesn't exist
      return;
    }
    //TODO: Check if player is already in a lobby
    lobby.get().update(l -> {
      if (l.getPlayerCount() >= l.getMaxPlayers()) {
        //TODO: Send message to client: Lobby full
        return l;
      }
      l.addPlayer(userID);
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

  /**
   * Creates a new lobby and adds it to the
   *
   * @param name is the common name of the lobby.
   * @param adminID references the user who created the lobby.
   */
  public void createLobby(String name, String adminID) {
    String id = generateId(6);
    lobbyStore.lobbies().add(new Lobby(id, name, adminID));
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
