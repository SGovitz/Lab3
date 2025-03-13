import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.util.*;

public class Sort {

    public static void addSortingFunctionality(JTable table) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        // Disable default header mouse listeners to avoid double-click issues.
        JTableHeader header = table.getTableHeader();
        for (MouseListener ml : header.getMouseListeners()) {
            header.removeMouseListener(ml);
        }

        header.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int viewColumn = table.columnAtPoint(e.getPoint());
                if (viewColumn < 0) return;
                int modelColumn = table.convertColumnIndexToModel(viewColumn);

                // Determine new sort order for the clicked column.
                List<RowSorter.SortKey> currentKeys = (List<RowSorter.SortKey>) sorter.getSortKeys();
                SortOrder newOrder;
                if (!currentKeys.isEmpty() && currentKeys.get(0).getColumn() == modelColumn) {
                    newOrder = (currentKeys.get(0).getSortOrder() == SortOrder.ASCENDING)
                            ? SortOrder.DESCENDING
                            : SortOrder.ASCENDING;
                } else {
                    newOrder = SortOrder.ASCENDING; // Always start with ascending
                }

                // Build new sort keys list
                List<RowSorter.SortKey> sortKeys = new ArrayList<>();
                sortKeys.add(new RowSorter.SortKey(modelColumn, newOrder));

                // For columns other than Pokedex (# at model index 0), add a secondary sort key on Pokedex ascending.
                if (modelColumn != 0) {
                    sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
                }
                sorter.setSortKeys(sortKeys);
                sorter.sort();
            }
        });
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
                finalSorter.setRowFilter(RowFilter.regexFilter("(?i)" + selectedType, 10));
            }
        });
    }

}
