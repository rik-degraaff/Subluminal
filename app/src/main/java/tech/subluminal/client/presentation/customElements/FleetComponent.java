package tech.subluminal.client.presentation.customElements;

import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import tech.subluminal.shared.stores.records.game.Coordinates;

public class FleetComponent extends ShipComponent {


  private final IntegerProperty numberOfShips = new SimpleIntegerProperty();

  public FleetComponent(Coordinates coordinates, String playerId,
      List<String> targetIDs) {
    super(coordinates, playerId, targetIDs);
  }

  public FleetComponent(Coordinates coordinates, int numberOfShips, String ID, String ownerID,
      List<String> targetIDs) {
    super(coordinates, ownerID, targetIDs);
    this.setNumberOfShips(numberOfShips);

    Label amount = new Label();

    amount.textProperty().bind(Bindings.createStringBinding(() ->
        this.numberOfShipsProperty().getValue().toString(), numberOfShipsProperty()));

    group.getChildren().add(amount);

  }

  public int getNumberOfShips() {
    return numberOfShips.get();
  }

  public IntegerProperty numberOfShipsProperty() {
    return numberOfShips;
  }

  public void setNumberOfShips(int numberOfShips) {
    this.numberOfShips.set(numberOfShips);
  }
}
