package tech.subluminal.client.presentation.customElements;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import tech.subluminal.shared.util.FxUtils;

public class DebugComponent extends HBox {

  private long DELAY = 1000;
  private DoubleProperty averageFps = new SimpleDoubleProperty();


  public DebugComponent() {
    Label fpsLabel = new Label("FPS: ");
    Label fpsCounter = new Label("0");
    fpsCounter.textProperty().bind(
        Bindings.createStringBinding(() -> Integer.toString((int) averageFps.doubleValue()), averageFps));

    TimerTask fpsUpdater = new TimerTask() {
      @Override
      public void run() {
        Platform.runLater(() -> {
          setAverageFps(FxUtils.getAverageFPS());
        });
      }
    };

    Timer timer = new Timer();
    timer.schedule(fpsUpdater,0,DELAY);

    this.getChildren().addAll(fpsLabel, fpsCounter);
    this.getStyleClass().add("fps-counter");
  }

  public double getAverageFps() {
    return averageFps.get();
  }

  public DoubleProperty averageFpsProperty() {
    return averageFps;
  }

  public void setAverageFps(double averageFps) {
    this.averageFps.set(averageFps);
  }
}
