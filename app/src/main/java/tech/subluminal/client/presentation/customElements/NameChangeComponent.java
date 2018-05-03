package tech.subluminal.client.presentation.customElements;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import tech.subluminal.client.presentation.controller.MainController;

public class NameChangeComponent extends HBox {

  private boolean isOpen;
  private TextField field = new TextField();
  Button change;

  public NameChangeComponent(MainController main){
    HBox box = new HBox();
    Button nameButton = new Button("C");

    change = new Button("Change Name");
    box.getChildren().addAll(field, change);
    change.setOnAction(e -> {
      main.getChatController().changeName(field.getText());
      field.setText("");
      this.getChildren().remove(box);
    });

    this.getChildren().add(nameButton);
    nameButton.setOnAction(event -> {
      if(isOpen == false){
        openNamechange(box, nameButton);
      }else{
        closeNamechange(box, nameButton);
      }

    });

    this.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
      if(keyEvent.getCode() == KeyCode.ENTER){
        keyEvent.consume();
        change.fire();
      }
    });

  }

  private void closeNamechange(HBox box, Button nameButton) {
    this.getChildren().remove(box);
    isOpen = false;
    nameButton.setText("C");
  }

  private void openNamechange(HBox box, Button nameButton) {
    this.getChildren().addAll(box);
    isOpen = true;
    nameButton.setText("X");
    field.requestFocus();
  }


}
