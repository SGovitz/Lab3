import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TypeColor extends DefaultTableCellRenderer {
    private static final Map<String, Color> typeColors = new HashMap<>();

    static {
        typeColors.put("normal", Color.decode("#9FA19F"));
        typeColors.put("fighting", Color.decode("#FF8000"));
        typeColors.put("flying", Color.decode("#81B9EF"));
        typeColors.put("poison", Color.decode("#9141CB"));
        typeColors.put("ground", Color.decode("#915121"));
        typeColors.put("rock", Color.decode("#AFA981"));
        typeColors.put("bug", Color.decode("#91A119"));
        typeColors.put("ghost", Color.decode("#704170"));
        typeColors.put("steel", Color.decode("#60A1B8"));
        typeColors.put("fire", Color.decode("#E62829"));
        typeColors.put("water", Color.decode("#2980EF"));
        typeColors.put("grass", Color.decode("#3FA129"));
        typeColors.put("electric", Color.decode("#FAC000"));
        typeColors.put("psychic", Color.decode("#EF4179"));
        typeColors.put("ice", Color.decode("#3FD8FF"));
        typeColors.put("dragon", Color.decode("#5060E1"));
        typeColors.put("dark", Color.decode("#50413F"));
        typeColors.put("fairy", Color.decode("#EF70EF"));
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value != null && typeColors.containsKey(value.toString())) {
            cell.setBackground(typeColors.get(value.toString()));
            cell.setForeground(Color.WHITE);
        } else {
            cell.setBackground(Color.WHITE);
            cell.setForeground(Color.BLACK);
        }
        return cell;
    }
}