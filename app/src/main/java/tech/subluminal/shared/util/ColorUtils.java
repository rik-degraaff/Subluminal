package tech.subluminal.shared.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.paint.Color;

public class ColorUtils {


  public static List<Color> getNiceColors(int amount){
    return Stream.iterate(0.0, hue -> hue + 360.0/amount)
        .map(hue -> Color.hsb(hue, 1.0, 1.0))
        .limit(amount)
        .collect(Collectors.toList());
  }

}
