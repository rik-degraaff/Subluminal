package tech.subluminal.client.presentation.customElements;

import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import tech.subluminal.shared.util.FxUtils;

public class FpsUpdater {

  private final IntegerProperty averageFps= new SimpleIntegerProperty();
  private final Integer DELAY = 1000;

  public FpsUpdater(){
    TimerTask fpsUpdater = new TimerTask() {
      @Override
      public void run() {
        Platform.runLater(() -> {
          averageFps.set((int) FxUtils.getAverageFPS());
        });
      }
    };

    Timer timer = new Timer();
    timer.schedule(fpsUpdater,0,DELAY);
  }

  public int getAverageFps() {
    return averageFps.get();
  }

  public IntegerProperty averageFpsProperty() {
    return averageFps;
  }
}
