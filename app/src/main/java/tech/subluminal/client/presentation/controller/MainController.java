package tech.subluminal.client.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.customElements.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Parent chatView;
    @FXML
    private ChatController chatViewController;

    @FXML
    private Parent userListView;
    @FXML
    private UserListController userListController;

    @FXML
    private AnchorPane spaceBackgroundDock;

    @FXML
    private AnchorPane chatDock;

    @FXML
    private AnchorPane menuDock;

    private BackgroundComponent background;

    private MenuComponent menu;

    private LobbyListComponent lobbyList;

    private SettingsComponent settings;

    private LobbyHostComponent lobbyHost;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        background = new BackgroundComponent(2000);
        spaceBackgroundDock.getChildren().add(background);

        menu = new MenuComponent(this);
        lobbyList = new LobbyListComponent(this);
        settings = new SettingsComponent(this);
        lobbyHost = new LobbyHostComponent(this);

        menuDock.getChildren().add(menu);
    }


    public MainController getController() {
        return this;
    }

    public ChatController getChatController() {
        return this.chatViewController;
    }

    public void onWindowResizeHandle(int diffX, int diffY) {
        background.onWindowResize(diffX, diffY);
    }

    public void onJoinHandle() {
        menuDock.getChildren().remove(menu);

        menuDock.getChildren().add(lobbyList);
        lobbyList.onWindowOpen();
    }

    public void onHostOpenHandle() {
        menuDock.getChildren().remove(menu);

        menuDock.getChildren().add(lobbyHost);
        lobbyHost.onWindowOpen();

    }

    public void onSettingOpenHandle() {
        menuDock.getChildren().forEach(i -> i.setVisible(false));


        menuDock.getChildren().add(settings);
    }

    public void onSettingsOpenClose() {
        menuDock.getChildren().remove(settings);

        menuDock.getChildren().forEach(i -> i.setVisible(true));
    }

    public void onLobbyListClose() {
        menuDock.getChildren().remove(lobbyList);

        menuDock.getChildren().add(menu);
    }
}
