package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.presentation.LobbyPresenter;
import tech.subluminal.client.presentation.LobbyPresenter.Delegate;
import tech.subluminal.client.presentation.customElements.LobbyStatusComponent;
import tech.subluminal.client.stores.LobbyStore;
import tech.subluminal.client.stores.UserStore;
import tech.subluminal.shared.stores.records.SlimLobby;
import tech.subluminal.shared.util.MapperList;

public class LobbyListController implements Initializable {

  private final ListProperty<SlimLobby> slimLobbies = new SimpleListProperty<>();
  @FXML
  private ListView<LobbyStatusComponent> lobbyList;

  @FXML
  private TextField lobbyName;

  @FXML
  private AnchorPane window;

  private LobbyPresenter.Delegate lobbyDelegate;
  private UserStore userStore;

  public void setLobbyDelegate(Delegate lobbyDelegate) {
    this.lobbyDelegate = lobbyDelegate;
  }

  public ObservableList<SlimLobby> getSlimLobbies() {
    return slimLobbies.get();
  }

  public void setSlimLobbies(ObservableList<SlimLobby> slimLobbies) {
    this.slimLobbies.set(slimLobbies);
  }

  public ListProperty<SlimLobby> slimLobbiesProperty() {
    return slimLobbies;
  }

  /**
   * Sets the lobbystore on the controller.
   * @param store that holds the lobbies on the client.
   */
  public void setLobbyStore(LobbyStore store) {
    Logger.trace("Setting lobbystore of lobbyListComponent");
    lobbyList.setItems(new MapperList<>(store.observableLobbies(),
        lobby -> new LobbyStatusComponent(lobby.getSettings().getName(), lobby.getID(),
            lobby.getPlayerCount(), lobby.getSettings().getMaxPlayers(),
            lobby.getStatus(), lobbyDelegate)));
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    lobbyList.getItems()
        .forEach(l -> l.lobbyToJoinProperty().addListener(e -> Logger.trace("help me !!!")));
  }

  @FXML
  private void onLobbyCreate() {
    VBox vbox = new VBox();
    //vbox.prefHeightProperty().bind(window.heightProperty());
    //vbox.prefWidthProperty().bind(window.widthProperty());
    vbox.setAlignment(Pos.CENTER);

    HBox hbox = new HBox();
    hbox.prefHeightProperty().bind(window.heightProperty());
    hbox.prefWidthProperty().bind(window.widthProperty());
    hbox.setAlignment(Pos.CENTER);

    TextField lobbyName = new TextField();
    Label lobbyLabel = new Label("Lobbyname: ");
    lobbyLabel.getStyleClass().addAll("font-dos");
    HBox lobbyNameBox = new HBox(lobbyLabel, lobbyName);
    lobbyNameBox.setAlignment(Pos.CENTER);
    vbox.getChildren().addAll(lobbyNameBox);

    vbox.setSpacing(10);

    Button cancel = new Button("Cancel");
    Button ok = new Button("Ok");

    HBox promptButtons = new HBox(cancel,ok);
    promptButtons.setAlignment(Pos.CENTER);
    promptButtons.setSpacing(10);
    vbox.getChildren().addAll(promptButtons);

    vbox.setSpacing(20);

    hbox.getChildren().addAll(vbox);
    hbox.getStyleClass().addAll("console");
    window.getChildren().add(hbox);
    lobbyName.requestFocus();
    lobbyName.getStyleClass().addAll("font-dos", "textfield-green");
    lobbyName.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
      if(keyEvent.getCode() == KeyCode.ENTER){
        ok.fire();
        keyEvent.consume();
      }
    });

    cancel.setOnAction(e -> {
      removePrompt(hbox);
    });

    ok.setOnAction(e -> {
      String name = lobbyName.getText();
      if(name.equals("")){
        name = userStore.currentUser().get().use(u -> u.get().getUsername());
      }
      removePrompt(hbox);
      lobbyDelegate.createLobby(name);
    });

    Logger.trace("Creating lobby");
  }

  private void removePrompt(HBox hbox) {
    if(window.getChildren().contains(hbox)){
      window.getChildren().remove(hbox);
    }
  }

  @FXML
  private void onLobbyRefresh() {
    lobbyDelegate.getLobbyList();
  }


  public void setUserStore(UserStore userStore) {
    this.userStore = userStore;
  }
  
  @FXML
  private void onTutorialStart() {
    lobbyDelegate.startTutorial();
  }
}
