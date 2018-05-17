package tech.subluminal.shared.util;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.AnchorPane;

/**
 * Can be used to bind the x and y coordinates on respective to a node.
 */
public class DrawingUtils {

  /**
   * Can be used to get a dynamic x property.
   *
   * @param parent on which the x propety has to be placed.
   * @param xPos double value between 0 and 1, representing the x value.
   * @return a dynamic x postion on the parent node.
   */
  public static DoubleProperty getXPosition(AnchorPane parent, DoubleProperty xPos) {
    DoubleProperty parentWidthProperty = getParentWidthProperty(parent);

    DoubleProperty parentHeightProperty = getParentHeightProperty(parent);

    return getYProperty(xPos, parentWidthProperty, parentWidthProperty,
        parentHeightProperty.doubleValue(), parentWidthProperty, parentHeightProperty);
  }

  /**
   * Can be used to get a dynamic y property.
   *
   * @param parent on which the y propety has to be placed.
   * @param yPos double value between 0 & 1, representing the y value.
   * @return a dynamic y postion on the parent node.
   */
  public static DoubleProperty getYPosition(AnchorPane parent, DoubleProperty yPos) {
    DoubleProperty parentWidthProperty = getParentWidthProperty(parent);

    DoubleProperty parentHeightProperty = getParentHeightProperty(parent);

    return getYProperty(yPos, parentWidthProperty, parentHeightProperty,
        parentHeightProperty.doubleValue(), parentWidthProperty,
        parentHeightProperty);
  }

  private static DoubleProperty getYProperty(DoubleProperty yPos,
      DoubleProperty parentWidthProperty, DoubleProperty parentHeightProperty, double v,
      DoubleProperty parentWidthProperty2, DoubleProperty parentHeightProperty2) {
    DoubleProperty doubleProperty = new SimpleDoubleProperty();
    doubleProperty.bind(Bindings
        .createDoubleBinding(
            () -> parentHeightProperty.doubleValue() / 2 + (yPos.getValue() - 0.5) * Math
                .min(parentWidthProperty.doubleValue(), v),
            yPos, parentWidthProperty2, parentHeightProperty2));
    doubleProperty.add(10);

    return doubleProperty;
  }

  private static DoubleProperty getParentHeightProperty(AnchorPane parent) {
    DoubleProperty parentHeightProperty = new SimpleDoubleProperty();
    parentHeightProperty.bind(parent.heightProperty().subtract(20));
    return parentHeightProperty;
  }

  private static DoubleProperty getParentWidthProperty(AnchorPane parent) {
    DoubleProperty parentWidthProperty = new SimpleDoubleProperty();
    parentWidthProperty.bind(parent.widthProperty().subtract(20));
    return parentWidthProperty;
  }
}
