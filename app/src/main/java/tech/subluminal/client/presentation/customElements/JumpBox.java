package tech.subluminal.client.presentation.customElements;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class JumpBox extends Group {

  IntegerProperty shipToSend = new SimpleIntegerProperty();
  //IntegerProperty maxShips = new SimpleIntegerProperty();

  public JumpBox(Property x, Property y) {
    this.layoutXProperty().bind(x);
    this.layoutYProperty().bind(y);
    //this.maxShips.bind(maxAmount);

    VBox box = new VBox();

    HBox shipsAmount = new HBox();
    Label max = new Label();
    //max.textProperty().bind(Bindings
    //    .createStringBinding(() -> maxShipsProperty().getValue().toString(), maxShipsProperty()));

    TextField actual = new TextField();

    actual.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
      if (!"0123456789".contains(keyEvent.getCharacter()) && keyEvent.getCode() != KeyCode.ENTER) {
        keyEvent.consume();
      }
    });

    actual.setOnAction(event -> {
      tryToSend(Integer.parseInt(actual.getText()));
    });

    shipsAmount.getChildren().addAll(actual, max);

    Button send = new Button("Send Ships");
    send.setAlignment(Pos.CENTER);

    send.setOnMouseClicked(event -> {
      String command = actual.getText();

    });

    Button sendMother = new Button("Send Mothership");
    sendMother.setAlignment(Pos.CENTER);

    box.getChildren().addAll(shipsAmount, send, sendMother);

    this.getChildren().add(box);
    box.getStyleClass().add("jumpbox");
  }

  public int getShipToSend() {
    return shipToSend.get();
  }

  public void setShipToSend(int shipToSend) {
    this.shipToSend.set(shipToSend);
  }

  public IntegerProperty shipToSendProperty() {
    return shipToSend;
  }

  /*public int getMaxShips() {
    return maxShips.get();
  }

  public void setMaxShips(int maxShips) {
    this.maxShips.set(maxShips);
  }

  public IntegerProperty maxShipsProperty() {
    return maxShips;
  }
  */

  private void tryToSend(int actual) {
    if (actual >= 0) {
      sendShips(actual);
    } else {
      System.out.println("Not a possiblity");
    }
  }

  private void sendShips(int command) {
    shipToSend.set(command);
  }
}
