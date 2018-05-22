package tech.subluminal.client.presentation.customElements;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
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
  private final LobbyPresenter.Delegate lobbyDelegate;
  private String lobbyID;
  private Rectangle statusBox;
  private Label playersNow;

  public LobbyStatusComponent(String lobbyName, String lobbyID, int players, int max,
      LobbyStatus status, LobbyPresenter.Delegate delegate) {
    this.lobbyDelegate = delegate;
    this.lobbyID = lobbyID;
    this.getStyleClass().add("font-white");

    HBox hbox = new HBox();
    hbox.setSpacing(20);
    hbox.setPadding(new Insets(0, 0, 0, 10));
    this.setSpacing(20);

    statusBox = new Rectangle(20, 20);
    statusBox.setFill(status.getColor());
    Label name = new Label(lobbyName);
    playersNow = new Label(Integer.toString(players));

    Label playersMax = new Label(Integer.toString(max));

    Label spectate = new Label("Spectate");
    spectate.getStyleClass().add("font-blue");
    spectate.setOnMouseClicked(e -> {
      lobbyToJoinProperty().set(lobbyID);
      delegate.joinGame(lobbyID);
    });

    Label join = new Label("Join Lobby");
    join.getStyleClass().add("font-blue");
    join.setOnMouseClicked(e -> {
      Logger.trace("on Mouse Click");
      lobbyToJoinProperty().set(lobbyID);
      delegate.joinLobby(lobbyID);
    });

    Pane spacer = new Pane();
    HBox.setHgrow(spacer, Priority.ALWAYS);

    int length = 20;
    String line = " ";
    Label space = new Label("");

    name.getStyleClass().add("font-white");

    length -= lobbyName.length();

    if(length >= 0){
      for(int i = 0; i < length; i++){
        space.setText(space.getText() + line);
      }
    }

    playersNow.getStyleClass().add("font-white");
    playersMax.getStyleClass().add("font-white");
    Label slash = new Label("/");
    slash.getStyleClass().add("font-white");
    hbox.getChildren().addAll(statusBox, name, space, playersNow, slash, playersMax);
    this.getChildren().addAll(hbox, spacer);

    if (status == LobbyStatus.INGAME) {
      this.getChildren().add(spectate);
    }

    if (status == LobbyStatus.OPEN) {
      this.getChildren().add(join);
    }


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
