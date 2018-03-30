package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SampleController {

  public Label helloWorld;

  public TextArea chatHistory;

  public void sayHelloWorld(ActionEvent actionEvent) {
    helloWorld.setText("Hello Traveler!");
  }

  public void initialze(){
    chatHistory.setText("Hello World");
  }
}
