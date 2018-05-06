package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.logic.Graph;
import tech.subluminal.client.presentation.GamePresenter;
import tech.subluminal.client.presentation.customElements.ArrowComponent;
import tech.subluminal.client.presentation.customElements.EndGameComponent;
import tech.subluminal.client.presentation.customElements.FleetComponent;
import tech.subluminal.client.presentation.customElements.Jump;
import tech.subluminal.client.presentation.customElements.JumpBox;
import tech.subluminal.client.presentation.customElements.MotherShipComponent;
import tech.subluminal.client.presentation.customElements.StarComponent;
import tech.subluminal.client.stores.GameStore;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.client.stores.records.game.OwnerPair;
import tech.subluminal.shared.records.Channel;
import tech.subluminal.shared.stores.records.User;
import tech.subluminal.shared.stores.records.game.Coordinates;
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

  private Map<String, StarComponent> stars = new HashMap<>();
  private Map<String, MotherShipComponent> ships = new HashMap<>();
  private Map<String, FleetComponent> fleets = new HashMap<>();

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
  private String gameID;


  @Override
  public void initialize(URL location, ResourceBundle resources) {

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
        removeJumpPath();
        pressStore[0] = star;
        pressStore[1] = null;
      } else if (pressStore[0] == star) {

      } else {
        Logger.debug("creating JumpPath");
        removeJumpPath();
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
      removeJumpPath();
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
    jump.clear();

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
  public void setUserID() {
    Platform.runLater(() -> {
      playerID = userStore.currentUser().get().use(opt -> opt.get().getID());
    });
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

  @Override
  public void removeFleets(List<String> fleetIDs) {
    fleetIDs.forEach(f -> {
      FleetComponent remFleet = fleets.get(f);
      if (remFleet != null) {
        fleets.remove(f);
        Platform.runLater(() -> {
          map.getChildren().remove(remFleet);
        });
      }
    });
  }

  @Override
  public void onEndGame(String gameID, String winnerID) {
    if (gameID.equals(this.gameID)) {
      if (winnerID != null) {
        String winnerName = userStore.users().getByID(winnerID).get().use(User::getUsername);
        map.getChildren().add(new EndGameComponent(main, winnerName));
      } else {
        map.getChildren().add(new EndGameComponent(main));
      }
    } else {
      if (winnerID != null) {
        String winnerName = userStore.users().getByID(winnerID).get().use(User::getUsername);
        main.getChatController()
            .addMessageChat(winnerName + " won one of the last games you where in.", Channel.INFO);
      } else {
        main.getChatController()
            .addMessageChat("You all failed Bob...", Channel.INFO);
      }
    }

  }

  @Override
  public void removeMotherShips(List<String> shipIDs) {
    shipIDs.forEach(f -> {
      MotherShipComponent remShip = ships.get(f);
      if (remShip != null) {
        ships.remove(f);
        Platform.runLater(() -> {
          map.getChildren().remove(remShip);
        });
      }
    });
  }

  @Override
  public void setGameID(String gameID) {
    this.gameID = gameID;
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
                  star.getName(),
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

              if (pair.getKey().equals(playerID)) {
                Coordinates coordinates = pair.getValue().getCoordinates();
                ArrowComponent arrow = new ArrowComponent(shipComponent.layoutYProperty());
                arrow.layoutXProperty().bind(shipComponent.layoutXProperty());
                arrow.layoutYProperty().bind(shipComponent.layoutYProperty());
                arrow.setFill(playerColors.get(pair.getKey()));

                Platform.runLater(() -> {
                  Timeline timeTl = new Timeline();
                  timeTl.getKeyFrames()
                      .add(
                          new KeyFrame(Duration.seconds(0), event -> map.getChildren().add(arrow)));
                  timeTl.getKeyFrames().add(
                      new KeyFrame(Duration.seconds(03), event -> map.getChildren().remove(arrow)));
                  timeTl.play();
                });

              }
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
            //Logger.debug("Target ID's: " + pair.getValue().getTargetIDs());
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
            //Logger.debug("Fleet Target ID's: " + pair.getValue().getTargetIDs());
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

  public void clearMap() {
    map.getChildren().clear();
  }

  public void leaveGame() {
    gameDelegate.leaveGame();
    main.onMapCloseHandle();
    gameID = null;
  }
}
