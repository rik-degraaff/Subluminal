package tech.subluminal.client.presentation.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class SettingsController implements Observer, Initializable {

  private MainController main;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  @Override
  public void setMainController(MainController main) {
    this.main = main;
  }
}
