package tech.subluminal.shared.logic.game;

import org.pmw.tinylog.Logger;

/**
 * Represents the repetitive steps that have to be done to update the game.
 */
public class SleepGameLoop implements GameLoop {

  private long msPerIteration;
  private Delegate delegate;

  public SleepGameLoop(int tps, Delegate delegate) {
    this.msPerIteration = 1000 / tps;
    this.delegate = delegate;
  }

  /**
   * Starts the game loop.
   */
  @Override
  public void start() {
    long lastTime = System.currentTimeMillis();
    while (true) {
      long timeBeforeBeforeTick = System.currentTimeMillis();
      delegate.beforeTick();
      long currentTime = System.currentTimeMillis();
      long elapsedTime = currentTime - lastTime;
      lastTime = currentTime;
      delegate.tick(elapsedTime / 1000.0);
      if (delegate.afterTick()) {
        break;
      }
      try {
        Thread.sleep(
            Math.max(0, msPerIteration - (System.currentTimeMillis() - timeBeforeBeforeTick)));
      } catch (InterruptedException e) {
        Logger.error(e);
      }
    }
  }

}
