package tech.subluminal.shared.logic.game;

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
      delegate.afterTick();
      try {
        Thread.sleep(
            Math.max(0, msPerIteration - (System.currentTimeMillis() - timeBeforeBeforeTick)));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
