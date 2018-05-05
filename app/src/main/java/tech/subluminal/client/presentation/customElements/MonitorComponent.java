package tech.subluminal.client.presentation.customElements;

import javafx.beans.property.IntegerProperty;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.layout.Pane;

public class MonitorComponent extends Pane {

  private final NumberAxis xAxis = new NumberAxis();
  private final NumberAxis yAxis = new NumberAxis();
  private LineChart lineChart;
  private XYChart.Series series;

  public MonitorComponent(IntegerProperty averageFps){
    xAxis.setLabel("Time");
    yAxis.setLabel("FPS");

    lineChart = new LineChart<>(xAxis,yAxis);
    lineChart.setTitle("Average FPS");

    series = new XYChart.Series<Number, Number>();

    averageFps.addListener(e -> {
      append(averageFps.getValue());
    });

    series.setName("FPS");

    lineChart.getData().add(series);

    this.getChildren().add(lineChart);

  }

  public void append(int fps){
    series.getData().add(new Data(series.getData().size() + 1, fps));
  }

}
