package tech.subluminal.client.presentation.controller;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import tech.subluminal.client.presentation.customElements.LobbyStatusComponent;
import tech.subluminal.shared.records.LobbyStatus;

import java.net.URL;
import java.util.ResourceBundle;

public class LobbyListController implements Initializable, Observer {
    @FXML
    private ListView<LobbyStatusComponent> lobbyList;

    private MainController main;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ScaleTransition scaleTl = new ScaleTransition(Duration.seconds(0.2), lobbyList);
        scaleTl.setFromY(0);
        scaleTl.setToY(1);
        scaleTl.play();

        lobbyList.getItems().add(new LobbyStatusComponent("testLobby", "wefwef", 3,8, LobbyStatus.OPEN));

    }

    @FXML
    public void closePressed(){
        main.onLobbyListClose();
    }

    @Override
    public void setMainController(MainController main) {
        this.main = main;
    }
}
