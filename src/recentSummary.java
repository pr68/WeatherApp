import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;

//data to be used in table for 2017
public class recentSummary {
    public static ArrayList<ArrayList<Double>> recent() {
        //initialise array for data to be used in table
        ArrayList<ArrayList<Double>> summary = new ArrayList<>();

        //get stations
        String[] stations = Main.stations;
        //loop through each station
        for (String station: stations) {

            //set file path
            String filePath = "./src/CMT205CWDATA/" + station + ".csv";
            String line = "";
            String cvsSplit = ",";
            ArrayList<ArrayList<Double>> weather = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

                //Read each line of csv until line is empty
                while ((line = br.readLine()) != null) {
                    //split line by comma
                    String[] mnth = line.split(cvsSplit);
                    //put data into an array list
                    ArrayList<Double> month = new ArrayList<>();
                    for (int i = 0; i < 6; i++) {
                        month.add(Double.parseDouble(mnth[i]));
                    }

                    weather.add(month);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            //initialise array for relevant data for each station
            ArrayList<Double> stationSummary = new ArrayList<>();
            stationSummary.add(0.0);
            stationSummary.add(20.0);

            double af = 0;
            double rain = 0;

            //Go through the lines of the data that pertain to 2017 (Row 96 to end)
            for (int i = 96; i < weather.size(); i++) {
                //Get total rainfall and air frost
                af += weather.get(i).get(4);
                rain += weather.get(i).get(5);
                //Get max mean max and min mean min
                if (weather.get(i).get(2) > stationSummary.get(0)) {
                    stationSummary.set(0, weather.get(i).get(2));
                } else {
                    continue;
                }

                if (weather.get(i).get(3) < stationSummary.get(1)) {
                    stationSummary.set(1, weather.get(i).get(3));
                } else {
                    continue;
                }


            }
            //Round af and rain to 1dp
            af = Math.round(af * 10.0) /10.0;
            rain = Math.round(rain * 10.0) / 10.0;
            stationSummary.add(af);
            stationSummary.add(rain);
            summary.add(stationSummary);

        }
        return summary;
    }

}
