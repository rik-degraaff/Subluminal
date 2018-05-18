package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    vBox.setAlignment(Pos.CENTER);
    keyDock.getChildren().addAll(vBox);


    Platform.runLater(() -> {
      keyMap.getKeyMap().forEach((k,v) -> {
        Label keyName = new Label(k);
        Label keyKey = new Label(v.getValue());

        HBox hBox = new HBox();
        hBox.setFillHeight(false);
        hBox.setSpacing(20);
        hBox.getChildren().addAll(keyName,keyKey);


        keyKey.setOnMouseClicked(e -> {
          System.out.println(v.getValue());
          hBox.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            v.set(keyEvent.getCode().getName());
            System.out.println(v.getValue());
          });
        });

        vBox.getChildren().add(hBox);
      });
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
