package tech.subluminal.client.presentation.customElements;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.layout.Pane;

public class MonitorComponent extends Pane {

  private final NumberAxis xAxis = new NumberAxis();
  private final NumberAxis yAxis = new NumberAxis();
  private LineChart<Number, Number> lineChart;
  private XYChart.Series<Number, Number> series;
  private int index = 0;
  private double prevLowerBound;
  private double prevUpperBound;

  public MonitorComponent(DoubleProperty averageFps, String label) {
    xAxis.setLabel("Time");
    xAxis.setForceZeroInRange(false);
    yAxis.setLabel(label);
    yAxis.setAutoRanging(false);
    yAxis.setForceZeroInRange(false);
    yAxis.setLowerBound(0);
    yAxis.setUpperBound(0);

    lineChart = new LineChart<>(xAxis, yAxis);
    lineChart.setTitle("Average " + label);

    series = new XYChart.Series<>();

    averageFps.addListener(e -> {
      append(averageFps.getValue());
    });

    lineChart.setLegendVisible(false);

    lineChart.getData().add(series);

    this.getChildren().add(lineChart);

  }

  private void append(double fps) {
    final ObservableList<Data<Number, Number>> data = series.getData();
    if (data.size() > 50) {
      data.remove(0);
      xAxis.setLowerBound(xAxis.getLowerBound() - 1);
    }
    data.add(new Data<>(index, fps));

    final List<Data<Number, Number>> dataTail = data
        .subList(Math.max(data.size() - 50, 0), data.size());
    double lower = dataTail.stream().mapToDouble(d -> d.getYValue().doubleValue()).min()
        .orElse(0.0);
    if (lower < prevLowerBound - 0.0001 || lower > prevLowerBound + 1) {
      yAxis.setLowerBound(lower - 1);
    }

    double upper = dataTail.stream().mapToDouble(d -> d.getYValue().doubleValue()).max()
        .orElse(0.0);
    if (upper > prevUpperBound + 0.0001 || upper < prevUpperBound - 1) {
      yAxis.setUpperBound(upper + 1);
    }

    prevLowerBound = yAxis.getLowerBound();
    prevUpperBound = yAxis.getUpperBound();
    index++;
  }

}
