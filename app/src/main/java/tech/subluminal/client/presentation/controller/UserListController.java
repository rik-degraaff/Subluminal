package tech.subluminal.client.presentation.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import tech.subluminal.client.presentation.customElements.PlayerStatusComponent;
import tech.subluminal.shared.records.PlayerStatus;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class UserListController implements Initializable {
    @FXML
    private ListView<PlayerStatusComponent> playerBoard;
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


        addPlayerStatus("test", PlayerStatus.INGAME);
        //updatePlayerStatus("test", PlayerStatusComponent.INGAME);
        //removePlayerStatus("test");
    }

    public void addPlayerStatus(String username, PlayerStatus status){

        PlayerStatusComponent playerStatus = new PlayerStatusComponent("test", PlayerStatus.INGAME);
        PlayerStatusComponent playerStatus1 = new PlayerStatusComponent("test", PlayerStatus.INGAME);
        playerStatus1.updateStatus(PlayerStatus.ONLINE);

        //playerBoard.getItems().add(playerTag);
        playerBoard.getItems().add(playerStatus);
        playerBoard.getItems().add(playerStatus1);
        //players.add(playerTag);
    }


/**    private void removePlayerStatus(String username){
        for(PlayerStatusComponent player: playerBoard.getItems()){
            if(player.getText().equals(username)){
                Platform.runLater(() ->{
                    playerBoard.getItems().remove(player);

                });
                break;
            }
        }
    }**/

 /**   public void updatePlayerStatus(String username, PlayerStatusComponent status){
        for(Label player: playerBoard.getItems()){
            if(player.getText().equals(username)){
                player.setTextFill(Color.web(getStatusColor(status)));
            }
        }
    }**/

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
