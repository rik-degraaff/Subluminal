package tech.subluminal.client.presentation.customElements;

import java.util.List;
import tech.subluminal.shared.stores.records.game.Coordinates;

public class MotherShipComponent extends ShipComponent {

  public MotherShipComponent(Coordinates coordinates, String playerId,
      List<String> targetIDs) {
    super(coordinates, playerId, targetIDs);
  }
}
