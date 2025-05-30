
import java.io.*;
import java.util.*;

public class TrainManager {
    private List<Train> trains;

    public TrainManager() {
        trains = new ArrayList<>();
    }

    public void addTrain(Train train) {
        for (Train t : trains) {
            if (t.getName().equals(train.getName())) return;
        }
        trains.add(train);
    }

    public void removeTrain(String name) {
        trains.removeIf(t -> t.getName().equals(name));
    }

    public List<Train> getTrains() {
        return trains;
    }

    public Train getTrainByName(String name) {
        for (Train t : trains) {
            if (t.getName().equals(name)) return t;
        }
        return null;
    }

    public List<Train> findTrainsByStop(String stop) {
        List<Train> result = new ArrayList<>();
        for (Train t : trains) {
            if (t.getStops().contains(stop)) result.add(t);
        }
        return result;
    }

    public List<String> findRoute(String start, String end) {
        for (Train t : trains) {
            List<String> stops = t.getStops();
            if (stops.contains(start) && stops.contains(end) && stops.indexOf(start) < stops.indexOf(end)) {
                return Arrays.asList(t.getName());
            }
        }
        for (Train t1 : trains) {
            for (Train t2 : trains) {
                if (t1 == t2) continue;
                for (String mid : t1.getStops()) {
                    if (t1.getStops().contains(start) && t2.getStops().contains(end) &&
                        t1.getStops().indexOf(start) < t1.getStops().indexOf(mid) &&
                        t2.getStops().contains(mid) && t2.getStops().indexOf(mid) < t2.getStops().indexOf(end)) {
                        return Arrays.asList(t1.getName(), t2.getName());
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    public void sortByStopCount() {
        trains.sort(Comparator.comparingInt(t -> t.getStops().size()));
    }

    public void sortByStartStation() {
        for (int i = 1; i < trains.size(); i++) {
            Train key = trains.get(i);
            int j = i - 1;
            while (j >= 0 && trains.get(j).getStartStation().compareTo(key.getStartStation()) > 0) {
                trains.set(j + 1, trains.get(j));
                j--;
            }
            trains.set(j + 1, key);
        }
    }

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Train t : trains) {
                writer.write(t.getName() + ";" + t.getStartStation() + ";" + t.getEndStation() + ";" + String.join(",", t.getStops()));
                writer.newLine();
            }
        }
    }

    public void loadFromFile(String filename) throws IOException {
        trains.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String name = parts[0];
                String start = parts[1];
                String end = parts[2];
                List stops = Arrays.asList(parts[3].split(","));
                trains.add(new Train(name, start, end, (java.awt.List) stops));
            }
        }
    }
}
