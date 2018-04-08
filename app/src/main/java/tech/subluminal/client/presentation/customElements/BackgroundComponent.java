package tech.subluminal.client.presentation.customElements;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class BackgroundComponent extends AnchorPane {

    public BackgroundComponent(int stars){
        generateBackground(stars);

    }

    private void generateBackground(int stars) {
        Group group = new Group();


        Platform.runLater(() ->{
            System.out.println(this.getChildren().toString());

            //remove old star animation if it exists
            this.getChildren().clear();
            System.out.println(this.getChildren().toString());

            double widthX = this.getScene().getWidth();
            double heightY = this.getScene().getHeight();

            for(int i = 0; i < stars; i++) {
                double angle = Math.random()*2*Math.PI;
                double x = Math.sin(angle)*(widthX+heightY);
                double y = Math.cos(angle)*(widthX+heightY);
                double radius = Math.floor(Math.random() * 3);
                Circle star = new Circle(0, 0, 1, Color.WHITE);
                star.setOpacity(0.0);

                group.getChildren().add(star);

                final PauseTransition pauseTl = new PauseTransition(Duration.seconds(Math.floor(Math.random()*10)));

                final Timeline timeline = new Timeline();
                KeyValue startKvX = new KeyValue(star.centerXProperty(), x, Interpolator.EASE_IN);
                KeyValue startKvY = new KeyValue(star.centerYProperty(), y, Interpolator.EASE_IN);
                KeyFrame startKf = new KeyFrame(Duration.seconds(Math.random()*12+3),startKvX,startKvY);

                timeline.getKeyFrames().add(startKf);

                final FadeTransition fadeTl = new FadeTransition(Duration.seconds(6), star);
                fadeTl.setFromValue(0);
                fadeTl.setToValue(1);

                final ScaleTransition scaleTl = new ScaleTransition(Duration.seconds(Math.random()*12+3), star);
                scaleTl.setFromX(0.3);
                scaleTl.setFromY(0.3);
                scaleTl.setToX(Math.sqrt(radius*2));
                scaleTl.setToY(Math.sqrt(radius*2));

                ParallelTransition paraTl = new ParallelTransition(timeline, fadeTl,scaleTl);

                paraTl.setCycleCount(ParallelTransition.INDEFINITE);

                SequentialTransition mainTl = new SequentialTransition();
                mainTl.getChildren().addAll(pauseTl,paraTl);
                mainTl.play();
            }

            this.getChildren().add(group);

            this.setTranslateX(widthX/2);
            this.setTranslateY(heightY/2);

        });
    }


    /**
     * Handler to call, when a window gets resized.
     * @param diffX
     */
    public void onWindowResize(int diffX, int diffY){
        Platform.runLater(() -> {
            this.setTranslateX(((AnchorPane)this.getParent()).getWidth()/2);
            this.setTranslateY(((AnchorPane)this.getParent()).getHeight()/2);
        });

    }
}
