package tech.subluminal.shared.logic.game;

public interface GameLoop {

  void start();

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
    void tick(double elapsedTime);

    /**
     * Is used for the game-updating tasks that are not time-dependant and need to be done after the
     * time-critical game-updating tasks.
     */
    void afterTick();
  }
}
