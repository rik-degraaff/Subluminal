package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import tech.subluminal.client.presentation.customElements.ChatComponent;

public class LobbyController implements Initializable{

  public static final int STAR_AMOUNT = 2000;
  @FXML
  private Parent chatView;
  @FXML
  private ChatController chatViewController;

  @FXML
  private Parent userListView;
  @FXML
  private UserListController userListController;

  @FXML
  private TextArea chatHistory;
  @FXML
  private TextField messageText;

  @FXML
  private AnchorPane spaceBackground;

  @FXML
  private AnchorPane chatDock;

  @FXML
  private AnchorPane window;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    generateBackground();


  }



  private void generateBackground() {
    Group group = new Group();


    Platform.runLater(() ->{
      System.out.println(spaceBackground.getChildren().toString());

      //remove old star animation if it exists
      spaceBackground.getChildren().clear();
      System.out.println(spaceBackground.getChildren().toString());

      double widthX = spaceBackground.getWidth();
      double heightY = spaceBackground.getHeight();

      for(int i = 0; i < STAR_AMOUNT; i++) {
        double angle = Math.random()*2*Math.PI;
        double x = Math.sin(angle)*(widthX+heightY);
        double y = Math.cos(angle)*(widthX+heightY);
        double radius = Math.floor(Math.random() * 3);
        Circle star = new Circle(0, 0, 1, Color.WHITE);
        star.setOpacity(0.0);

        group.getChildren().add(star);

        final PauseTransition pauseTl = new PauseTransition(Duration.seconds(Math.floor(Math.random()*10)));

        final Timeline timeline = new Timeline();
        KeyValue startKvX = new KeyValue(star.centerXProperty(), x, Interpolator.EASE_IN);
        KeyValue startKvY = new KeyValue(star.centerYProperty(), y, Interpolator.EASE_IN);
        KeyFrame startKf = new KeyFrame(Duration.seconds(Math.random()*12+3),startKvX,startKvY);

        timeline.getKeyFrames().add(startKf);

        final FadeTransition fadeTl = new FadeTransition(Duration.seconds(6), star);
        fadeTl.setFromValue(0);
        fadeTl.setToValue(1);

        final ScaleTransition scaleTl = new ScaleTransition(Duration.seconds(Math.random()*12+3), star);
        scaleTl.setFromX(0.3);
        scaleTl.setFromY(0.3);
        scaleTl.setToX(Math.sqrt(radius*2));
        scaleTl.setToY(Math.sqrt(radius*2));

        ParallelTransition paraTl = new ParallelTransition(timeline, fadeTl,scaleTl);

        paraTl.setCycleCount(ParallelTransition.INDEFINITE);

        SequentialTransition mainTl = new SequentialTransition();
        mainTl.getChildren().addAll(pauseTl,paraTl);
        mainTl.play();
      }

      spaceBackground.getChildren().add(group);

      spaceBackground.setTranslateX(widthX/2);
      spaceBackground.setTranslateY(heightY/2);

    });
  }

  /**
   * Handler to call, when a window gets resized.
   * @param diffX
   */
  public void onWindowResize(int diffX, int diffY){
    //generateBackground();
    Platform.runLater(() ->{
      spaceBackground.setTranslateX(spaceBackground.getWidth()/2);
      spaceBackground.setTranslateY(spaceBackground.getHeight()/2);
    });

  }

  public LobbyController getController(){
    return this;
  }

  public ChatController getChatController() {
    return this.chatViewController;
  }

}
