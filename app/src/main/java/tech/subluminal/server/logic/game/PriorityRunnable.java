package tech.subluminal.server.logic.game;

/**
 * A runnable with an associated priority.
 */
public class PriorityRunnable implements Runnable, Comparable<PriorityRunnable> {

  private final long priority;
  private final Runnable runnable;

  public PriorityRunnable(long priority, Runnable runnable) {
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
    return Long.compare(priority, o.priority);
  }
}
