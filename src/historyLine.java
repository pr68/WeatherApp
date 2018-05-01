import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

import java.util.ArrayList;

//Build line chart of temperatures
public class historyLine {
    public static LineChart tempLine() {
        //Set axis
        NumberAxis xAxis = new NumberAxis(2009, 2016, 1);
        NumberAxis yAxis = new NumberAxis();
        LineChart lc = new LineChart(xAxis,yAxis);

        //get data
        ArrayList<ArrayList<Double>> historicalWeather = stationHistory.history(Main.stationChooser.getValue());

        lc.setTitle("Temperature by Year");
        xAxis.setLabel("Year");
        yAxis.setLabel("Temperature (C)");

        //Add data to chart
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Max Temperature");

        for (ArrayList<Double> aHistoricalWeather : historicalWeather) {
            series1.getData().add(new XYChart.Data(aHistoricalWeather.get(0), aHistoricalWeather.get(1)));

        }

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Min Temperature");

        for (ArrayList<Double> aHistoricalWeather : historicalWeather) {
            series2.getData().add(new XYChart.Data(aHistoricalWeather.get(0), aHistoricalWeather.get(2)));
        }

        lc.getData().addAll(series1, series2);

        //Add tooltips on hover
        for (int i = 0; i < historicalWeather.size(); i++) {
            XYChart.Data item = (XYChart.Data)series1.getData().get(i);
            Tooltip.install(item.getNode(), new Tooltip(item.getYValue().toString() + " (C)"));
        }

        for (int i = 0; i < historicalWeather.size(); i++) {
            XYChart.Data item = (XYChart.Data)series2.getData().get(i);
            Tooltip.install(item.getNode(), new Tooltip(item.getYValue().toString() + " (C)"));
        }

        return lc;
    }
}
