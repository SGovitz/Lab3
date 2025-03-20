import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class StatsPanel {

    public static JPanel createStatsPanel(Map<String, Integer> stats, String pokemonName) {
        // Create a panel with a 3x2 grid layout for 6 stats.
        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.setBorder(BorderFactory.createTitledBorder(pokemonName + " Stats"));

        // Iterate over each stat entry and add a label.
        stats.forEach((stat, value) -> {
            panel.add(new JLabel(stat + ": " + value));
        });

        return panel;
    }
}
