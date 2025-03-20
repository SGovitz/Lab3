import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.util.Map;

public class ChartPanel {

    public static JPanel createChartPanel(Map<String, Integer> stats, String pokemonName) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Calculate total stat sum
        int totalStats = stats.values().stream().mapToInt(Integer::intValue).sum();

        // Populate the dataset with percentages
        stats.forEach((stat, value) -> {
            double percentage = (value / (double) totalStats) * 100;
            dataset.setValue(stat + " (" + String.format("%.1f", percentage) + "%)", percentage);
        });

        // Create chart
        JFreeChart pieChart = ChartFactory.createPieChart(
                pokemonName + " Stat Distribution",
                dataset,
                true,  // Legend
                true,  // Tooltips
                false  // URLs
        );

        return new org.jfree.chart.ChartPanel(pieChart);
    }
}
