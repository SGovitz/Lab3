import java.io.*;
import java.util.*;

public class DataLoader {
    /**
     * Loads data from a CSV file. The file is expected to have a header line.
     * Assumes columns: Name, Category, Metric1, Metric2.
     * The file must be named "data" and placed in the same folder as the code.
     */
    public static List<SampleData> loadData(String filename) {
        List<SampleData> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String header = br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String name = tokens[0].trim();
                String category = tokens[1].trim();
                int metric1 = Integer.parseInt(tokens[2].trim());
                double metric2 = Double.parseDouble(tokens[3].trim());
                list.add(new SampleData(name, category, metric1, metric2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
