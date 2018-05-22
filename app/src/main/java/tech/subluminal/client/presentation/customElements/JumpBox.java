package tech.subluminal.client.presentation.customElements;

import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import tech.subluminal.client.presentation.controller.MainController;
import tech.subluminal.client.presentation.customElements.custom3DComponents.Button3dComponent;

public class JumpBox extends Group {

  IntegerProperty shipToSend = new SimpleIntegerProperty();

  public JumpBox(MainController main, Consumer<Integer> onSendFleet, Runnable onSendMotherShip) {

    VBox box = new VBox();

    TextField actual = new TextField();
    Platform.runLater(() -> {
      actual.requestFocus();
    });


    actual.addEventFilter(KeyEvent.KEY_TYPED, keyEvent -> {
      if (!"0123456789".contains(keyEvent.getCharacter()) && keyEvent.getCode() != KeyCode.ENTER) {
        keyEvent.consume();
      }
    });

    Button3dComponent send = new Button3dComponent("Ships");


    send.setOnMouseClicked(event -> {
      fireSendFleet(onSendFleet, actual, main);
      event.consume();
    });

    Button3dComponent sendMother = new Button3dComponent("Mothership");

    sendMother.setOnMouseClicked(event -> {
      fireSendMother(onSendMotherShip, main);
      event.consume();
    });

    actual.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
      if(keyEvent.getCode() == KeyCode.ENTER){
        keyEvent.consume();
        if(!actual.getText().equals("")){
          fireSendFleet(onSendFleet, actual, main);
        }else{
          fireSendMother(onSendMotherShip, main);
        }
      }
    });

    //box.getChildren().addAll(actual, send, sendMother);

    main.setAmountBox(actual, sendMother, send);


  }

  private void fireSendFleet(Consumer<Integer> onSendFleet, TextField actual, MainController main) {
    String text = actual.getText();
    int amount = 0;
    if(text != ""){
      amount = Integer.parseInt(text);
    }
    if(amount != 0) {
      onSendFleet.accept(amount);
    }else{
      onSendFleet.accept(Integer.MAX_VALUE);
    }
    main.resetAmounBox();
  }

  private void fireSendMother(Runnable onSendMotherShip,
      MainController main) {
    onSendMotherShip.run();
    main.resetAmounBox();
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
}
