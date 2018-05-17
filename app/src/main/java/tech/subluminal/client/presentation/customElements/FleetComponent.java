package tech.subluminal.client.presentation.customElements;

import java.util.List;
import tech.subluminal.client.presentation.controller.MainController;
import tech.subluminal.client.stores.GameStore;
import tech.subluminal.shared.stores.records.game.Coordinates;

public class FleetComponent extends ShipComponent {


  public FleetComponent(Coordinates coordinates, int numberOfShips, String ID, String ownerID,
      List<String> targetIDs, GameStore gamestore, MainController main) {
    super(coordinates, numberOfShips, ID, ownerID, targetIDs, gamestore, main);
    this.setNumberOfShips(numberOfShips);

  }

}
