import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//data object for historical weather
public class stationHistory {
    public static ArrayList<ArrayList<Double>> history(String station) {
        //initialise array list for data
        ArrayList<ArrayList<Double>> historicalWeather = new ArrayList<>();

        //set filepath
        String filePath = "./src/CMT205CWDATA/" + station + ".csv";
        String line = "";
        String cvsSplit = ",";

        ArrayList<ArrayList<Double>> weather = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))){

            //read through each line until line is empty
            while ((line = br.readLine()) != null) {
                String[] mnth = line.split(cvsSplit);
                //Put data in an array list
                ArrayList<Double> month = new ArrayList<>();
                for (int i = 0; i < 6; i++) {
                    month.add(Double.parseDouble(mnth[i]));
                }
                weather.add(month);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Go through each year
        for (int i = 2009; i < 2018; i++) {
            double tmax = 0;
            double tmin = 20;
            double af = 0;
            double rain = 0;
            for (int j = 0; j < weather.size(); j++) {
                if (weather.get(j).get(0) < i) {
                    continue;
                } else if (weather.get(j).get(0) == i) {
                    //Get max mean max and min mean min
                    if (weather.get(j).get(2) > tmax) {
                        tmax = weather.get(j).get(2);
                    } else {
                        continue;
                    }
                    if (weather.get(j).get(3) < tmin) {
                        tmin = weather.get(j).get(3);
                    } else {
                        continue;
                    }
                    //get total air frost and rainfall
                    af += weather.get(j).get(4);
                    rain += weather.get(j).get(5);
                } else {
                    //build data for year
                    ArrayList<Double> year = new ArrayList<>();
                    year.add((double) i);
                    year.add(tmax);
                    year.add(tmin);
                    year.add(af);
                    year.add(rain);
                    //add to array list
                    historicalWeather.add(year);

                }
            }
        }

        return historicalWeather;
    }
}
