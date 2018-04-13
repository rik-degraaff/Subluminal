package tech.subluminal.client.presentation.controller;

import java.lang.reflect.Array;
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
import tech.subluminal.client.presentation.customElements.JumpPath;
import tech.subluminal.client.presentation.customElements.StarComponent;

public class GameController implements Initializable {

  private MainController main;

  @FXML
  private Pane playArea;

  @FXML
  private Pane map;

  private StarComponent[] pressStore = new StarComponent[2];

  private JumpPath jump;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    map.getChildren().add(new StarComponent(0.4,0.7,0.4, "ANDROMEDAR"));
    StarComponent star = new StarComponent(0.2,0.5,0.2, "TITTY IX");
    star.setColorProperty(Color.PINK);
    map.getChildren().add(star);

    map.getChildren().forEach(e -> {
      if(e instanceof StarComponent){
        e.setOnMouseClicked(mouseEvent -> {
          if(pressStore[1] == null){
            if(pressStore[0] == null){
              pressStore[0] = (StarComponent) e;
              pressStore[1] = null;
            }else if(pressStore[0] == e){

            }else{
              pressStore[1] = (StarComponent) e;
              jump = new JumpPath(pressStore[0].layoutXProperty(), pressStore[0].layoutYProperty(), pressStore[1].layoutXProperty(), pressStore[1].layoutYProperty());
              map.getChildren().add(jump);
            }
          }else{
            pressStore[0] = (StarComponent) e;
            pressStore[1] = null;
          }
          System.out.println(pressStore[0].getName());
          mouseEvent.consume();
        });
      }
    });

    map.setOnMouseClicked(mouseEvent -> {
      pressStore[0] = null;
      pressStore[1] = null;
      if(map.getChildren().contains(jump)){
        map.getChildren().remove(jump);
      }
      System.out.println("cleared");
    });
  }

  public void setMainController(MainController main){
    this.main = main;
  }
}
