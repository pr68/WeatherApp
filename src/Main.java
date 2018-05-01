import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    //List of stations
    public static String[] stations = new String[]{"Aberporth", "Armagh", "Ballypatrick Forest", "Bradford", "Braemar", "Camborne", "Cambridge NIAB", "Cardiff Bute Park", "Chivenor", "Dunstaffnage", "Durham", "Eastbourne", "Eskdalemuir", "Heathrow", "Hurn", "Lerwick", "Leuchars", "Lowestoft", "Manston", "Nairn", "Newton Rigg", "Oxford", "Paisley", "Ross-on-Wye", "Shawbury", "Sheffield", "Stornoway Airport", "Sutton Bonington", "Tiree", "Valley", "Waddington", "Whitby", "Wick Airport", "Yeovilton"};

    //Initialling dropdown
    public static ComboBox<String> stationChooser = new ComboBox<>();


    @Override
    public void start(Stage primaryStage) {

        //Main Page of App
        primaryStage.setTitle("Weather App");

        Label pageTitle = new Label("Weather App");
        pageTitle.getStyleClass().add("title");

        Button recentButton = new Button("View All Station Data for 2017");
        recentButton.setOnAction(event -> viewRecent());
        recentButton.getStyleClass().add("button");

        Button stationButton = new Button("View Historical Data for Station");
        stationButton.setOnAction(event -> viewStation());
        recentButton.getStyleClass().add("button");

        Button reportButton = new Button("Generate Report");

        //Generating report to a txt file
        reportButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Report");
            //Set file to save with .txt extension
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text File", "*.txt")
            );
            //Default file name
            fileChooser.setInitialFileName("WeatherData");
            //Bring up save dialog
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                //Generate report file from getReport class
                getReport.download(file);
                Stage popup = new Stage();
                Label pMessage = new Label("Download Successful!");
                pMessage.getStyleClass().add("title");
                Button closeBut = new Button("Return");
                closeBut.getStyleClass().add("button");
                closeBut.setOnAction(event1 -> popup.close());
                VBox pop = new VBox();
                pop.setAlignment(Pos.CENTER);
                pop.getChildren().addAll(pMessage, closeBut);
                BorderPane borderPane = new BorderPane();
                borderPane.setCenter(pop);
                borderPane.setPadding(new Insets(20));
                borderPane.getStyleClass().add("main");
                Scene scene = new Scene(borderPane);
                scene.getStylesheets().add("style.css");
                popup.setScene(scene);
                popup.show();
            }
        });
        recentButton.getStyleClass().add("button");

        //Layout
        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.CENTER);
        titleBar.getChildren().add(pageTitle);

        VBox functionButtons = new VBox(10);
        functionButtons.setAlignment(Pos.CENTER);
        functionButtons.getChildren().addAll(recentButton,stationButton, reportButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(titleBar);
        borderPane.setCenter(functionButtons);
        borderPane.setPadding(new Insets(20));

        borderPane.getStyleClass().add("main");

        Scene scene = new Scene(borderPane, 600, 600);

        //add stylesheet
        scene.getStylesheets().add("style.css");

        primaryStage.setScene(scene);
        primaryStage.show();

        //Bring up pop up on close of program to confirm close request
        scene.getWindow().setOnCloseRequest(event -> {
            event.consume();
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            Label message = new Label("Are you Sure?");

            Button yes = new Button("Yes");
            Button no = new Button("No");
            yes.setOnAction(event1 -> {
                window.close();
                Platform.exit();

            });
            no.setOnAction(event1 -> window.close());

            HBox title = new HBox();
            title.setAlignment(Pos.CENTER);
            title.getChildren().add(message);

            HBox buttons = new HBox();
            buttons.setAlignment(Pos.CENTER);
            buttons.getChildren().addAll(yes, no);

            BorderPane borderPane1 = new BorderPane();
            borderPane1.setTop(title);
            borderPane1.setBottom(buttons);
            borderPane1.setPadding(new Insets(10));

            Scene scene1 = new Scene(borderPane1);
            window.setScene(scene1);
            window.show();
        });
    }

    //Page for 2017 data
    private void viewRecent() {
        Stage window = new Stage();
        window.setTitle("2017 Summary");
        //Get Table object
        TableView summaryT = tableSummary.getSummaryTable();
        summaryT.getStyleClass().add("table");

        Label title = new Label("2017 Summary");
        title.getStyleClass().add("title");

        Button closeButton = new Button("Return");
        closeButton.setOnAction(event -> window.close());
        closeButton.getStyleClass().add("button");

        //Layout
        HBox titleBar = new HBox(10);
        titleBar.setAlignment(Pos.CENTER);
        titleBar.getChildren().add(title);

        VBox table = new VBox(10);
        table.setAlignment(Pos.CENTER);
        table.getChildren().add(summaryT);

        HBox button = new HBox(10);
        button.setAlignment(Pos.BOTTOM_RIGHT);
        button.getChildren().add(closeButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(titleBar);
        borderPane.setCenter(table);
        borderPane.setBottom(button);
        borderPane.setPadding(new Insets(20));
        borderPane.getStyleClass().add("main");

        Scene scene = new Scene(borderPane, 1052, 800);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.show();
    }

    //Page for viewing data for a specific station
    private void viewStation() {
        Stage window = new Stage();

        //Add dropdown populated with stations
        stationChooser.getItems().addAll(stations);
        stationChooser.setValue("Bradford");
        stationChooser.getStyleClass().add("combo");

        //Set title to be the default station
        window.setTitle(stationChooser.getValue());

        Label title = new Label();
        title.setText(stationChooser.getValue());
        title.getStyleClass().add("title");


        Button closeButton = new Button("Return");
        closeButton.setOnAction(event -> window.close());
        closeButton.getStyleClass().add("button");

        //Get chart objects
        LineChart lc = historyLine.tempLine();
        lc.getStyleClass().add("graphs");
        BarChart afB = afBar.getAfBar();
        afB.getStyleClass().add("graphs");
        BarChart rainB = rainBar.getRainBar();
        rainB.getStyleClass().add("graphs");

        //Layout
        HBox titleBar = new HBox(10);
        title.setAlignment(Pos.CENTER);
        titleBar.getChildren().addAll(title, stationChooser);

        HBox graphs = new HBox(10);
        graphs.setAlignment(Pos.CENTER);
        graphs.getChildren().addAll(lc, afB, rainB);


        HBox button = new HBox();
        button.setAlignment(Pos.BOTTOM_RIGHT);
        button.getChildren().add(closeButton);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(titleBar);
        borderPane.setCenter(graphs);
        borderPane.setBottom(button);
        borderPane.setPadding(new Insets(20));
        borderPane.getStyleClass().add("main");

        Scene scene = new Scene(borderPane, 1400, 800);
        scene.getStylesheets().add("style.css");
        window.setScene(scene);
        window.show();

        //Change data in charts when dropdown value is changed
        stationChooser.valueProperty().addListener((observable, oldValue, newValue) -> {
            //clear current data from charts
            lc.getData().clear();
            afB.getData().clear();
            rainB.getData().clear();
            //Set titles to new Station
            window.setTitle(newValue);
            title.setText(newValue);
            //Read in data for new station
            ArrayList<ArrayList<Double>> historicalWeather = stationHistory.history(newValue);

            //Set data for new staions in charts
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
            XYChart.Series seriesaf = new XYChart.Series();
            for (ArrayList<Double> aHistoricalWeather : historicalWeather) {
                seriesaf.getData().add(new XYChart.Data(String.valueOf(aHistoricalWeather.get(0)), aHistoricalWeather.get(3)));
            }

            afB.getData().add(seriesaf);
            XYChart.Series series = new XYChart.Series();
            for (ArrayList<Double> aHistoricalWeather : historicalWeather) {
                series.getData().add(new XYChart.Data(String.valueOf(aHistoricalWeather.get(0)), aHistoricalWeather.get(4)));
            }

            rainB.getData().add(series);
        });


    }

}
