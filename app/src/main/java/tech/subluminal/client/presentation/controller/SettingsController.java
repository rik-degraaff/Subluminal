package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import tech.subluminal.client.presentation.KeyMap;

public class SettingsController implements Observer, Initializable {

  private MainController main;

  @FXML
  private CheckBox muteMasterVolume;

  @FXML
  private Slider masterVolume;

  @FXML
  private AnchorPane keyDock;

  private KeyMap keyMap;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initSound();

    VBox vBox = new VBox();
    vBox.setSpacing(20);
    vBox.setAlignment(Pos.CENTER);

    vBox.prefHeightProperty().bind(keyDock.heightProperty());
    vBox.prefWidthProperty().bind(keyDock.widthProperty());



    //vBox.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

    keyDock.getChildren().addAll(vBox);

    vBox.setPadding(new Insets(20));

    Platform.runLater(() -> {
      keyMap.getKeyMap().forEach((k,v) -> {
        Label keyName = new Label(k);
        Label keyKey = new Label();
        keyKey.textProperty().bind(v.asString());
        Label change = new Label("Change");
        change.getStyleClass().addAll("button", "font-dos");

        Label reset = new Label("Reset");
        reset.getStyleClass().addAll("button", "font-dos");


        HBox hBox = new HBox();
        hBox.setFillHeight(false);
        hBox.setSpacing(20);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hBox.getChildren().addAll(keyName,keyKey,spacer, change, reset);
        hBox.setAlignment(Pos.CENTER);
        //hBox.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));



        change.setOnMouseClicked(e -> {
          EventHandler handler = new EventHandler<KeyEvent>(){
            public void handle(KeyEvent keyEvent){
              keyEvent.consume();
              v.setValue(keyEvent.getCode());
              main.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, this);

            }
          };

          System.out.println(v.getValue());
          main.getScene().addEventHandler(KeyEvent.KEY_PRESSED, handler);
        });

        reset.setOnMouseClicked(e -> {
          keyMap.reset(k);
        });

        vBox.getChildren().add(hBox);
      });

      Label resetAll = new Label("Reset All");
      resetAll.setOnMouseClicked(e -> keyMap.resetAll());

      vBox.getChildren().add(resetAll);
    });


  }

  private void initSound() {
    Media media[] = new Media[3];

    media[0] = new Media(
        getClass().getResource("/tech/subluminal/resources/music/theyre-here_looping.wav")
            .toString());
    media[1] = new Media(
        getClass().getResource("/tech/subluminal/resources/music/dark-techno-city_looping.wav")
            .toString());
    media[2] = new Media(
        getClass().getResource("/tech/subluminal/resources/music/urban-Jjngle-2061_looping.wav")
            .toString());

    int random = (int) Math.floor(Math.random() * 3);

    MediaPlayer player = new MediaPlayer(media[random]);
    player.setVolume(0.1);
    player.play();

    player.setOnEndOfMedia(() -> player.seek(Duration.ZERO)
    );

    player.volumeProperty().bind(masterVolume.valueProperty());

    muteMasterVolume.selectedProperty().addListener(((observable, oldValue, newValue) -> {
      if (!oldValue && newValue) {
        player.pause();
      } else if (oldValue && !newValue) {
        player.play();
      }
    }));
  }

  @Override
  public void setMainController(MainController main) {
    this.main = main;
  }

  public void setKeyMap(KeyMap keyMap) {
    this.keyMap = keyMap;
  }
}
