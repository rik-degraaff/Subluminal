package tech.subluminal.client.presentation.customElements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;

public class DisplayComponent extends AnchorPane {

  private final Integer width = 370;
  private final Integer height = 130;
  private Box box;
  private VBox vbox;
  private Rectangle rect;


  public DisplayComponent() {
    super();
    rect = new Rectangle(20, 20, Color.BLACK);
    box = new Box();
    box.setDepth(10);
    box.setWidth(width);
    box.setHeight(height);
    vbox = new VBox(new Group(box, rect));
    rect.widthProperty().bind(box.widthProperty());
    rect.heightProperty().bind(box.heightProperty());
    rect.translateXProperty().bind(box.widthProperty().divide(2).negate());
    rect.translateYProperty().bind(box.heightProperty().divide(2).negate());
    rect.setTranslateZ(-4);
    vbox.setAlignment(Pos.CENTER);


    //this.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

    this.getChildren().add(vbox);
    this.setMaxWidth(width);
    this.setMaxHeight(height);
  }

  public void setDisplay(AnchorPane node) {
    this.getChildren().setAll(vbox, node);
    node.maxWidthProperty().bind(this.widthProperty());
    node.maxHeightProperty().bind(this.heightProperty());
    node.prefWidthProperty().bind(this.widthProperty().subtract(10));
    node.prefHeightProperty().bind(this.heightProperty());
    node.setTranslateZ(-5);
    node.setPadding(new Insets(5));
  }

  public void clearDisplay() {
    this.getChildren().clear();
    this.getChildren().add(vbox);
  }
}
