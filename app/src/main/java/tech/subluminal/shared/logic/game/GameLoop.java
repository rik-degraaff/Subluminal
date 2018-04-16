package tech.subluminal.shared.logic.game;

/**
 * Represents the repetitive steps that have to be done to update the game.
 */
public class GameLoop {

  private long msPerIteration;
  private Delegate delegate;

  public GameLoop(int tps, Delegate delegate) {
    this.msPerIteration = 1000 / tps;
    this.delegate = delegate;
  }

  public void start() {
    long lastTime = System.currentTimeMillis();
    while (true) {
      long timeBeforeBeforeTick = System.currentTimeMillis();
      delegate.beforeTick();
      long currentTime = System.currentTimeMillis();
      long elapsedTime = currentTime - lastTime;
      lastTime = currentTime;
      delegate.tick(elapsedTime);
      delegate.afterTick();
      try {
        Thread.sleep(
            Math.max(0, msPerIteration - (System.currentTimeMillis() - timeBeforeBeforeTick)));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Defines the tasks that have to be done in each game loop.
   */
  public static interface Delegate {

    /**
     * Is used for the game-updating tasks that are not time-dependant and need to be done before
     * the time-critical game-updating tasks.
     */
    void beforeTick();

    /**
     * Is used to update the game state.
     *
     * @param elapsedTime the time that passed since the last call of the tick method.
     */
    void tick(long elapsedTime);

    /**
     * Is used for the game-updating tasks that are not time-dependant and need to be done after the
     * time-critical game-updating tasks.
     */
    void afterTick();
  }
}
