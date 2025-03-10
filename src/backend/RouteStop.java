package backend;

import java.io.Serializable;

public class RouteStop implements Serializable {
    private static final long serialVersionUID = 6048325275258574928L;
    private String stopName;
    private String stopAddress;
    private String stopTime;
    private String stopDuration;
    public RouteStop(String stopName, String stopAddress, String stopTime,  String stopDuration) {
        this.stopName = stopName;
        this.stopAddress = stopAddress;
        this.stopTime = stopTime;
        this.stopDuration = stopDuration;
    }
    public String getStopName() {
        return stopName;
    }
    public String getStopTime() {
        return stopTime;
    }
    public String getStopDuration() {
        return stopDuration;
    }
    public String getStopAddress() {
        return stopAddress;
    }
    @Override
    public String toString() {
        return stopName + ", " + stopAddress + " <br>"+stopTime+", stopDuration="+stopDuration;
    }
}
