import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;

public class Sort {

    public static void addSortingFunctionality(JTable table) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        // Add sorting for numeric columns (ascending/descending)
        for (int i = 1; i < table.getColumnCount(); i++) {
            final int colIndex = i;
            table.getTableHeader().addMouseListener(new MouseAdapter() {
                boolean ascending = true;

                public void mouseClicked(MouseEvent e) {
                    sorter.setComparator(colIndex, (o1, o2) -> {
                        try {
                            return ascending
                                    ? Integer.compare(Integer.parseInt(o1.toString()), Integer.parseInt(o2.toString()))
                                    : Integer.compare(Integer.parseInt(o2.toString()), Integer.parseInt(o1.toString()));
                        } catch (NumberFormatException ex) {
                            return o1.toString().compareToIgnoreCase(o2.toString());
                        }
                    });
                    sorter.sort();
                    ascending = !ascending; // Toggle sort direction
                }
            });
        }
    }

    public static void addTypeFilter(JTable table, JComboBox<String> typeFilterBox) {
        TableRowSorter<TableModel> sorter = (TableRowSorter<TableModel>) table.getRowSorter();
        if (sorter == null) {
            sorter = new TableRowSorter<>(table.getModel());
            table.setRowSorter(sorter);
        }
        final TableRowSorter<TableModel> finalSorter = sorter; // Now effectively final
        typeFilterBox.addActionListener(e -> {
            String selectedType = (String) typeFilterBox.getSelectedItem();
            if ("All".equals(selectedType)) {
                finalSorter.setRowFilter(null); // Show all data
            } else {
                finalSorter.setRowFilter(RowFilter.regexFilter("(?i)" + selectedType));
            }
        });
    }

}
