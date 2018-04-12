package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import tech.subluminal.client.presentation.customElements.StarComponent;

public class GameController implements Initializable {

  private MainController main;

  @FXML
  private Pane playArea;

  @FXML
  private Pane map;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    map.getChildren().add(new StarComponent(0.4,0.7,60, "ANDROMEDAR"));
    StarComponent star = new StarComponent(0.2,0.5,50, "TITTY IX");
    star.setSizeProperty(50);
    star.setColorProperty(Color.PINK);
    map.getChildren().add(star);
  }

  public void setMainController(MainController main){
    this.main = main;
  }
}
