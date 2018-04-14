package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import tech.subluminal.client.presentation.GamePresenter;
import tech.subluminal.client.presentation.customElements.FleetComponent;
import tech.subluminal.client.presentation.customElements.Jump;
import tech.subluminal.client.presentation.customElements.JumpBox;
import tech.subluminal.client.presentation.customElements.MotherShip;
import tech.subluminal.client.presentation.customElements.StarComponent;
import tech.subluminal.client.stores.records.game.Player;
import tech.subluminal.shared.stores.records.game.Coordinates;
import tech.subluminal.shared.stores.records.game.Ship;
import tech.subluminal.shared.stores.records.game.Star;

public class GameController implements Initializable, GamePresenter {

  private MainController main;

  @FXML
  private Pane playArea;

  @FXML
  private Pane map;

  private StarComponent[] pressStore = new StarComponent[2];

  private Collection<Jump> jump = new LinkedList<>();

  private JumpBox box;

  private List<StarComponent> starList = new LinkedList<StarComponent>();

  private List<MotherShip> shipList = new LinkedList<MotherShip>();


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    /*StarComponent star = new StarComponent(0.4,0.7,0.4, "ANDROMEDAR");
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
    //removeJumpPath();*/

    //FleetComponent fleet = new FleetComponent(new Coordinates(0.2, 0.3), 30, "wefwef", "22r2f2");
    //map.getChildren().add(fleet);


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

  @Override
  public void displayMap(Collection<Star> stars) {
    stars.stream().forEach(s -> starList.add(new StarComponent(s.getOwnerID(), 0.0, s.getCoordinates(), s.getID())));

    starList.stream().forEach(s -> map.getChildren().add(s));
  }

  @Override
  public void updateStar(Collection<Star> stars) {
    stars.stream().forEach(s -> {
      starList.stream().forEach(e -> {
        if(e.getStarID().equals(s.getID())){
          e.setOwnerIDProperty(s.getOwnerID());
          e.setPossession(s.getPossession());
        }
      });
    });

  }

  @Override
  public void updateFleet(List<Player> players) {

  }

  @Override
  public void addFleet(Star star, int amount) {

  }

  @Override
  public void removeFleet(Star star, int amount) {

  }

  @Override
  public void updateMothership(List<Player> players) {
    players.stream().forEach(p -> {
      Ship mothership = p.getMotherShip();
      shipList.stream().forEach(s -> {
        if (p.getMotherShip().getID().equals(s.getId())){
          s.setLayoutX(mothership.getCoordinates().getX());
          s.setLayoutY(mothership.getCoordinates().getY());
        }
      });
    });
  }

  @Override
  public void addMothership(List<Player> players) {
    players.stream().forEach(p -> {
      Ship mothership = p.getMotherShip();

      shipList.add(new MotherShip(mothership.getCoordinates().getX(), mothership.getCoordinates().getY(), p.getID()));

      shipList.stream().forEach(s -> map.getChildren().add(s));
    });

  }

  @Override
  public void removeMothership(List<String> playerID) {
    playerID.stream().forEach(p -> {
      shipList.stream().forEach(s -> {
        if(s.getOwnerID().equals(p)){
          shipList.remove(s);
        }
      });
    });
  }
}
