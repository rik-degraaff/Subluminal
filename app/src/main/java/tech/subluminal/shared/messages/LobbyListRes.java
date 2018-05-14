package tech.subluminal.shared.messages;

import java.util.LinkedList;
import java.util.List;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONList;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.SlimLobby;

/**
 * Represents a lobby list response after a lobby list request. This message, when converted to SON
 * and then to string, might look like this:
 * <pre>
 * {
 *   "lobbies":l[
 *     o{
 *       "playerCount":i0,
 *       "status":s"FULL",
 *       "settings":o{
 *         "maxPlayers":i8,
 *         "minPlayers":i2,
 *         "mapSize":d2.0,
 *         "gameSpeed":d1.0,
 *         "name":s"weoi",
 *         "adminID":s"3472"
 *       },
 *       "identifiable":o{
 *         "id":s"9439"
 *       }
 *     },
 *     o{
 *       "playerCount":i0,
 *       "status":s"LOCKED",
 *       "settings":o{
 *         "maxPlayers":i8,
 *         "minPlayers":i2,
 *         "mapSize":d2.0,
 *         "gameSpeed":d1.0,
 *         "name":s"eruo",
 *         "adminID":s"1338"
 *       },
 *       "identifiable":o{
 *         "id":s"1790"
 *       }
 *     },
 *     o{
 *       "playerCount":i0,
 *       "status":s"OPEN",
 *       "settings":o{
 *         "maxPlayers":i8,
 *         "minPlayers":i2,
 *         "mapSize":d2.0,
 *         "gameSpeed":d1.0,
 *         "name":s"sdfu",
 *         "adminID":s"1237"
 *       },
 *       "identifiable":o{
 *         "id":s"1295"
 *       }
 *     }
 *   ]
 * }
 * </pre>
 */
public class LobbyListRes implements SONRepresentable {

  private static final String CLASS_NAME = LobbyListRes.class.getSimpleName();
  private static final String LOBBIES_KEY = "lobbies";

  private List<SlimLobby> slimLobbies = new LinkedList<>();

  /**
   * Creates a new lobby list response with a slimmed down representation of the lobbies.
   *
   * @param slimLobbies the existing lobbies.
   */
  public LobbyListRes(List<SlimLobby> slimLobbies) {
    this.slimLobbies = slimLobbies;
  }

  /**
   * Creates and returns a new response to a lobby list request, converted from it SON
   * representation.
   *
   * @param son the SON representation of a lobby list response.
   * @return the lobby list response, converted from its SON representation.
   * @throws SONConversionError if the conversion fails.
   */
  public static LobbyListRes fromSON(SON son) throws SONConversionError {
    SONList slimLobbies = son.getList(LOBBIES_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, LOBBIES_KEY));
    List<SlimLobby> lobbies = new LinkedList<>();
    for (SON lobby : slimLobbies.objects()) {
      lobbies.add(SlimLobby.fromSON(lobby));
    }
    return new LobbyListRes(lobbies);
  }

  /**
   * @return the requested list of lobbies.
   */
  public List<SlimLobby> getSlimLobbies() {
    return slimLobbies;
  }

  /**
   * Creates a SON object representing this object.
   *
   * @return the SON representation.
   */
  @Override
  public SON asSON() {
    SONList lobbies = new SONList();
    slimLobbies.stream().map(SlimLobby::asSON).forEach(lobbies::add);

    return new SON()
        .put(lobbies, LOBBIES_KEY);
  }
}
