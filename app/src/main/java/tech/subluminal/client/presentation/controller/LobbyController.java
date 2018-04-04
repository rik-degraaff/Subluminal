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




    Platform.runLater(() ->{
      double widthX = spaceBackground.getWidth();
      double heightY = spaceBackground.getHeight();

      for(int i = 0; i < 1000; i++) {
        double angle = Math.random()*2*Math.PI;
        double x = Math.sin(angle)*(widthX+heightY);
        double y = Math.cos(angle)*(widthX+heightY);
        double radius = Math.floor(Math.random() * 3);
        Circle star = new Circle(widthX/2, heightY/2, 1, Color.WHITE);
        star.setOpacity(0.0);

        group.getChildren().add(star);

        final TranslateTransition transTl = new TranslateTransition(Duration.seconds(Math.random()*8+2), star);
        transTl.setToX(widthX/2 + x);
        transTl.setToY(heightY/2 + y);

        final FadeTransition fadeTl = new FadeTransition(Duration.seconds(2), star);
        fadeTl.setFromValue(0);
        fadeTl.setToValue(1);

        final ParallelTransition mainTl = new ParallelTransition(transTl, fadeTl);

        mainTl.setCycleCount(ParallelTransition.INDEFINITE);
        mainTl.play();
      }

      spaceBackground.getChildren().add(group);

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
