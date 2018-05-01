import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

import java.util.ArrayList;

//Build bar chart for rainfall
public class rainBar {
    public static BarChart<String,Number> getRainBar() {
        //get data
        ArrayList<ArrayList<Double>> historicalWeather = stationHistory.history(Main.stationChooser.getValue());

        //set axis
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        //initialise barchart object
        BarChart<String,Number> rainB = new BarChart<>(xAxis,yAxis);

        rainB.setTitle("Total Rain per Year");
        rainB.setLegendVisible(false);
        xAxis.setLabel("Year");
        yAxis.setLabel("Total Rain (ml)");

        rainB.setBarGap(5);

        //Add data to chart
        XYChart.Series series = new XYChart.Series();
        for (ArrayList<Double> aHistoricalWeather : historicalWeather) {
            series.getData().add(new XYChart.Data(String.valueOf(aHistoricalWeather.get(0)), aHistoricalWeather.get(4)));
        }

        rainB.getData().add(series);

        //Add tooltip on hover
        for (int i = 0; i < historicalWeather.size(); i++) {
            XYChart.Data item = (XYChart.Data)series.getData().get(i);
            Tooltip.install(item.getNode(), new Tooltip(item.getYValue().toString() + "ml"));
        }

        return rainB;
    }
}
