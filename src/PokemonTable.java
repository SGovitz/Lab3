import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PokemonTable extends AbstractTableModel {
    private final String[] columnNames = {
            "Pokedex #", "Name", "Height", "Weight", "HP", "Attack", "Defense",
            "S. Attack", "S. Defense", "Speed", "Type", "Evo Set"
    };

    private final List<Pokemon> pokemonList;

    public PokemonTable(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    public int getRowCount() {
        return pokemonList.size();
    }
    public int getColumnCount() {
        return columnNames.length;
    }
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            // Columns: 0, 2, 3, 4, 5, 6, 7, 8, 9, and 11 are numeric.
            case 0, 2, 3, 4, 5, 6, 7, 8, 9, 11 -> Integer.class;
            // Columns: 1 (Name) and 10 (Type) are Strings.
            case 1, 10 -> String.class;
            default -> Object.class;
        };
    }

    public Object getValueAt(int row, int col) {
        Pokemon p = pokemonList.get(row);
        return switch (col) {
            case 0 -> p.getPokedex();
            case 1 -> p.getName();
            case 2 -> p.getHeight();
            case 3 -> p.getWeight();
            case 4 -> p.getHp();
            case 5 -> p.getAttack();
            case 6 -> p.getDefense();
            case 7 -> p.getSAttack();
            case 8 -> p.getSDefense();
            case 9 -> p.getSpeed();
            case 10 -> p.getType();
            case 11 -> p.getEvo();
            default -> null;
        };
    }

}
