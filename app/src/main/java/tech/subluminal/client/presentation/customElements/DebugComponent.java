package tech.subluminal.client.presentation.customElements;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DebugComponent extends HBox {

  private IntegerProperty averageFps;


  public DebugComponent(IntegerProperty averageFps) {
    this.averageFps = averageFps;
    Label fpsLabel = new Label("FPS: ");
    Label fpsCounter = new Label("0");
    fpsCounter.textProperty().bind(
        Bindings.createStringBinding(() -> Integer.toString( averageFps.getValue()), averageFps));


    this.getChildren().addAll(fpsLabel, fpsCounter);
    this.getStyleClass().add("fps-counter");
  }

  public double getAverageFps() {
    return averageFps.get();
  }

  public IntegerProperty averageFpsProperty() {
    return averageFps;
  }

  public void setAverageFps(int averageFps) {
    this.averageFps.set(averageFps);
  }
}
