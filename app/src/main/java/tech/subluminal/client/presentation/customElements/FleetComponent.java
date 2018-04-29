package tech.subluminal.client.presentation.customElements;

import java.util.List;
import tech.subluminal.shared.stores.records.game.Coordinates;

public class FleetComponent extends ShipComponent {


  public FleetComponent(Coordinates coordinates, int numberOfShips, String ID, String ownerID,
      List<String> targetIDs) {
    super(coordinates, numberOfShips, ID, ownerID, targetIDs);
    this.setNumberOfShips(numberOfShips);

  }

}
