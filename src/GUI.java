import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

public class GUI extends JFrame {

    // This panel will hold the three sub-panels on the right.
    private static JPanel rightPanel;

    public static void createAndShowGUI(List<Pokemon> pokemonList) {
        JFrame frame = new JFrame("Pokémon Data Visualization Tool");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 600);
        frame.setLayout(new BorderLayout());

        // Create the table model and JTable for displaying Pokémon data
        PokemonTable tableModel = new PokemonTable(pokemonList);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add sorting functionality and filters
        Sort.addSortingFunctionality(table);
        JComboBox<String> typeFilterBox = new JComboBox<>(getTypeOptions(pokemonList));
        String[] monoDualOptions = {"N/A", "Mono-Type", "Dual-Type"};
        JComboBox<String> monoDualFilterBox = new JComboBox<>(monoDualOptions);
        Sort.addCombinedTypeFilter(table, typeFilterBox, monoDualFilterBox);
        table.getColumnModel().getColumn(10).setCellRenderer(new TypeColor());
        table.getColumnModel().getColumn(11).setCellRenderer(new TypeColor());

        // Create control panel for filters and add to NORTH region
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Filter by Type:"));
        controlPanel.add(typeFilterBox);
        controlPanel.add(monoDualFilterBox);
        frame.add(controlPanel, BorderLayout.NORTH);

        // Add the table (data view) in the center
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create a container panel (rightPanel) for ChartPanel, StatsPanel, and DetailsPanel
        rightPanel = new JPanel(new GridLayout(3, 1));
        // Initially display the first Pokémon's data
        updateRightPanel(pokemonList.get(0));
        frame.add(rightPanel, BorderLayout.EAST);

        // Add a selection listener to update the rightPanel when a different Pokémon is clicked
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Assumes table row order matches the order in pokemonList.
                    Pokemon selectedPokemon = pokemonList.get(selectedRow);
                    updateRightPanel(selectedPokemon);
                }
            }
        });

        frame.setVisible(true);
    }

    // Utility method to extract stats as a Map from a Pokémon
    private static Map<String, Integer> getStatsForPokemon(Pokemon p) {
        return Map.of(
                "HP", p.getHp(),
                "Attack", p.getAttack(),
                "Defense", p.getDefense(),
                "Sp. Atk", p.getSAttack(),
                "Sp. Def", p.getSDefense(),
                "Speed", p.getSpeed()
        );
    }

    // Updates the rightPanel with ChartPanel, StatsPanel, and DetailsPanel for the given Pokémon.
    private static void updateRightPanel(Pokemon p) {
        rightPanel.removeAll();

        // Create a stats map from the Pokémon
        Map<String, Integer> stats = getStatsForPokemon(p);

        // Create each of the panels
        JPanel chartPanel = ChartPanel.createChartPanel(stats, p.getName());
        JPanel statsPanel = StatsPanel.createStatsPanel(stats, p.getName());
        JPanel detailsPanel = DetailsPanel.createDetailsPanel(p.getDetail(), p.getName());

        // Add panels to the rightPanel
        rightPanel.add(chartPanel);
        rightPanel.add(statsPanel);
        rightPanel.add(detailsPanel);

        rightPanel.revalidate();
        rightPanel.repaint();
    }

    // Generate unique type options for filtering
    private static String[] getTypeOptions(List<Pokemon> pokemonList) {
        Set<String> types = pokemonList.stream()
                .flatMap(pokemon -> Stream.of(pokemon.getType1(), pokemon.getType2()))
                .map(String::trim)
                .collect(Collectors.toSet());

        List<String> typeList = new ArrayList<>(types);
        Collections.sort(typeList); // Alphabetize types
        typeList.add(0, "All"); // "All" option
        return typeList.toArray(new String[0]);
    }
}
