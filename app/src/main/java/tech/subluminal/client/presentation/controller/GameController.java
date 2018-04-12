package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import tech.subluminal.client.presentation.customElements.StarComponent;

public class GameController implements Initializable {

  private MainController main;

  @FXML
  private Group map;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    map.getChildren().add(new StarComponent(50,50,20));
    Node star = new StarComponent(0,80,10);

    map.getChildren().add(star);
  }

  public void setMainController(MainController main){
    this.main = main;
  }
}
