package tech.subluminal.client.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import tech.subluminal.client.presentation.customElements.*;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.records.PlayerStatus;

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
    private UserListController userListViewController;

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

    private WindowContainerComponent window;

    private UserStore userStore;

    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;

        userListViewController.setUserStore(userStore);
    }


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

    public UserListController getUserListController() { return  this.userListViewController;}

    public void onWindowResizeHandle(int diffX, int diffY) {
        background.onWindowResize(diffX, diffY);
    }

    public void onJoinHandle() {
        menuDock.getChildren().clear();

        window = new WindowContainerComponent(this, lobbyList);

        menuDock.getChildren().add(window);
        window.onWindowOpen();
    }

    public void onHostOpenHandle() {
        menuDock.getChildren().remove(menu);

        window = new WindowContainerComponent(this, lobbyHost);

        menuDock.getChildren().add(window);
        window.onWindowOpen();

    }

    public void onSettingOpenHandle() {
        menuDock.getChildren().remove(menu);

        window = new WindowContainerComponent(this, settings);

        menuDock.getChildren().add(window);
        window.onWindowOpen();
    }

    public void onWindowClose(){
        menuDock.getChildren().clear();

        menuDock.getChildren().add(menu);
    }
}
