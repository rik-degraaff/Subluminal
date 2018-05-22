package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.presentation.KeyMap;
import tech.subluminal.shared.records.GlobalSettings;
import tech.subluminal.shared.util.ConfigModifier;

/**
 * A controller for teh settings view.
 */
public class SettingsController implements Observer, Initializable {

  private Random rand = new Random(GlobalSettings.SHARED_RANDOM.nextLong());
  public static final String VOLUME_KEY = "Volume";
  public static final String MUTE_SOUND_KEY = "MuteSound";
  private static final boolean MUTE_SOUND_DEFAULT = false;
  private final double VOLUME_DEFAULT = 0.5;
  ObservableMap<String, String> settingsMap;
  private MainController main;
  @FXML
  private CheckBox muteMasterVolume;
  @FXML
  private Slider masterVolume;
  @FXML
  private ScrollPane keyDock;
  private KeyMap keyMap;

  /**
   * Initializes the settings controller.
   *
   * @param location the location where the settings are saved.
   * @param resources the bundle which contains the settings resources.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ConfigModifier<String, String> cm = new ConfigModifier<>("settings");
    cm.attachToFile("settings.properties");
    settingsMap = cm.getProps();

    initSound();

    VBox vBox = new VBox();
    vBox.setSpacing(20);
    vBox.setAlignment(Pos.CENTER);

    vBox.prefHeightProperty().bind(keyDock.heightProperty());
    vBox.prefWidthProperty().bind(keyDock.widthProperty());


    //vBox.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

    keyDock.setContent(vBox);
    keyDock.setFitToHeight(true);
    keyDock.setFitToWidth(true);

    vBox.setPadding(new Insets(20));

    Platform.runLater(() -> {
      keyMap.getKeyMap().forEach((k, v) -> {
        Label keyName = new Label(k);
        keyName.getStyleClass().add("font-white");

        Label keyKey = new Label();
        keyKey.getStyleClass().add("font-white");

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
        hBox.getChildren().addAll(keyName, spacer, keyKey, change, reset);
        hBox.setAlignment(Pos.CENTER);
        //hBox.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        change.setOnMouseClicked(e -> {
          keyKey.setTextFill(Color.RED);
          EventHandler handler = new EventHandler<KeyEvent>() {
            public void handle(KeyEvent keyEvent) {
              keyEvent.consume();
              v.setValue(keyEvent.getCode());
              main.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, this);
              keyKey.setTextFill(Color.WHITE);
            }
          };
          main.getScene().addEventHandler(KeyEvent.KEY_PRESSED, handler);
        });

        reset.setOnMouseClicked(e -> {
          keyMap.reset(k);
        });

        vBox.getChildren().add(hBox);
      });

      Label resetAll = new Label("Reset All");
      resetAll.getStyleClass().add("font-white");
      resetAll.setOnMouseClicked(e -> keyMap.resetAll());

      vBox.getChildren().add(resetAll);
    });
  }

  private void initSound() {
    if (settingsMap.get(VOLUME_KEY) == null) {
      settingsMap.put(VOLUME_KEY, Double.toString(VOLUME_DEFAULT));
      Logger.debug("default sound");
    }
    settingsMap.computeIfAbsent(MUTE_SOUND_KEY, k -> Boolean.toString(MUTE_SOUND_DEFAULT));

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

    int random = (int) Math.floor(rand.nextDouble() * 3);

    MediaPlayer player = new MediaPlayer(media[random]);
    player.setVolume(0.1);
    player.play();

    player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));

    player.volumeProperty().bind(masterVolume.valueProperty());

    masterVolume.setValue(Double.parseDouble(settingsMap.get(VOLUME_KEY)));

    masterVolume.valueProperty().addListener((observable, oldValue, newValue) -> {
      settingsMap.put(VOLUME_KEY, newValue.toString());
    });

    muteMasterVolume.selectedProperty().addListener((observable, oldValue, newValue) -> {
      settingsMap.put(MUTE_SOUND_KEY, newValue.toString());
    });

    muteMasterVolume.selectedProperty().addListener(((observable, oldValue, newValue) -> {
      if (!oldValue && newValue) {
        player.pause();
      } else if (oldValue && !newValue) {
        player.play();
      }
    }));

    muteMasterVolume.setSelected(Boolean.parseBoolean(settingsMap.get(MUTE_SOUND_KEY)));

  }

  @Override
  public void setMainController(MainController main) {
    this.main = main;
  }

  public void setKeyMap(KeyMap keyMap) {
    this.keyMap = keyMap;
  }
}
