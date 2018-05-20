package tech.subluminal.client.presentation.customElements;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class BackgroundComponent extends AnchorPane {

  public BackgroundComponent(int stars) {
    generateBackground(stars);

  }

  private void generateBackground(int stars) {
    Group group = new Group();

    Platform.runLater(() -> {
      //remove old star animation if it exists
      this.getChildren().clear();

      double widthX = this.getScene().getWidth();
      double heightY = this.getScene().getHeight();

      for (int i = 0; i < stars; i++) {
        double angle = Math.random() * 2 * Math.PI;
        double x = Math.sin(angle) * (widthX + heightY);
        double y = Math.cos(angle) * (widthX + heightY);
        double radius = Math.floor(Math.random() * 3);
        Circle star = new Circle(0, 0, 1, Color.WHITE);
        star.setOpacity(0.0);

        group.getChildren().add(star);

        final PauseTransition pauseTl = new PauseTransition(
            Duration.seconds(Math.floor(Math.random() * 10)));

        final Timeline timeline = new Timeline();
        KeyValue startKvX = new KeyValue(star.centerXProperty(), x, Interpolator.EASE_IN);
        KeyValue startKvY = new KeyValue(star.centerYProperty(), y, Interpolator.EASE_IN);
        KeyFrame startKf = new KeyFrame(Duration.seconds(Math.random() * 12 + 3), startKvX,
            startKvY);

        timeline.getKeyFrames().add(startKf);

        final FadeTransition fadeTl = new FadeTransition(Duration.seconds(6), star);
        fadeTl.setFromValue(0);
        fadeTl.setToValue(1);

        final ScaleTransition scaleTl = new ScaleTransition(
            Duration.seconds(Math.random() * 12 + 3), star);
        scaleTl.setFromX(0.3);
        scaleTl.setFromY(0.3);
        scaleTl.setToX(Math.sqrt(radius * 2));
        scaleTl.setToY(Math.sqrt(radius * 2));

        ParallelTransition paraTl = new ParallelTransition(timeline, fadeTl, scaleTl);

        paraTl.setCycleCount(ParallelTransition.INDEFINITE);

        SequentialTransition mainTl = new SequentialTransition();
        mainTl.getChildren().addAll(pauseTl, paraTl);
        mainTl.play();
      }

      this.getChildren().add(group);

      Platform.runLater(() -> {
        this.translateXProperty().bind(Bindings.createDoubleBinding(() -> getScene().getWidth() / 2, getScene().widthProperty()));
        this.translateYProperty().bind(Bindings.createDoubleBinding(() -> getScene().getHeight() / 2, getScene().heightProperty()));
      });

    });
  }
}
