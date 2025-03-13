import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class PokemonSort {

    public static void main(String[] args) {
        List<Pokemon> pokemonList = loadPokemonData("srcpokedex.csv");

        if (pokemonList.isEmpty()) {
            System.out.println("No data loaded. Check the file path or format.");
            return;
        }

        // Console Output for Part One
        System.out.println("First Pokemon: " + pokemonList.get(0));
        System.out.println("Tenth Pokemon: " + (pokemonList.size() >= 10 ? pokemonList.get(9) : "Less than 10 entries"));
        System.out.println("Total Entries: " + pokemonList.size());

        // GUI Setup for Part Two
        javax.swing.SwingUtilities.invokeLater(() -> GUI.createAndShowGUI(pokemonList));
    }

    private static List<Pokemon> loadPokemonData(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath)).skip(1)) {
            // Split on commas while respecting quoted fields
            return lines.map(line -> line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
                    .map(Pokemon::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
