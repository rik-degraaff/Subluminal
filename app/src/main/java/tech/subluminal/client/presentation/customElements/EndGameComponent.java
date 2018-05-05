package tech.subluminal.client.presentation.customElements;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import tech.subluminal.client.presentation.controller.MainController;

public class EndGameComponent extends HBox {

  private MainController main;

  public EndGameComponent(MainController main, String winnerName){
    this.main = main;

    this.setAlignment(Pos.CENTER);
    Pane pane = new Pane();
    Label endText = new Label("End of Game" + "\n" + "Winner is: " + winnerName);
    pane.getChildren().add(endText);


    this.getChildren().add(pane);
  }

  public EndGameComponent(MainController main){
    this.main = main;

    this.setAlignment(Pos.CENTER);
    Pane pane = new Pane();
    StringProperty dots = new SimpleStringProperty();
    dots.set("");
    String failed = "You all failed Bob";
    Label endText = new Label();
    endText.textProperty().bind(Bindings.createStringBinding(() -> failed + dots, dots));
    pane.getChildren().add(endText);

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
    timeTl.play();

    this.getChildren().add(pane);
  }

}
