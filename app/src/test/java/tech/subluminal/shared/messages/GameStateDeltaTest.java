package tech.subluminal.shared.messages;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import tech.subluminal.client.stores.records.game.Player;
import tech.subluminal.shared.records.LobbyStatus;
import tech.subluminal.shared.son.SON;
import tech.subluminal.shared.son.SONConversionError;
import tech.subluminal.shared.son.SONParsingError;
import tech.subluminal.shared.stores.records.LobbySettings;
import tech.subluminal.shared.stores.records.SlimLobby;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.Fleet;
import tech.subluminal.shared.stores.records.game.Ship;
import tech.subluminal.shared.stores.records.game.Star;

public class GameStateDeltaTest {

  @Test
  public void stringifyAndParseGameStateDelta() {
    GameStateDelta delta = new GameStateDelta();
    delta.addPlayer(new Player("1234",
        new Ship(new Coordinates(1.0, 2.3), "4321", Arrays.asList("1", "2", "3"), "3", 0.2),
        Arrays.asList(
            new Fleet(new Coordinates(1.0, 2.3), 17, "4321321", Arrays.asList("2", "3"), "3", 0.2),
            new Fleet(new Coordinates(0.0, 2000.13), 17, "4qd321", Arrays.asList("1", "2", "3"),
                "3",
                0.2),
            new Fleet(new Coordinates(1.0, 12.3), 17, "432gte1", Collections.singletonList("2"),
                "2", 0.2)
        )));

    delta.addPlayer(new Player("2345",
        new Ship(new Coordinates(10.0, 2.3), "4321", Collections.emptyList(), "5", 0.2),
        Collections.emptyList()));

    delta.addStar(new Star("1234", 1, new Coordinates(0, 0), "starid", true, 0.1));
    delta.addStar(new Star(null, 0, new Coordinates(0, 42), "starid2", false, 0.3));

    delta.addRemovedFleet("1234", "645");
    delta.addRemovedFleet("1234", "123");
    delta.addRemovedFleet("4321", "345");
    delta.addRemovedFleet("4321", "134vj");

    delta.addRemovedPlayer("ftdq");

    String msg = delta.asSON().asString();
    try {
      GameStateDelta parsedDelta = GameStateDelta.fromSON(SON.parse(msg));
      assertNotNull(parsedDelta);
    } catch (SONConversionError | SONParsingError sonConversionError) {
      sonConversionError.printStackTrace();
    }
    System.out.println(msg);
  }

  @Test
  public void easyTest() {
    System.out.println(
        new SlimLobby("gwewef", new LobbySettings("name", "adminID"), LobbyStatus.OPEN).asSON()
            .asString());
  }
}
