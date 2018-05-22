package tech.subluminal.client.presentation.customElements;

import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tech.subluminal.server.stores.records.HighScore;

public class HighscoreComponent extends ScrollPane {

  private VBox vbox;

  public HighscoreComponent() {
    //super();

    vbox = new VBox();
    vbox.setAlignment(Pos.CENTER);
    vbox.setSpacing(20);
    vbox.setFillWidth(true);

    this.setContent(vbox);
    //this.setFitToHeight(true);
    this.setFitToWidth(true);
    this.getStyleClass().add("window-opac");
    this.setHbarPolicy(ScrollBarPolicy.NEVER);

  }

  public void update(List<HighScore> highScores) {

    Platform.runLater(() -> {
      vbox.getChildren().clear();

      highScores.stream().forEach(highScore -> {
        HBox box = new HBox();
        box.setSpacing(20);
        Label user = new Label(highScore.getUsername());
        user.getStyleClass().add("font-blue");
        box.getChildren().add(user);
        Label score = new Label(Double.toString(highScore.getScore()));
        score.getStyleClass().add("font-white");
        box.getChildren().add(score);
        box.setPadding(new Insets(0, 0, 0, 20));
        vbox.getChildren().add(box);
      });
    });


  }
}
