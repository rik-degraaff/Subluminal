package tech.subluminal.client.presentation.controller;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MenuController {
    @FXML
    private ImageView logoDock;

    @FXML
    private VBox menuDock;

    @FXML
    private HBox menuButtonDock;

    private void generateLogo() {
        Platform.runLater(() ->{
            Image logo = new Image("/tech/subluminal/resources/subluminal_logo.png");
            logoDock.setImage(logo);

            PauseTransition pauseTl = new PauseTransition(Duration.seconds(3));

            ScaleTransition scaleTl = new ScaleTransition(Duration.seconds(5), menuDock);
            scaleTl.setFromX(0);
            scaleTl.setFromY(0);
            scaleTl.setToX(1);
            scaleTl.setByY(1);

            SequentialTransition seqTl = new SequentialTransition();

            FadeTransition fadeTl = new FadeTransition(Duration.seconds(0.7), menuButtonDock);
            //fadeTl.setFromValue(0);
            fadeTl.setToValue(1);
            //fadeTl.setCycleCount(3);
            seqTl.getChildren().addAll(pauseTl,scaleTl);

            ScaleTransition scaleMainTl = new ScaleTransition(Duration.seconds(4), logoDock);
            scaleMainTl.setFromX(1);
            scaleMainTl.setFromY(1);
            scaleMainTl.setToX(1.1);
            scaleMainTl.setToY(1.1);

            scaleMainTl.setCycleCount(ScaleTransition.INDEFINITE);
            scaleMainTl.setAutoReverse(true);

            SequentialTransition seqMainTl = new SequentialTransition();
            seqMainTl.getChildren().addAll(seqTl, scaleMainTl);
            seqMainTl.play();
        });


    }
}
