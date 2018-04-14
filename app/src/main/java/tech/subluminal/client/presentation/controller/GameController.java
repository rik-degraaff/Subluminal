package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import tech.subluminal.client.presentation.customElements.Jump;
import tech.subluminal.client.presentation.customElements.JumpBox;
import tech.subluminal.client.presentation.customElements.MotherShip;
import tech.subluminal.client.presentation.customElements.StarComponent;

public class GameController implements Initializable {

  private MainController main;

  @FXML
  private Pane playArea;

  @FXML
  private Pane map;

  private StarComponent[] pressStore = new StarComponent[2];

  private Collection<Jump> jump = new LinkedList<>();

  private JumpBox box;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    StarComponent star = new StarComponent(0.4,0.7,0.4, "ANDROMEDAR");
    StarComponent star2 = new StarComponent(1,0.0,0.2, "TITTY IX");
    star2.setColorProperty(Color.PINK);
    StarComponent star3 = new StarComponent(0.1,1,0.1, "LUXIS BB");

    map.getChildren().addAll(star,star2, star3);

    List<StarComponent> test = new LinkedList();
    test.add(star);
    test.add(star2);
    test.add(star3);
    createJumpPath(test);
    removeJumpPath();

    MotherShip ship = new MotherShip(star.layoutXProperty(),star.layoutYProperty(), "fwefr1");
    ship.setIsRotating(true);
    map.getChildren().add(ship);
    //removeJumpPath();


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

  public void createJumpPath(List<StarComponent> path){
    for(int i = 0; i < path.size() - 1; i++){
      jump.add(new Jump(path.get(i).layoutXProperty(), path.get(i).layoutYProperty(),path.get(i+1).layoutXProperty(), path.get(i+1).layoutYProperty()));
    }
    jump.stream().forEach(j -> map.getChildren().add(j));

    createJumpBox(path.get(0));

  }

  public void removeJumpPath(){
    jump.stream().forEach(j -> map.getChildren().remove(j));

    removeJumpBox();
  }

  public void createJumpBox(StarComponent start){
    box = new JumpBox(start.layoutXProperty(), start.layoutYProperty(), start.shipsProperty());

    box.shipToSendProperty().addListener((observable, oldValue, newValue) -> {
      System.out.println("Ships send" + newValue);
    });

    map.getChildren().add(box);
  }

  public void removeJumpBox(){
    map.getChildren().remove(box);
  }

  public void setMainController(MainController main){
    this.main = main;
  }
}
