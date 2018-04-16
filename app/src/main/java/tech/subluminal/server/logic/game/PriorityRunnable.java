package tech.subluminal.server.logic.game;

/**
 * A runnable with an associated priority.
 */
public class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {

  private final double priority;
  private final Runnable runnable;

  public PriorityRunnable(double priority, Runnable runnable) {
    this.priority = priority;
    this.runnable = runnable;
  }

  /**
   * Runs the Runnable that was passed into the constructor.
   */
  @Override
  public void run() {
    runnable.run();
  }

  @Override
  public int compareTo(PriorityRunnable o) {
    return Double.compare(priority, o.priority);
  }
}
