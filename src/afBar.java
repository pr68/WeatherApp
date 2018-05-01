import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

import java.util.ArrayList;

//Produce bar chart object for Air Frost
public class afBar {
    public static BarChart<String,Number> getAfBar() {
        //Get data
        ArrayList<ArrayList<Double>> historicalWeather = stationHistory.history(Main.stationChooser.getValue());

        //Set axis
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        BarChart<String, Number> afB = new BarChart<>(xAxis, yAxis);

        afB.setTitle("Total Air Frost Days per Year");
        afB.setLegendVisible(false);
        xAxis.setLabel("Year");
        yAxis.setLabel("Air Frost Days");

        afB.setBarGap(5);

        //Add data to chart
        XYChart.Series series = new XYChart.Series();
        for (ArrayList<Double> aHistoricalWeather : historicalWeather) {
            series.getData().add(new XYChart.Data(String.valueOf(aHistoricalWeather.get(0)), aHistoricalWeather.get(3)));
        }

        afB.getData().add(series);

        //Add tooltip on hover
        for (int i = 0; i < historicalWeather.size(); i++) {
            XYChart.Data item = (XYChart.Data) series.getData().get(i);
            Tooltip.install(item.getNode(), new Tooltip(item.getYValue().toString() + " days"));
        }

        return afB;
    }
}
