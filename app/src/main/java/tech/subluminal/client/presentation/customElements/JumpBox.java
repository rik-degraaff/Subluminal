package tech.subluminal.client.presentation.customElements;

import java.util.function.Consumer;
import javafx.application.Platform;
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
import javafx.scene.transform.Translate;
import org.pmw.tinylog.Logger;

public class JumpBox extends Group {

  IntegerProperty shipToSend = new SimpleIntegerProperty();

  public JumpBox(Property x, Property y, Consumer<Integer> onSendFleet, Runnable onSendMotherShip) {
    this.layoutXProperty().bind(x);
    this.layoutYProperty().bind(y);

    VBox box = new VBox();

    HBox shipsAmount = new HBox();
    Label max = new Label();

    TextField actual = new TextField();

    actual.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
      if (!"0123456789".contains(keyEvent.getCharacter()) && keyEvent.getCode() != KeyCode.ENTER) {
        keyEvent.consume();
      }
    });

    shipsAmount.getChildren().addAll(actual, max);

    Button send = new Button("Send Ships");
    send.setAlignment(Pos.CENTER);

    send.setOnMouseClicked(event -> {
      int amount = Integer.parseInt(actual.getText());
      onSendFleet.accept(amount);
    });

    Button sendMother = new Button("Send Mothership");
    sendMother.setAlignment(Pos.CENTER);

    sendMother.setOnMouseClicked(event -> {
      onSendMotherShip.run();
    });

    box.getChildren().addAll(shipsAmount, send, sendMother);

    this.getChildren().add(box);
    box.getStyleClass().add("jumpbox");

    Platform.runLater(() -> {
      if((double) x.getValue() >= getScene().getWidth()/2){
        //right side
        if((double) y.getValue() >= getScene().getHeight()/2){
          //up
          Logger.debug("right down");
          box.layoutXProperty().unbind();
          box.layoutXProperty().bind(Bindings.createDoubleBinding(()-> -box.getWidth(), box.widthProperty()));
          box.layoutYProperty().bind(Bindings.createDoubleBinding(()-> -box.getHeight(), box.heightProperty()));
        }else{
          Logger.debug("right up");
          box.layoutXProperty().unbind();
          box.layoutXProperty().bind(Bindings.createDoubleBinding(()-> -box.getWidth(), box.widthProperty()));
        }
      }else{
        //left side
        if((double) y.getValue() >= getScene().getHeight()/2){
          //up
          Logger.debug("left down");
          box.layoutYProperty().unbind();
          box.layoutYProperty().bind(Bindings.createDoubleBinding(()-> -box.getHeight(), box.heightProperty()));
        }else{
          Logger.debug("left up");
          box.layoutYProperty().unbind();
          box.layoutYProperty().setValue(0);
        }
      }
    });


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
