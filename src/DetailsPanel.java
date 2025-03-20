import javax.swing.*;
import java.awt.*;

public class DetailsPanel {

    // This method creates a panel that shows the Pokémon description.
    public static JPanel createDetailsPanel(String description, String pokemonName) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(pokemonName + " Details"));

        // Use a JTextArea to display the description so that it wraps text and is scrollable.
        JTextArea textArea = new JTextArea(description);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
}
