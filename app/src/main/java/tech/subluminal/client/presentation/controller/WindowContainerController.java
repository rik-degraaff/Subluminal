package tech.subluminal.client.presentation.controller;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import sun.applet.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class WindowContainerController implements Initializable {
    @FXML
    private AnchorPane window;

    @FXML
    private AnchorPane windowDock;

    @FXML
    private Label windowTitel;

    @FXML
    private Button windowClose;


    private MainController main;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //openWindow();
    }

    public void setMainController(MainController main){
        this.main = main;
    }

    public void openWindow(){
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

    @FXML
    public void onWindowClose(){
        main.onWindowClose();
        System.out.println("pressed");
    }

    public void setChild(Node node) {
        windowDock.getChildren().add(node);
    }
}
