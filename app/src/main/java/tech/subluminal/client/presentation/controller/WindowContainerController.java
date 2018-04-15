package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class WindowContainerController implements Initializable {

  private final StringProperty titel = new SimpleStringProperty();
  @FXML
  private AnchorPane window;
  @FXML
  private AnchorPane windowDock;
  @FXML
  private Label windowTitel;
  @FXML
  private Button windowClose;
  private MainController main;

  public String getTitel() {
    return titel.get();
  }

  public void setTitel(String titel) {
    this.titel.set(titel);
  }

  public StringProperty titelProperty() {
    return titel;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //openWindow();
    windowTitel.textProperty().bind(titel);
  }

  public void setMainController(MainController main) {
    this.main = main;
  }

  public void openWindow() {
    ScaleTransition scaleTlX = new ScaleTransition(Duration.seconds(0.2), window.getParent());
    scaleTlX.setFromX(0);
    scaleTlX.setToX(1);

    ScaleTransition scaleTlY = new ScaleTransition(Duration.seconds(0.5), window.getParent());
    scaleTlY.setFromY(0);
    scaleTlY.setToY(1);

    ParallelTransition paraTl = new ParallelTransition();

    paraTl.getChildren().addAll(scaleTlX, scaleTlY);
    paraTl.play();
  }

  public void closeWindow() {
    ScaleTransition scaleTlX = new ScaleTransition(Duration.seconds(0.2), window.getParent());
    scaleTlX.setFromX(1);
    scaleTlX.setToX(0);

    ScaleTransition scaleTlY = new ScaleTransition(Duration.seconds(0.5), window.getParent());
    scaleTlY.setFromY(1);
    scaleTlY.setToY(0);

    ParallelTransition paraTl = new ParallelTransition();

    paraTl.getChildren().addAll(scaleTlX, scaleTlY);
    paraTl.setOnFinished(e -> removeWindow());
    paraTl.play();
  }

  private void removeWindow() {
    main.removeWindow();
  }

  @FXML
  public void onWindowClose() {
    main.onWindowClose();
  }

  public void setChild(Node node) {
    windowDock.getChildren().add(node);
  }

}
