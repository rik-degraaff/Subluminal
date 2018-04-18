package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class SettingsController implements Observer, Initializable {

  private MainController main;

  @FXML
  private CheckBox muteMasterVolume;

  @FXML
  private Slider masterVolume;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initSound();

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

    int random = (int) Math.floor(Math.random()*3);

    MediaPlayer player = new MediaPlayer(media[random]);
    player.setVolume(0.75);
    player.play();

    player.setOnEndOfMedia(() -> player.seek(Duration.ZERO)
    );

    player.volumeProperty().bind(masterVolume.valueProperty());

    muteMasterVolume.selectedProperty().addListener(((observable, oldValue, newValue) -> {
      if(!oldValue && newValue){
        player.pause();
      }else if(oldValue && !newValue){
        player.play();
      }
    }));
  }

  @Override
  public void setMainController(MainController main) {
    this.main = main;
  }
}
