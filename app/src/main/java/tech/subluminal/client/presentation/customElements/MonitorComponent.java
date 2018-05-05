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
  private int index = 0;

  public MonitorComponent(IntegerProperty averageFps) {
    xAxis.setLabel("Time");
    xAxis.setForceZeroInRange(false);
    yAxis.setLabel("FPS");
    yAxis.setForceZeroInRange(false);

    lineChart = new LineChart<>(xAxis, yAxis);
    lineChart.setTitle("Average FPS");

    series = new XYChart.Series<Number, Number>();

    averageFps.addListener(e -> {
      append(averageFps.getValue());
    });

    series.setName("FPS");

    lineChart.getData().add(series);

    this.getChildren().add(lineChart);

  }

  public void append(int fps) {
    if (series.getData().size() > 20) {
      series.getData().remove(0);
      xAxis.setLowerBound(xAxis.getLowerBound()-1);
    }
    series.getData().add(new Data(index, fps));

    index++;
  }

}
