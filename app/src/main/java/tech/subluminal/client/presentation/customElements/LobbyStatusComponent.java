package tech.subluminal.client.presentation.customElements;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Rectangle;
import tech.subluminal.shared.records.LobbyStatus;

public class LobbyStatusComponent extends HBox {

  private String lobbyID;
  private Rectangle statusBox;
  private Label playersNow;

  private final StringProperty lobbyToJoin = new SimpleStringProperty();

  public String getLobbyToJoin() {
    return lobbyToJoin.get();
  }

  public StringProperty lobbyToJoinProperty() {
    return lobbyToJoin;
  }

  public void setLobbyToJoin(String lobbyToJoin) {
    this.lobbyToJoin.set(lobbyToJoin);
  }

  public LobbyStatusComponent(String lobbyName, String lobbyID, int players, int max,
      LobbyStatus status) {
    this.lobbyID = lobbyID;

    HBox hbox = new HBox();
    hbox.setSpacing(5);
    this.setSpacing(5);

    statusBox = new Rectangle(20, 20);
    statusBox.setFill(status.getColor());
    Label name = new Label(lobbyName);
    playersNow = new Label(Integer.toString(players));

    Label playersMax = new Label(Integer.toString(max));

    Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    Button join = new Button("Join Lobby");
    Button password = new Button("Password");
    password.disableProperty();

    join.setOnMouseClicked(e -> {
      lobbyToJoinProperty().set(lobbyID);
    });

    hbox.getChildren().addAll(statusBox, name, playersNow, playersMax);
    this.getChildren().addAll(hbox, spacer, password, join);
  }

  public void updateLobby(int players) {
    playersNow.setText(Integer.toString(players));
  }

  public void updateLobby(LobbyStatus status) {
    statusBox.setFill(status.getColor());
  }
}
