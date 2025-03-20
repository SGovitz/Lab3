import javax.swing.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class PokemonMain {

    public static void main(String[] args) {
        List<Pokemon> pokemonList = loadPokemonData("src\\pokedex.csv");

        if (pokemonList.isEmpty()) {
            System.out.println("No data loaded. Check the file path or format.");
            return;
        }

        // Console Output for Part One
        System.out.println("First Pokemon: " + pokemonList.get(0));
        System.out.println("Tenth Pokemon: " + (pokemonList.size() >= 10 ? pokemonList.get(9) : "Less than 10 entries"));
        System.out.println("Total Entries: " + pokemonList.size());

        SwingUtilities.invokeLater(() -> GUI.createAndShowGUI(pokemonList));
    }

    public static List<Pokemon> loadPokemonData(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath)).skip(1)) {
            return lines.map(line -> line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
                    .map(fields -> Arrays.stream(fields)
                            .map(field -> field.replaceAll("^\"|\"$", ""))
                            .toArray(String[]::new))
                    .map(Pokemon::new)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
