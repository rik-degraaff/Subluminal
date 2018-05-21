package tech.subluminal.client.presentation.customElements;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import tech.subluminal.shared.util.FxUtils;

public class FpsUpdater {

  private final DoubleProperty averageFps= new SimpleDoubleProperty();
  private final Integer DELAY = 500;

  public FpsUpdater(){
    TimerTask fpsUpdater = new TimerTask() {
      @Override
      public void run() {
        Platform.runLater(() -> {
          averageFps.set(FxUtils.getAverageFPS());
        });
      }
    };

    Timer timer = new Timer();
    timer.schedule(fpsUpdater, 0, DELAY);
  }

  public double getAverageFps() {
    return averageFps.get();
  }

  public DoubleProperty averageFpsProperty() {
    return averageFps;
  }
}
