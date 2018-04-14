package tech.subluminal.shared.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

public class DeltaTimeUtilsTest {

  @Test
  public void timeAdvance() {
    List<Double> list = new ArrayList<>();

    double next = DeltaTimeUtils.advanceBy(10.0, 2.0, 3.0, list::add);
    assertEquals(1.0, next, 0.00001);
    assertEquals(Arrays.asList(2.0, 5.0, 8.0), list);
  }
}
