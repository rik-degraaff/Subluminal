package tech.subluminal.client.presentation.customElements;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import tech.subluminal.client.presentation.controller.MainController;

public class EndGameComponent extends HBox {

  private MainController main;
  private VBox box;

  public EndGameComponent(MainController main, String winnerName) {
    this.main = main;

    this.setAlignment(Pos.CENTER);
    box = new VBox();
    Label endText = new Label("End of Game" + "\n" + "Winner is: " + winnerName);
    box.getChildren().add(endText);
    box.getStyleClass().add("console");

    this.getChildren().add(box);

    this.prefWidthProperty().bind(getScene().widthProperty());
    this.prefHeightProperty().bind(getScene().heightProperty());

    addButtons();
  }

  public EndGameComponent(MainController main) {
    this.main = main;

    this.setAlignment(Pos.CENTER);
    box = new VBox();
    StringProperty dots = new SimpleStringProperty();
    dots.set("");
    String failed = "You all failed Bob";
    Label endText = new Label();
    endText.textProperty().bind(Bindings.createStringBinding(() -> failed + dots, dots));
    box.getChildren().add(endText);

    Timeline timeTl = new Timeline();
    timeTl.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
      dots.set(dots.get() + ".");
    }));
    timeTl.getKeyFrames().add(new KeyFrame(Duration.seconds(2), event -> {
      dots.set(dots.get() + ".");
    }));
    timeTl.getKeyFrames().add(new KeyFrame(Duration.seconds(3), event -> {
      dots.set(dots.get() + ".");
    }));
    timeTl.setAutoReverse(true);
    timeTl.play();

    this.getChildren().add(box);
    addButtons();
  }

  public void addButtons() {
    Label backToLobby = new Label("Back to Lobby");
    this.getChildren().add(backToLobby);
    backToLobby.setOnMouseClicked(event -> {
      main.onMapCloseHandle();
    });
  }

}
