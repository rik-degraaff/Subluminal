package tech.subluminal.client.presentation.controller;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MenuController implements Initializable, Observer {

  @FXML
  private ImageView logoDock;

  @FXML
  private AnchorPane menuContainer;

  @FXML
  private VBox menuDock;

  @FXML
  private HBox menuButtonsDock;

  private MainController main;

  private void generateLogo() {
    Platform.runLater(() -> {
      PauseTransition pauseTl = new PauseTransition(Duration.seconds(1));

      ScaleTransition scaleTl = new ScaleTransition(Duration.seconds(3), menuContainer);
      scaleTl.setFromX(0);
      scaleTl.setFromY(0);
      scaleTl.setToX(1);
      scaleTl.setByY(1);

      SequentialTransition seqTl = new SequentialTransition();

      FadeTransition fadeTl = new FadeTransition(Duration.seconds(0.7), menuButtonsDock);
      //fadeTl.setFromValue(0);
      fadeTl.setToValue(1);
      //fadeTl.setCycleCount(3);
      seqTl.getChildren().addAll(pauseTl, scaleTl);

      seqTl.play();
      seqTl.setOnFinished(e -> onFinishedLogoHandler());
    });

  }

  private void onFinishedLogoHandler() {
    pulsateLogo();
    showMenuButtons();
  }

  private void pulsateLogo() {
    ScaleTransition scaleMainTl = new ScaleTransition(Duration.seconds(4), menuContainer);
    scaleMainTl.setFromX(1);
    scaleMainTl.setFromY(1);
    scaleMainTl.setToX(1.1);
    scaleMainTl.setToY(1.1);

    scaleMainTl.setCycleCount(ScaleTransition.INDEFINITE);
    scaleMainTl.setAutoReverse(true);
    scaleMainTl.play();
  }

  private void showMenuButtons() {
    FadeTransition fadeTl = new FadeTransition(Duration.seconds(0.2), menuButtonsDock);
    fadeTl.setFromValue(0);
    fadeTl.setToValue(1);
    fadeTl.setCycleCount(3);
    fadeTl.play();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Image logo = new Image("/tech/subluminal/resources/subluminal_logo.png");
    logoDock.setImage(logo);
    menuButtonsDock.setOpacity(0.0);
    generateLogo();

    //TODO: Iterate over all the files and play random track
    Media media = null;
    //try {
    //TODO: Import track with getResources
    media = new Media(getClass().getResource("/tech/subluminal/resources/music/theyre-here_looping.mp3").toString());
    //} catch (URISyntaxException e) {
    //  e.printStackTrace();
    //}

    MediaPlayer player = new MediaPlayer(media);
    player.setVolume(0.75);
    player.play();

    player.setOnEndOfMedia(new Runnable() {
                             public void run() {
                               player.seek(Duration.ZERO);
                             }
                           }
    );
  }

    @FXML
    private void joinPressed (ActionEvent e){
      main.onJoinHandle();

    }

    @FXML
    private void hostPressed (ActionEvent e){
      main.onHostOpenHandle();
    }

    @FXML
    private void settingPressed (ActionEvent e){
      main.onSettingOpenHandle();
    }

    @Override
    public void setMainController (MainController main){
      this.main = main;
    }
  }
