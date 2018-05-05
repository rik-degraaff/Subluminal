package tech.subluminal.client.presentation.customElements;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import tech.subluminal.client.presentation.controller.MainController;

public class EndGameComponent extends HBox {

  private MainController main;

  public EndGameComponent(MainController main, String winner){
    this.main = main;

    this.setAlignment(Pos.CENTER);
    Pane pane = new Pane();
    Label endText = new Label("End of Game" + "\n" + "Winner is: " + winner);
    pane.getChildren().add(endText);


    this.getChildren().add(pane);
  }

}
