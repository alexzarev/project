
import java.util.List;

public class Train {
    private String name;
    private String startStation;
    private String endStation;
    private List<String> stops;

    public Train(String name, String startStation, String endStation, java.awt.List stops2) {
        this.name = name;
        this.startStation = startStation;
        this.endStation = endStation;
        this.stops = (List<String>) stops2;
    }

    public String getName() {
        return name;
    }

    public String getStartStation() {
        return startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public List<String> getStops() {
        return stops;
    }

    public void setStops(List<String> stops) {
        this.stops = stops;
    }

    @Override
    public String toString() {
        return name + ": " + stops.toString();
    }
}
