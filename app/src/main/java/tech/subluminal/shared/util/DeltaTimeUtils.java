package tech.subluminal.shared.util;

import static tech.subluminal.shared.util.function.FunctionalUtils.takeWhile;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class DeltaTimeUtils {

  /**
   * Advances a time left to a periodic event by a certain delta.
   *
   * @param delta how much time should be advanced by.
   * @param nextLooparound the time left to the next time the event takes place.
   * @param loopAroundRate the frequency at which the event takes place.
   * @param onLoopAround the event that takes place at a loop around.
   * @return the new time eft until the next iteration.
   */
  public static Double advanceBy(
      Double delta, Double nextLooparound, Double loopAroundRate, Consumer<Double> onLoopAround
  ) {
    takeWhile(Stream.iterate(nextLooparound, t -> t + loopAroundRate), t -> t < delta)
        .forEach(onLoopAround);

    double newTimeLeft = nextLooparound - delta;
    return newTimeLeft > 0 ? newTimeLeft : loopAroundRate - ((-newTimeLeft) % loopAroundRate);
  }
}
