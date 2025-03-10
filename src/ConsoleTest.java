import java.util.List;

public class ConsoleTest {
    public static void main(String[] args) {
        String filename = "data.csv";  // File "data" must be in the same folder as the code.
        List<SampleData> data = DataLoader.loadData(filename);
        if (data.isEmpty()) {
            System.out.println("No data loaded from " + filename);
            return;
        }
        System.out.println("First data entry: " + data.get(0));
        if (data.size() >= 10) {
            System.out.println("Tenth data entry: " + data.get(9));
        } else {
            System.out.println("Less than 10 data entries available.");
        }
        System.out.println("Total entries: " + data.size());
    }
}
