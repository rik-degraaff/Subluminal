package tech.subluminal.client.presentation.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tech.subluminal.client.presentation.customElements.PlayerStatus;
import tech.subluminal.shared.records.Channel;
import tech.subluminal.shared.records.Status;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class UserListController implements Initializable {
    @FXML
    private ListView<Label> playerBoard;
    @FXML
    private VBox playerBoardWrapper;
    @FXML
    private Button updaterPlayerBoard;

    private LinkedList<Label> players;

    private boolean isBoardShown = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(!isBoardShown){
            playerBoard.setVisible(false);
        }


        addPlayerStatus("test", Status.INGAME);
        updatePlayerStatus("test", Status.INGAME);
        //removePlayerStatus("test");
    }

    public void addPlayerStatus(String username, Status status){
        Label playerTag = new Label(username);

        playerTag.setTextFill(Color.web(getStatusColor(status)));

        playerBoard.getItems().add(playerTag);
        //players.add(playerTag);
    }

    private String getStatusColor(Status status) {
        String color;

        switch (status){
            case INGAME:
                color = "red";
                break;
            case ONLINE:
                color = "green";
                break;
            case INLOBBY:
                color = "yellow";
                break;
            default:
                color = "grey";
        }
        return color;
    }

    private void removePlayerStatus(String username){
        for(Label player: playerBoard.getItems()){
            if(player.getText().equals(username)){
                Platform.runLater(() ->{
                    playerBoard.getItems().remove(player);

                });
                break;
            }
        }
    }

    public void updatePlayerStatus(String username, Status status){
        for(Label player: playerBoard.getItems()){
            if(player.getText().equals(username)){
                player.setTextFill(Color.web(getStatusColor(status)));
            }
        }
    }

    public void switchPlayerBoard(ActionEvent actionEvent) {
        if(isBoardShown){
            playerBoard.setVisible(false);
            isBoardShown = false;
            updaterPlayerBoard.setText("Show Players");
        }else {
            playerBoard.setVisible(true);
            isBoardShown = true;
            updaterPlayerBoard.setText("Hide Players");
        }

    }

}
