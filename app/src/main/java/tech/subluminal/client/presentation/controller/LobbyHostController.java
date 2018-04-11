package tech.subluminal.client.presentation.controller;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class LobbyHostController implements Initializable, Observer {
    @FXML
    private AnchorPane lobbyHost;

    @FXML
    private AnchorPane playerList;

    @FXML
    private AnchorPane lobbySettings;

    private MainController main;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        openWindow();
    }

    public void openWindow() {
        /*ScaleTransition scaleTlX = new ScaleTransition(Duration.seconds(0.2), lobbyList.getParent());
        scaleTlX.setFromX(0);
        scaleTlX.setToX(1);

        ScaleTransition scaleTlY = new ScaleTransition(Duration.seconds(0.5), lobbyList.getParent());
        scaleTlY.setFromY(0);
        scaleTlY.setToY(1);

        ParallelTransition paraTl = new ParallelTransition();

        paraTl.getChildren().addAll(scaleTlX, scaleTlY);
        paraTl.play();*/
    }

    @Override
    public void setMainController(MainController main) {
        this.main = main;
    }
}
