package tech.subluminal.client.presentation.customElements;

import java.text.DecimalFormat;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DebugComponent extends HBox {

  private DoubleProperty averageFps;
  private static DecimalFormat format = new DecimalFormat("#.0");

  public DebugComponent(DoubleProperty averageFps, String label) {
    this.averageFps = averageFps;
    Label fpsLabel = new Label(label + ": ");
    Label fpsCounter = new Label("0.0");
    fpsCounter.textProperty().bind(
        Bindings.createStringBinding(() -> format.format(averageFps.getValue()), averageFps));

    this.getChildren().addAll(fpsLabel, fpsCounter);
    this.getStyleClass().add("fps-counter");
  }

  public double getAverageFps() {
    return averageFps.get();
  }

  public DoubleProperty averageFpsProperty() {
    return averageFps;
  }

  public void setAverageFps(int averageFps) {
    this.averageFps.set(averageFps);
  }
}
