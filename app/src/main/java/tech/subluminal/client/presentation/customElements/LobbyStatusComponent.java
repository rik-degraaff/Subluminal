package tech.subluminal.client.presentation.customElements;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Rectangle;
import org.pmw.tinylog.Logger;
import tech.subluminal.client.presentation.LobbyPresenter;
import tech.subluminal.shared.records.LobbyStatus;

public class LobbyStatusComponent extends HBox {

  private final StringProperty lobbyToJoin = new SimpleStringProperty();
  private String lobbyID;
  private Rectangle statusBox;
  private Label playersNow;
  private final LobbyPresenter.Delegate lobbyDelegate;

  public LobbyStatusComponent(String lobbyName, String lobbyID, int players, int max,
      LobbyStatus status, LobbyPresenter.Delegate delegate) {
    Logger.trace("LobbyStatusComponent !!!!!!!!!!!");
    this.lobbyDelegate = delegate;
    this.lobbyID = lobbyID;

    HBox hbox = new HBox();
    hbox.setSpacing(5);
    this.setSpacing(5);

    statusBox = new Rectangle(20, 20);
    statusBox.setFill(status.getColor());
    Label name = new Label(lobbyName);
    playersNow = new Label(Integer.toString(players));

    Label playersMax = new Label(Integer.toString(max));

    Label join = new Label("Join Lobby");
    join.setOnMouseClicked(e -> {
      Logger.trace("on Mouse Click");
      lobbyToJoinProperty().set(lobbyID);
      delegate.joinLobby(lobbyID);
    });

    Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);


    hbox.getChildren().addAll(statusBox, name, playersNow, playersMax);
    this.getChildren().addAll(hbox, spacer, join);
  }

  public String getLobbyToJoin() {
    return lobbyToJoin.get();
  }

  public void setLobbyToJoin(String lobbyToJoin) {
    this.lobbyToJoin.set(lobbyToJoin);
  }

  public StringProperty lobbyToJoinProperty() {
    return lobbyToJoin;
  }

  public void updateLobby(int players) {
    playersNow.setText(Integer.toString(players));
  }

  public void updateLobby(LobbyStatus status) {
    statusBox.setFill(status.getColor());
  }
}
