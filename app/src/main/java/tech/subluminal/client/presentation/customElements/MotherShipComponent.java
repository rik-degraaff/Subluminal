package tech.subluminal.client.presentation.customElements;

import java.util.List;
import tech.subluminal.client.stores.GameStore;
import tech.subluminal.shared.stores.records.game.Coordinates;

public class MotherShipComponent extends ShipComponent {

  public MotherShipComponent(Coordinates coordinates, String playerId,
      List<String> targetIDs, GameStore gamestore) {
    super(coordinates, playerId, targetIDs, gamestore);
  }
}
