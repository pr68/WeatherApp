import java.io.*;
import java.util.ArrayList;

//Produce report file
public class getReport {
    public static void download(File file) {

        //String to produce new line
        final String NEW_LINE_SEPARATOR = "\r\n";
        //Headings
        final String NUMBER = "Number: ";
        final String STATION = "Station: ";
        final String HIGHEST = "Highest: ";
        final String LOWEST ="Lowest: ";
        final String AF = "Average annual af: ";
        final String RAIN = "Average Annual rainfall: ";

        //Get list of stations
        String[] stations = Main.stations;

        try {
            String sep = System.getProperty("file.separator");
            Writer w = new FileWriter(new File(file.getAbsolutePath() + sep));
            //Go through each station in turn
            for (int i1 = 0; i1 < stations.length; i1++) {
                String station = stations[i1];

                //Set filePath of csv's
                String filePath = "./src/CMT205CWDATA/" + station + ".csv";
                String line = "";
                String cvsSplit = ",";
                String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

                //Initialise array list for data
                ArrayList<ArrayList<Double>> weather = new ArrayList<>();

                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

                    //Read through each line until line is empty
                    while ((line = br.readLine()) != null) {
                        //Split each line by comma
                        String[] mnth = line.split(cvsSplit);
                        //Put into an array list
                        ArrayList<Double> month = new ArrayList<>();
                        for (int i = 0; i < 6; i++) {
                            month.add(Double.parseDouble(mnth[i]));
                        }
                        //add to data array list
                        weather.add(month);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Initialise data needed for report
                ArrayList<String> stationReport = new ArrayList<>();
                stationReport.add(String.valueOf(i1 + 1));
                stationReport.add(station);

                //Find max mean max, and min mean min and when they occurred
                double tmax;
                tmax = 0;
                String tmaxDate;
                tmaxDate = null;
                double tmin;
                tmin = 20;
                String tminDate;
                tminDate = null;

                for (ArrayList<Double> aWeather : weather) {
                    if (aWeather.get(2) > tmax) {
                        tmax = aWeather.get(2);
                        tmaxDate = String.valueOf(aWeather.get(0).intValue()) + "-" + months[aWeather.get(1).intValue()-1];
                    }
                    if (aWeather.get(3) < tmin) {
                        tmin = aWeather.get(3);
                        tminDate = String.valueOf(aWeather.get(0).intValue()) + "-" + months[aWeather.get(1).intValue()-1];
                    }
                }

                //Add to array
                stationReport.add(tmaxDate + " " + String.valueOf(tmax));
                stationReport.add(tminDate + " " + String.valueOf(tmin));

                //Find average annual rainfall and air frost
                ArrayList<Double> frost = new ArrayList<>();
                ArrayList<Double> rain = new ArrayList<>();

                for (int i = 2009; i < 2018; i++) {
                    double af = 0;
                    double rn = 0;
                    for (ArrayList<Double> aWeather : weather) {
                        if (aWeather.get(0) < i) {
                            continue;
                        } else if (aWeather.get(0) == i) {
                            af += aWeather.get(4);
                            rn += aWeather.get(5);
                        } else {
                            frost.add(af);
                            rain.add(rn);

                        }
                    }
                }

                double sumAF = 0;
                for (Double aFrost : frost) {
                    sumAF += aFrost;
                }
                double aveFrost = sumAF / frost.size();

                double sumR = 0;
                for (Double aRain : rain) {
                    sumR += aRain;
                }
                double aveRain = sumR / rain.size();

                //Add to array
                stationReport.add(String.valueOf(aveFrost));
                stationReport.add(String.valueOf(aveRain));

                //Append to file
                w.append(NUMBER);
                w.append(stationReport.get(0));
                w.append(NEW_LINE_SEPARATOR);
                w.append(STATION);
                w.append(stationReport.get(1));
                w.append(NEW_LINE_SEPARATOR);
                w.append(HIGHEST);
                w.append(stationReport.get(2));
                w.append(NEW_LINE_SEPARATOR);
                w.append(LOWEST);
                w.append(stationReport.get(3));
                w.append(NEW_LINE_SEPARATOR);
                w.append(AF);
                w.append(stationReport.get(4));
                w.append(NEW_LINE_SEPARATOR);
                w.append(RAIN);
                w.append(stationReport.get(5));
                w.append(NEW_LINE_SEPARATOR);
                w.append(NEW_LINE_SEPARATOR);


            }
            //close file
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
