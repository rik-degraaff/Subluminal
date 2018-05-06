package tech.subluminal.client.presentation.customElements;

import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tech.subluminal.server.stores.records.HighScore;

public class HighscoreComponent extends VBox {

  public HighscoreComponent(){
    super();
    this.setAlignment(Pos.CENTER);
    this.setSpacing(20);
    this.setFillWidth(true);


  }

  public void update(List<HighScore> highScores) {

    Platform.runLater(() -> {
      this.getChildren().clear();

      highScores.stream().forEach(highScore -> {
        HBox box = new HBox();
        box.setSpacing(20);
        box.getChildren().add(new Label(highScore.getUsername()));
        box.getChildren().add(new Label(Double.toString(highScore.getScore())));
        this.getChildren().add(box);
      });
    });



  }
}
