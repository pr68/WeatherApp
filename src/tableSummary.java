import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

//Build table of data for 2017
public class tableSummary {

    //initialise structure for where data will go
    private static ObservableList<stationData> data = FXCollections.observableArrayList();

    public static TableView<stationData> getSummaryTable() {

        //get data
        ArrayList<ArrayList<Double>> summary = recentSummary.recent();
        //get stations
        String[] stations = Main.stations;
        //initialise table object
        TableView<stationData> summaryT = new TableView<>();

        //Set coloumns
        TableColumn stationCol = new TableColumn("Station");
        stationCol.setMinWidth(200);
        stationCol.setCellValueFactory(
                new PropertyValueFactory<stationData, String>("station")
        );

        TableColumn maxCol = new TableColumn("Max Mean Temperature");
        maxCol.setMinWidth(200);
        maxCol.setCellValueFactory(
                new PropertyValueFactory<stationData, Double>("meanMax")
        );

        TableColumn minCol = new TableColumn("Min Mean Temperature");
        minCol.setMinWidth(200);
        minCol.setCellValueFactory(
                new PropertyValueFactory<stationData, Double>("meanMin")
        );

        TableColumn afCol = new TableColumn("Air Frost Days");
        afCol.setMinWidth(200);
        afCol.setCellValueFactory(
                new PropertyValueFactory<stationData, Double>("af")
        );

        TableColumn rainCol = new TableColumn("Total Rain");
        rainCol.setMinWidth(200);
        rainCol.setCellValueFactory(
                new PropertyValueFactory<stationData, Double>("rain")
        );

        //add data to table
        for (int i = 0; i < summary.size(); i++) {
            data.add(new stationData(stations[i], summary.get(i).get(0), summary.get(i).get(1), summary.get(i).get(2), summary.get(i).get(3)));
        }
        summaryT.setItems(data);
        summaryT.getColumns().addAll(stationCol, maxCol, minCol, afCol, rainCol);


        return summaryT;
    }
}

