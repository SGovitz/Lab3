import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI {

    public static void createAndShowGUI(List<Pokemon> pokemonList) {
        JFrame frame = new JFrame("Pokémon Data Visualization Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // Define column names matching the CSV fields (excluding id and info)
        String[] columnNames = {
                "Pokedex #", "Name", "Height", "Weight", "HP", "Attack",
                "Defense", "S. Attack", "S. Defense", "Speed", "Type", "Evo Set"
        };

        // Create a 2D array of objects from the list of Pokemon objects
        Object[][] data = pokemonList.stream()
                .map(Pokemon::toObjectArray)
                .toArray(Object[][]::new);

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
