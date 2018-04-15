package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.presentation.GamePresenter;
import tech.subluminal.client.presentation.customElements.FleetComponent;
import tech.subluminal.client.presentation.customElements.Jump;
import tech.subluminal.client.presentation.customElements.JumpBox;
import tech.subluminal.client.presentation.customElements.MotherShipComponent;
import tech.subluminal.client.presentation.customElements.StarComponent;
import tech.subluminal.client.stores.GameStore;
import tech.subluminal.client.stores.records.game.OwnerPair;
import tech.subluminal.client.stores.records.game.Player;
import tech.subluminal.shared.stores.records.game.Ship;
import tech.subluminal.shared.stores.records.game.Star;
import tech.subluminal.shared.util.MapperList;

public class GameController implements Initializable, GamePresenter {

  private MainController main;

  @FXML
  private Pane playArea;

  @FXML
  private Pane map;

  private StarComponent[] pressStore = new StarComponent[2];

  private Collection<Jump> jump = new LinkedList<>();

  private JumpBox box;

  private List<StarComponent> starList = new LinkedList<StarComponent>();//TODO: remove this

  //private ListProperty<StarComponent> stars = new SimpleListProperty<>();

  private Map<String, StarComponent> stars = new HashMap<>();
  private Map<String, MotherShipComponent> ships = new HashMap<>();

  private List<MotherShipComponent> shipList = new LinkedList<MotherShipComponent>();

  private List<FleetComponent> fleetList = new LinkedList<FleetComponent>();

  private GamePresenter.Delegate gameDelegate;

  private GameStore gameStore;

  private ListView<MotherShipComponent> dummyShipList = new ListView<>();

  private ListView<StarComponent> dummyStarList = new ListView<>();


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    /*StarComponent star = new StarComponent("fewff",0.8,new Coordinates(0.2,0.6), "ANDROMEDAR");
    StarComponent star1 = new StarComponent("fewff",0.2,new Coordinates(0.6,0.2), "ANDROMEDAR");
    StarComponent star3 = new StarComponent("fewff",0.5,new Coordinates(0.8,0.5), "ANDROMEDAR");*/
    /*StarComponent star2 = new StarComponent(1,0.0,0.2, "TITTY IX");
    star2.setColorProperty(Color.PINK);
    StarComponent star3 = new StarComponent(0.1,1,0.1, "LUXIS BB");*/

    /*map.getChildren().addAll(star, star1, star3);

    List<StarComponent> test = new LinkedList();
    test.add(star);
    /*test.add(star2);
    test.add(star3);*/
    //createJumpPath(test);
    //removeJumpPath();

    /*MotherShipComponent ship = new MotherShipComponent(star.getLayoutX(),star.getLayoutY()), "fwefr1");
    ship.setIsRotating(true);
    map.getChildren().add(ship);
    //removeJumpPath();

    FleetComponent fleet = new FleetComponent(new Coordinates(0.2, 0.3), 30, "wefwef", "22r2f2");
    map.getChildren().add(fleet);*/

    map.getChildren().forEach(e -> {
      if (e instanceof StarComponent) {
        e.setOnMouseClicked(mouseEvent -> {
          if (pressStore[1] == null) {
            if (pressStore[0] == null) {
              pressStore[0] = (StarComponent) e;
              pressStore[1] = null;
            } else if (pressStore[0] == e) {

            } else {
              pressStore[1] = (StarComponent) e;
            }
          } else {
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
      if (map.getChildren().contains(jump)) {
        map.getChildren().remove(jump);
      }
      System.out.println("cleared");
    });
  }

  public void createJumpPath(List<StarComponent> path) {
    for (int i = 0; i < path.size() - 1; i++) {
      jump.add(new Jump(path.get(i).layoutXProperty(), path.get(i).layoutYProperty(),
          path.get(i + 1).layoutXProperty(), path.get(i + 1).layoutYProperty()));
    }
    jump.stream().forEach(j -> map.getChildren().add(j));

    createJumpBox(path.get(0));

  }

  public void removeJumpPath() {
    jump.stream().forEach(j -> map.getChildren().remove(j));

    removeJumpBox();
  }

  public void createJumpBox(StarComponent start) {
    box = new JumpBox(start.layoutXProperty(), start.layoutYProperty());

    box.shipToSendProperty().addListener((observable, oldValue, newValue) -> {
      System.out.println("Ships send" + newValue);
    });

    map.getChildren().add(box);
  }

  public void removeJumpBox() {
    map.getChildren().remove(box);
  }

  public void setMainController(MainController main) {
    this.main = main;
  }

  @Override
  public void displayMap(Collection<Star> stars) {
    stars.stream().forEach(
        s -> starList.add(new StarComponent(s.getOwnerID(), 0.0, s.getCoordinates(), s.getID())));

    starList.stream().forEach(s -> map.getChildren().add(s));
  }

  @Override
  public void updateStar(Collection<Star> stars) {
    stars.stream().forEach(s -> {
      starList.stream().forEach(e -> {
        if (e.getStarID().equals(s.getID())) {
          e.setOwnerID(s.getOwnerID());
          e.setPossession(s.getPossession());
        }
      });
    });

  }

  @Override
  public void updateFleet(List<Player> players) {
    players.stream().forEach(p -> {
      p.getFleets().stream().forEach(f -> {
        fleetList.stream().forEach(fc -> {
          // TODO: comment what is done here because of complex structure
          if (f.getID().equals(fc.getFleetID())) {
            fc.setNumberOfShips(f.getNumberOfShips());
            fc.setLayoutX(f.getCoordinates().getX());
            fc.setLayoutY(f.getCoordinates().getY());
            fc.setTargetIDs(f.getTargetIDs());
          } else {
            fleetList.add(
                new FleetComponent(f.getCoordinates(), f.getNumberOfShips(), f.getID(), p.getID(),
                    f.getTargetIDs()));
          }
        });
      });
    });
  }

  @Override
  public void addFleet(List<Player> players) {
    players.stream().forEach(p -> {
      p.getFleets().stream().forEach(f -> {
        fleetList.add(
            new FleetComponent(f.getCoordinates(), f.getNumberOfShips(), f.getID(), p.getID(),
                f.getTargetIDs()));
      });
    });
    fleetList.stream().forEach(f -> map.getChildren().add(f));
  }

  @Override
  public void removeFleet(Map<String, List<String>> removedFleets) {
    removedFleets.forEach((k, v) -> {
      v.stream().forEach(f -> {
        fleetList.stream().forEach(fc -> {
          // TODO: explain what is done here because of complex code
          if (f.equals(fc.getFleetID())) {
            fleetList.remove(fc);
          }
        });
      });
    });
  }

  @Override
  public void updateMothership(List<Player> players) {
    players.stream().forEach(p -> {
      Ship mothership = p.getMotherShip();
      shipList.stream().forEach(s -> {
        if (p.getMotherShip().getID().equals(s.getId())) {
          s.setLayoutX(mothership.getCoordinates().getX());
          s.setLayoutY(mothership.getCoordinates().getY());
          s.setTargetIDs(mothership.getTargetIDs());
        }
      });
    });
  }

  @Override
  public void addMothership(List<Player> players) {
    players.stream().forEach(p -> {
      Ship mothership = p.getMotherShip();

      shipList.add(new MotherShipComponent(mothership.getCoordinates().getX(),
          mothership.getCoordinates().getY(), p.getID(), mothership.getTargetIDs()));

      shipList.stream().forEach(s -> map.getChildren().add(s));
    });

  }

  @Override
  public void removeMothership(List<String> playerID) {
    playerID.stream().forEach(p -> {
      shipList.stream().forEach(s -> {
        if (s.getOwnerID().equals(p)) {
          shipList.remove(s);
        }
      });
    });
  }


  @Override
  public void setGameDelegate(Delegate delegate) {
    gameDelegate = delegate;

  }

  @Override
  public void update() {
    Logger.debug("REFRESH");
    Platform.runLater(() -> {
      dummyStarList.refresh();
      dummyStarList.getItems().forEach(starComponent -> Logger.debug("star: " + starComponent));
      dummyShipList.refresh();
      dummyShipList.getItems().forEach(shipComponent -> Logger.debug("ship: " + shipComponent));
    });

  }

  public void setGameStore(GameStore gameStore) {
    this.gameStore = gameStore;

    Logger.debug("before Mapperlist.");

    Platform.runLater(() -> {
      MapperList<StarComponent, Star> starComponents = new MapperList<>(
          gameStore.stars().observableList(),
          star -> {
            if (stars.get(star.getID()) == null) {
              StarComponent starComponent = new StarComponent(star.getOwnerID(),
                  star.getPossession(),
                  star.getCoordinates(),
                  star.getID());
              stars.put(star.getID(), starComponent);
              map.getChildren().add(starComponent);
              return starComponent;

            }
            StarComponent starComponent = stars.get(star.getID());
            starComponent.setPossession(star.getPossession());
            starComponent.setOwnerID(star.getOwnerID());
            return starComponent;
          });

      this.dummyStarList.setItems(starComponents);

      MapperList<MotherShipComponent, OwnerPair<Ship>> shipComponents = new MapperList<>(
          gameStore.motherShips().observableList(),
          pair -> {
            if (ships.get(pair.getID()) == null) {
              MotherShipComponent shipComponent = new MotherShipComponent(
                  pair.getValue().getCoordinates().getX(), pair.getValue().getCoordinates().getY(),
                  pair.getKey(), pair.getValue().getTargetIDs());
              ships.put(pair.getKey(), shipComponent);
              map.getChildren().add(shipComponent);
              return shipComponent;

            }
            MotherShipComponent shipComponent = ships.get(pair.getKey());
            shipComponent.setX(pair.getValue().getCoordinates().getX());
            shipComponent.setY(pair.getValue().getCoordinates().getY());
            return shipComponent;
          }
      );

      this.dummyShipList.setItems(shipComponents);
    });

  }
}
