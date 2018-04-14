package tech.subluminal.shared.messages;

import java.util.LinkedList;
import java.util.List;
import javafx.collections.ObservableList;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONList;
import tech.subluminal.shared.son.SONRepresentable;
import tech.subluminal.shared.stores.records.SlimLobby;

public class LobbyListRes implements SONRepresentable{

  private static final String CLASS_NAME = LobbyListRes.class.getSimpleName();
  private static final String LOBBIES_KEY = "lobbies";

  private List<SlimLobby> slimLobbies = new LinkedList<>();

  public LobbyListRes(List<SlimLobby> slimLobbies) {
    this.slimLobbies = slimLobbies;
  }

  public static LobbyListRes fromSON(SON son) throws SONConversionError {
    SONList slimLobbies = son.getList(LOBBIES_KEY)
        .orElseThrow(() -> SONRepresentable.error(CLASS_NAME, LOBBIES_KEY));
    List<SlimLobby> lobbies = new LinkedList<>();
    ObservableList<SlimLobby> = new Observa
    for (SON lobby : slimLobbies.objects()) {
      lobbies.add(SlimLobby.fromSON(lobby));
    }
    return new LobbyListRes(lobbies);
  }

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
