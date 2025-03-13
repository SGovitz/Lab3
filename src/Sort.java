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

    public static void addCombinedTypeFilter(JTable table, JComboBox<String> typeFilterBox, JComboBox<String> monoDualFilterBox) {
        TableRowSorter<TableModel> sorter = (TableRowSorter<TableModel>) table.getRowSorter();
        if (sorter == null) {
            sorter = new TableRowSorter<>(table.getModel());
            table.setRowSorter(sorter);
        }
        final TableRowSorter<TableModel> finalSorter = sorter;

        ActionListener updateFilter = e -> {
            String selectedType = (String) typeFilterBox.getSelectedItem();
            String monoDualSelected = (String) monoDualFilterBox.getSelectedItem();

            List<RowFilter<Object, Object>> filters = new ArrayList<>();

            // Filter by specific type (if not "All")
            if (!"All".equals(selectedType)) {
                filters.add(RowFilter.regexFilter("(?i)" + selectedType, 10));
            }

            // Custom filter for Mono-Type or Dual-Type.
            if (!"N/A".equals(monoDualSelected)) {
                RowFilter<Object, Object> monoDualFilter = new RowFilter<>() {
                    @Override
                    public boolean include(Entry<? extends Object, ? extends Object> entry) {
                        String typeString = entry.getStringValue(10);
                        String[] types = typeString.split(",");
                        int count = 0;
                        for (String s : types) {
                            if (!s.trim().isEmpty()) {
                                count++;
                            }
                        }
                        if ("Mono-Type".equals(monoDualSelected)) {
                            return count == 1;
                        } else if ("Dual-Type".equals(monoDualSelected)) {
                            return count == 2;
                        }
                        return true;
                    }
                };
                filters.add(monoDualFilter);
            }

            if (filters.isEmpty()) {
                finalSorter.setRowFilter(null);
            } else {
                finalSorter.setRowFilter(RowFilter.andFilter(filters));
            }
        };

        typeFilterBox.addActionListener(updateFilter);
        monoDualFilterBox.addActionListener(updateFilter);
    }

}
