import java.util.Arrays;
import org.junit.Test;
import tech.subluminal.client.stores.records.game.Player;
import tech.subluminal.shared.messages.GameStateDelta;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.Fleet;
import tech.subluminal.shared.stores.records.game.Ship;
import tech.subluminal.shared.stores.records.game.Star;

import static org.junit.Assert.assertEquals;

public class TestTests  {
  
  @Test
  public void StringTest() {
    String s = "Test";
    assertEquals(s, "Test");
    GameStateDelta delta = new GameStateDelta();
    delta.addPlayer(new Player("1234",
        new Ship(new Coordinates(1.0, 2.3), "4321", Arrays.asList("1", "2", "3")),
        Arrays.asList(
            new Fleet(new Coordinates(1.0, 2.3), 17, "4321321", Arrays.asList("2", "3")),
            new Fleet(new Coordinates(0.0, 2000.13), 17, "4qd321", Arrays.asList("1", "2", "3")),
            new Fleet(new Coordinates(1.0, 12.3), 17, "432gte1", Arrays.asList())
        )));

    delta.addPlayer(new Player("2345",
        new Ship(new Coordinates(10.0, 2.3), "4321", Arrays.asList("5")), Arrays.asList()));

    delta.addStar(new Star("1234", 1, new Coordinates(0, 0), "starid"));
    delta.addStar(new Star(null, 0, new Coordinates(0, 42), "starid2"));

    delta.addRemovedFleet("1234", "645");
    delta.addRemovedFleet("1234", "123");
    delta.addRemovedFleet("4321", "345");
    delta.addRemovedFleet("4321", "134vj");

    delta.addRemovedPlayer("ftdq");

    System.out.println(delta.asSON().asString());
  }
}
