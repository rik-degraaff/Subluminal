package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.logic.Graph;
import tech.subluminal.client.presentation.GamePresenter;
import tech.subluminal.client.presentation.customElements.FleetComponent;
import tech.subluminal.client.presentation.customElements.Jump;
import tech.subluminal.client.presentation.customElements.JumpBox;
import tech.subluminal.client.presentation.customElements.MotherShipComponent;
import tech.subluminal.client.presentation.customElements.StarComponent;
import tech.subluminal.client.stores.GameStore;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.client.stores.records.game.OwnerPair;
import tech.subluminal.shared.stores.records.game.Fleet;
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

  private String playerID;

  //private List<StarComponent> starList = new LinkedList<StarComponent>();//TODO: remove this

  //private ListProperty<StarComponent> stars = new SimpleListProperty<>();

  private Map<String, StarComponent> stars = new HashMap<>();
  private Map<String, MotherShipComponent> ships = new HashMap<>();
  private Map<String, FleetComponent> fleets = new HashMap<>();

  private List<MotherShipComponent> shipList = new LinkedList<MotherShipComponent>();

  private List<FleetComponent> fleetList = new LinkedList<FleetComponent>();

  private GamePresenter.Delegate gameDelegate;

  private GameStore gameStore;

  private ListView<MotherShipComponent> dummyShipList = new ListView<>();
  private ListView<FleetComponent> dummyFleetList = new ListView<>();

  private Map<String, Star> starMap = new HashMap<>();

  private ListView<StarComponent> dummyStarList = new ListView<>();
  private Graph<String> graph;
  private List<String> path;
  private UserStore userStore;
  private Map<String, Color> playerColors;


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

    map.setOnMouseClicked(mouseEvent -> {
      pressStore[0] = null;
      pressStore[1] = null;
      removeJumpPath();
      if (jump != null) {
        jump.clear();
      }
    });
  }

  private void starClicked(StarComponent star, MouseEvent mouseEvent) {
    if (pressStore[1] == null) {
      if (pressStore[0] == null) {
        pressStore[0] = star;
        pressStore[1] = null;
      } else {
        Logger.debug("creating JumpPath");
        pressStore[1] = star;
        if (path != null) {
          path.clear();
        }
        this.path = graph
            .findShortestPath(pressStore[0].getStarID(), pressStore[1].getStarID());
        if (!path.isEmpty()) {
          removeJumpPath();
          createJumpPath(path);
        } else {
          pressStore[0] = null;
          pressStore[1] = null;
          removeJumpPath();
          if (!jump.isEmpty()) {
            jump.clear();
          }
        }

      }
    } else {
      pressStore[0] = star;
      pressStore[1] = null;
    }
    mouseEvent.consume();
  }

  public void createJumpPath(List<String> path) {

    List<StarComponent> starComponents = path.stream().map(stars::get).collect(Collectors.toList());

    for (int i = 0; i < path.size() - 1; i++) {
      jump.add(
          new Jump(starComponents.get(i).layoutXProperty(), starComponents.get(i).layoutYProperty(),
              starComponents.get(i + 1).layoutXProperty(),
              starComponents.get(i + 1).layoutYProperty()));
    }
    jump.stream().forEach(j -> map.getChildren().add(j));

    createJumpBox(starComponents.get(0));

  }

  public void removeJumpPath() {
    jump.stream().forEach(j -> {
      map.getChildren().remove(j);
    });

    removeJumpBox();
  }

  public void createJumpBox(StarComponent start) {
    box = new JumpBox(start.layoutXProperty(), start.layoutYProperty(),
        amount -> {
          gameDelegate.sendShips(path, amount);
          removeJumpPath();
          if (!jump.isEmpty()) {
            jump.clear();
          }
        },
        () -> {
          gameDelegate.sendMothership(path);
          removeJumpPath();
          if (!jump.isEmpty()) {
            jump.clear();
          }
        });

    map.getChildren().add(box);
  }

  public void removeJumpBox() {
    map.getChildren().remove(box);
  }

  public void setMainController(MainController main) {
    this.main = main;
  }

  public String getPlayerID() {
    return playerID;
  }

  public void setPlayerID(String playerID) {
    this.playerID = playerID;
  }


  @Override
  public void setGameDelegate(Delegate delegate) {
    gameDelegate = delegate;

  }

  @Override
  public void update() {
    Platform.runLater(() -> {
      dummyStarList.refresh();
      dummyStarList.getItems().forEach(starComponent -> Logger.trace("star: " + starComponent));
      dummyShipList.refresh();
      dummyShipList.getItems().forEach(shipComponent -> Logger.trace("ship: " + shipComponent));

      if (graph != null) {
        return;
      }
      this.graph = new Graph<>(stars.keySet(),
          (s1, s2) -> starMap.get(s1).getDistanceFrom(starMap.get(s2)) <= starMap.get(s1).getJump(),
          (s1, s2) -> starMap.get(s1).getDistanceFrom(starMap.get(s2)),
          false);
    });

  }

  @Override
  public void setPlayerColors(Map<String, Color> playerColors) {
    this.playerColors = playerColors;
  }

  public void setGameStore(GameStore gameStore) {
    this.gameStore = gameStore;

    Platform.runLater(() -> {

      //this.playerID = userStore.currentUser().get().use(opt -> opt.get().getID());

      MapperList<StarComponent, Star> starComponents = new MapperList<>(
          gameStore.stars().observableList(),
          star -> {
            if (stars.get(star.getID()) == null) {
              StarComponent starComponent = new StarComponent(star.getOwnerID(),
                  star.getPossession(),
                  star.getCoordinates(),
                  star.getID(),
                  star.getJump());
              stars.put(star.getID(), starComponent);
              stars.put(star.getID(), starComponent);
              if (star.getOwnerID() != null) {
                starComponent.setColor(playerColors.get(star.getOwnerID()));
              }

              starComponent.setOnMouseClicked(e -> starClicked(starComponent, e));

              starMap.put(star.getID(), star);
              map.getChildren().add(starComponent);
              return starComponent;

            }
            StarComponent starComponent = stars.get(star.getID());
            starComponent.setPossession(star.getPossession());
            starComponent.setOwnerID(star.getOwnerID());
            stars.put(star.getID(), starComponent);
            if (star.getOwnerID() != null) {
              starComponent.setColor(playerColors.get(star.getOwnerID()));
            }
            return starComponent;
          });

      this.dummyStarList.setItems(starComponents);

      MapperList<MotherShipComponent, OwnerPair<Ship>> shipComponents = new MapperList<>(

          gameStore.motherShips().observableList(),
          pair -> {
            if (ships.get(pair.getID()) == null) {
              MotherShipComponent shipComponent = new MotherShipComponent(
                  pair.getValue().getCoordinates(),
                  pair.getKey(),
                  pair.getValue().getTargetIDs(),
                  gameStore);
              ships.put(pair.getID(), shipComponent);

              if (pair.getKey() != null) {
                shipComponent.setColor(playerColors.get(pair.getKey()));
              }

              map.getChildren().add(shipComponent);
              return shipComponent;

            }
            MotherShipComponent shipComponent = ships.get(pair.getID());

            if (Math.abs(pair.getValue().getCoordinates().getX() - shipComponent.getX())
                > 0.000001) {
              shipComponent.setX(pair.getValue().getCoordinates().getX());
            }
            if (Math.abs(pair.getValue().getCoordinates().getY() - shipComponent.getY())
                > 0.000001) {
              shipComponent.setY(pair.getValue().getCoordinates().getY());
            }
            Logger.debug("Target ID's: " + pair.getValue().getTargetIDs());
            if (shipComponent.getTargetsWrapper().size() != pair.getValue().getTargetIDs().size()) {
              shipComponent.setTargetsWrapper(pair.getValue().getTargetIDs());
            }

            return shipComponent;
          }
      );

      this.dummyShipList.setItems(shipComponents);

      MapperList<FleetComponent, OwnerPair<Fleet>> fleetComponents = new MapperList<>(

          gameStore.fleets().observableList(),
          pair -> {
            if (fleets.get(pair.getID()) == null) {
              FleetComponent fleetComponent = new FleetComponent(
                  pair.getValue().getCoordinates(),
                  pair.getValue().getNumberOfShips(),
                  pair.getID(),
                  pair.getKey(),
                  pair.getValue().getTargetIDs(),
                  gameStore);
              fleets.put(pair.getID(), fleetComponent);

              if (pair.getKey() != null) {
                fleetComponent.setColor(playerColors.get(pair.getKey()));
              }

              map.getChildren().add(fleetComponent);
              return fleetComponent;

            }
            FleetComponent fleetComponent = fleets.get(pair.getID());

            if (Math.abs(pair.getValue().getCoordinates().getX() - fleetComponent.getX())
                > 0.000001) {
              fleetComponent.setX(pair.getValue().getCoordinates().getX());
            }
            if (Math.abs(pair.getValue().getCoordinates().getY() - fleetComponent.getY())
                > 0.000001) {
              fleetComponent.setY(pair.getValue().getCoordinates().getY());
            }
            fleetComponent.setNumberOfShips(pair.getValue().getNumberOfShips());
            Logger.debug("Fleet Target ID's: " + pair.getValue().getTargetIDs());
            if (fleetComponent.getTargetsWrapper().size() != pair.getValue().getTargetIDs()
                .size()) {
              fleetComponent.setTargetsWrapper(pair.getValue().getTargetIDs());
            }

            return fleetComponent;
          }
      );

      this.dummyFleetList.setItems(fleetComponents);
    });

  }

  public void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }

  @Override
  public void setUserID() {
    Platform.runLater(() -> {
      playerID = userStore.currentUser().get().use(opt -> opt.get().getID());
    });
  }
}
