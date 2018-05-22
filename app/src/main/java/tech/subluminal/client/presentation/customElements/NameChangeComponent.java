package tech.subluminal.client.presentation.customElements;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import tech.subluminal.client.presentation.controller.MainController;

public class NameChangeComponent extends AnchorPane {

  public TextField field = new TextField();
  Button change;

  public NameChangeComponent(MainController main){
    HBox box = new HBox();

    field.requestFocus();
    change = new Button("Change Name");
    change.setOnAction(e -> {
      if(!field.getText().equals("")){
        main.getChatController().changeName(field.getText());
      }else{
        main.getChatController().printPatrick();
        main.getChatController().changeName("ThisIsPatrick!");
      }

      field.setText("");
    });

    box.getChildren().addAll(field,change);


    this.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
      if(keyEvent.getCode() == KeyCode.ENTER){
        keyEvent.consume();
        change.fire();
      }
    });
    this.getChildren().addAll(box);
    this.getStyleClass().add("font-dos");

    this.setHeight(Double.MAX_VALUE);
    this.setWidth(Double.MAX_VALUE);

  }


}
