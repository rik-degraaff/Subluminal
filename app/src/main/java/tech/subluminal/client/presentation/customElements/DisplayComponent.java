package tech.subluminal.client.presentation.customElements;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;

public class DisplayComponent extends AnchorPane{
  private Box box;
  private VBox vbox;
  private final Integer width = 370;
  private final Integer height = 140;


  public DisplayComponent(){
    super();
    box = new Box();
    box.setDepth(10);
    box.setWidth(width);
    box.setHeight(height);
    vbox = new VBox(box);
    vbox.setAlignment(Pos.CENTER);
    //this.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

    this.getChildren().add(vbox);
    this.setMaxWidth(width);
    this.setMaxHeight(height);
  }

  public void setDisplay(AnchorPane node){
    this.getChildren().setAll(vbox, node);
    node.maxWidthProperty().bind(this.widthProperty());
    node.maxHeightProperty().bind(this.heightProperty());
    node.setTranslateZ(-5);
    node.setPadding(new Insets(5));
  }

  public void clearDisplay(){
    this.getChildren().clear();
    this.getChildren().add(vbox);
  }
}
