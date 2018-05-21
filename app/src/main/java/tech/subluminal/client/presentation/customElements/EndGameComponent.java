package tech.subluminal.client.presentation.customElements;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import tech.subluminal.client.presentation.controller.MainController;

public class EndGameComponent extends HBox {

  private MainController main;
  private VBox box;

  public EndGameComponent(MainController main, String winnerName) {
    this.main = main;

    this.setAlignment(Pos.CENTER);
    box = new VBox();
    box.setAlignment(Pos.CENTER);
    Label endText = new Label("The game ended and the winner is: ");
    endText.setTextAlignment(TextAlignment.CENTER);
    Label winnerLabel = new Label(winnerName);
    endText.setTextAlignment(TextAlignment.CENTER);
    winnerLabel.setFont(new Font("PxPlus IBM VGA9", 30));
    winnerLabel.setTextFill(Color.GREEN);
    endText.getStyleClass().addAll("console-red", "font-dos");
    VBox hbox = new VBox();
    hbox.setPrefHeight(300);
    hbox.setPrefWidth(400);

    hbox.getChildren().addAll(endText, winnerLabel);
    hbox.setAlignment(Pos.CENTER);
    hbox.getStyleClass().add("console");
    hbox.setBackground(
        new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));

    box.getChildren().add(hbox);

    this.getChildren().add(box);


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
    endText.textProperty().bind(Bindings.createStringBinding(() -> failed + dots.get(), dots));
    endText.getStyleClass().add("console-red");
    box.getChildren().add(endText);

    Pane hbox = new Pane(box);
    box.setPrefHeight(300);
    box.setPrefWidth(400);

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

    this.getChildren().add(hbox);

    Platform.runLater(() -> {
      this.prefWidthProperty().bind(getScene().widthProperty());
      this.prefHeightProperty().bind(getScene().heightProperty());
    });

    addButtons();
  }

  public void addButtons() {
    Label backToLobby = new Label("Back to Lobby");
    backToLobby.getStyleClass().add("button");
    box.getChildren().add(backToLobby);
    backToLobby.setOnMouseClicked(event -> {
      main.getGameController().leaveGame();
    });
  }

}
