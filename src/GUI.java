import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

public class GUI extends JFrame {
    public static void createAndShowGUI(List<Pokemon> pokemonList) {
        JFrame frame = new JFrame("Pokémon Data Visualization Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        PokemonTable tableModel = new PokemonTable(pokemonList);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add sorting functionality
        Sort.addSortingFunctionality(table);

        // Add Type filter dropdown
        JComboBox<String> typeFilterBox = new JComboBox<>(getTypeOptions(pokemonList));
        Sort.addTypeFilter(table, typeFilterBox);

        // Layout
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Filter by Type:"));
        controlPanel.add(typeFilterBox);

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // Generate unique type options for filtering
    private static String[] getTypeOptions(List<Pokemon> pokemonList) {
        Set<String> types = pokemonList.stream()
                .flatMap(pokemon -> Arrays.stream(pokemon.getType().split(",")))
                .map(String::trim)
                .collect(Collectors.toSet());

        List<String> typeList = new ArrayList<>(types);
        Collections.sort(typeList); // Alphabetize types
        typeList.add(0, "All"); // Add "All" option for no filter
        return typeList.toArray(new String[0]);
    }
}
