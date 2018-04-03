package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Window;
import javafx.util.Duration;

public class LobbyController implements Initializable{

  @FXML
  private Parent chatView;
  @FXML
  private ChatController chatViewController;

  @FXML
  private TextArea chatHistory;
  @FXML
  private TextField messageText;

  @FXML
  private AnchorPane spaceBackground;

  @FXML
  private AnchorPane window;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    Group group = new Group();
    spaceBackground.getChildren().add(group);



    Platform.runLater(() ->{
      for(int i = 0; i < 1000; i++) {
        double x = Math.floor(Math.random() * spaceBackground.getWidth());
        double y = Math.floor(Math.random() * spaceBackground.getHeight());
        double radius = Math.floor(Math.random() * 3);
        Circle star = new Circle(x, y, radius, Color.WHITE);

        group.getChildren().add(star);

        TranslateTransition timeline = new TranslateTransition(Duration.seconds(Math.floor(Math.random()*10)), star);

        timeline.setFromX(x - Math.floor(Math.random()*100));
        timeline.setToX(x);
        timeline.setFromY(y - Math.floor(Math.random()*100));
        timeline.setToY(y);
        timeline.setCycleCount(TranslateTransition.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
      }


    });


  }

  public LobbyController getController(){
    return this;
  }

  public ChatController getChatController() {
    return this.chatViewController;
  }

  public void testStuff(){
    System.out.println("i am here");
    chatViewController.addMessageChat("hello from here");
    //chatController.addMessageChat("test");
    //chatHistory.appendText("heyy");
  }
}
