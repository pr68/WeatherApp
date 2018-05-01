//data structure for table
public class stationData {
    private String station;
    private Double meanMax;
    private Double meanMin;
    private Double af;
    private Double rain;

    //constructor
    public stationData(String station, Double meanMax, Double meanMin, Double af, Double rain) {
        this.station = station;
        this.meanMax = meanMax;
        this.meanMin = meanMin;
        this.af = af;
        this.rain = rain;
    }

    //setters and getters
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Double getMeanMax() {
        return meanMax;
    }

    public void setMeanMax(Double meanMax) {
        this.meanMax = meanMax;
    }

    public Double getMeanMin() {
        return meanMin;
    }

    public void setMeanMin(Double meanMin) {
        this.meanMin = meanMin;
    }

    public Double getAf() {
        return af;
    }

    public void setAf(Double af) {
        this.af = af;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }
}
