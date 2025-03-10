import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.*;

public class DataViewerGUI extends JFrame {
    private List<SampleData> allData;
    private SampleDataTableModel tableModel;
    private JTable table;
    private JTextArea detailsArea;
    private JLabel statsLabel;

    // Filter components
    private JComboBox<String> categoryFilter;
    private JTextField minMetric1Filter;  // This filter applies to Metric1 (e.g., minimum value)
    private JTextField maxMetric2Filter;  // This filter applies to Metric2 (e.g., maximum value)
    private JButton applyFilterButton;
    private JButton clearFilterButton;

    public DataViewerGUI(List<SampleData> data) {
        super("Sample Data Viewer");
        this.allData = data;
        initComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,600);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Table Panel
        tableModel = new SampleDataTableModel(allData);
        table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Details Panel
        detailsArea = new JTextArea(5, 50);
        detailsArea.setEditable(false);
        JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
        detailsScrollPane.setBorder(BorderFactory.createTitledBorder("Details Panel"));

        // Stats Panel
        statsLabel = new JLabel();
        JPanel statsPanel = new JPanel();
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        statsPanel.add(statsLabel);
        updateStats(allData);

        // Chart Panel (simple bar chart for top 5 by Metric1)
        JPanel chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                List<SampleData> top5 = allData.stream()
                        .sorted((a, b) -> Integer.compare(b.getMetric1(), a.getMetric1()))
                        .limit(5)
                        .collect(Collectors.toList());
                int panelWidth = getWidth();
                int barWidth = panelWidth / top5.size();
                int maxMetric1 = top5.stream().mapToInt(SampleData::getMetric1).max().orElse(1);
                for (int i = 0; i < top5.size(); i++) {
                    int barHeight = (int)(((double) top5.get(i).getMetric1() / maxMetric1) * (getHeight()-30));
                    g.setColor(Color.GREEN);
                    g.fillRect(i * barWidth + 10, getHeight()-barHeight-20, barWidth-20, barHeight);
                    g.setColor(Color.BLACK);
                    g.drawString(top5.get(i).getName(), i * barWidth + 10, getHeight()-5);
                }
            }
        };
        chartPanel.setPreferredSize(new Dimension(400,300));
        chartPanel.setBorder(BorderFactory.createTitledBorder("Chart Panel"));

        // Filter Panel with three filters: Category, Min Metric1, and Max Metric2.
        JPanel filterPanel = new JPanel(new FlowLayout());
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filters"));

        categoryFilter = new JComboBox<>();
        categoryFilter.addItem("All Categories");
        allData.stream()
                .map(SampleData::getCategory)
                .distinct()
                .forEach(categoryFilter::addItem);

        minMetric1Filter = new JTextField(8);
        minMetric1Filter.setToolTipText("Enter minimum value for Metric1");

        maxMetric2Filter = new JTextField(8);
        maxMetric2Filter.setToolTipText("Enter maximum value for Metric2");

        applyFilterButton = new JButton("Apply Filters");
        clearFilterButton = new JButton("Clear Filters");

        // Note the updated label text to clearly indicate which metric each filter applies to.
        filterPanel.add(new JLabel("Category:"));
        filterPanel.add(categoryFilter);
        filterPanel.add(new JLabel("Min Metric1:"));
        filterPanel.add(minMetric1Filter);
        filterPanel.add(new JLabel("Max Metric2:"));
        filterPanel.add(maxMetric2Filter);
        filterPanel.add(applyFilterButton);
        filterPanel.add(clearFilterButton);

        applyFilterButton.addActionListener(e -> applyFilters());
        clearFilterButton.addActionListener(e -> clearFilters());

        // Left side panels: Filters, Table, Details.
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(filterPanel, BorderLayout.NORTH);
        leftPanel.add(tableScrollPane, BorderLayout.CENTER);
        leftPanel.add(detailsScrollPane, BorderLayout.SOUTH);

        // Right side panels: Statistics and Chart.
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(statsPanel, BorderLayout.NORTH);
        rightPanel.add(chartPanel, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(500);
        add(splitPane, BorderLayout.CENTER);

        // Update the details panel when a row is selected.
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int modelRow = table.convertRowIndexToModel(selectedRow);
                    SampleData sd = tableModel.getSampleDataAt(modelRow);
                    detailsArea.setText(sd.toString());
                }
            }
        });
    }

    private void updateStats(List<SampleData> data) {
        double avgMetric1 = data.stream().mapToInt(SampleData::getMetric1).average().orElse(0);
        double avgMetric2 = data.stream().mapToDouble(SampleData::getMetric2).average().orElse(0);
        int total = data.size();
        statsLabel.setText(String.format("<html>Average Metric1: %.2f<br>Average Metric2: %.2f<br>Total Items: %d</html>",
                avgMetric1, avgMetric2, total));
    }

    private void applyFilters() {
        String selectedCategory = (String) categoryFilter.getSelectedItem();
        String minMetric1Text = minMetric1Filter.getText().trim();
        String maxMetric2Text = maxMetric2Filter.getText().trim();

        final int minMetric1Final;
        final double maxMetric2Final;
        try {
            if (!minMetric1Text.isEmpty()) {
                minMetric1Final = Integer.parseInt(minMetric1Text);
            } else {
                minMetric1Final = 0;
            }
            if (!maxMetric2Text.isEmpty()) {
                maxMetric2Final = Double.parseDouble(maxMetric2Text);
            } else {
                maxMetric2Final = Double.MAX_VALUE;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid filter values");
            return;
        }

        List<SampleData> filtered = allData.stream()
                .filter(sd -> selectedCategory.equals("All Categories") || sd.getCategory().equals(selectedCategory))
                .filter(sd -> sd.getMetric1() >= minMetric1Final)
                .filter(sd -> sd.getMetric2() <= maxMetric2Final)
                .collect(Collectors.toList());
        tableModel.setData(filtered);
        updateStats(filtered);
    }


    private void clearFilters() {
        categoryFilter.setSelectedIndex(0);
        minMetric1Filter.setText("");
        maxMetric2Filter.setText("");
        tableModel.setData(allData);
        updateStats(allData);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String filename = "data";  // The file named "data" must be in the same folder as the code.
            List<SampleData> data = DataLoader.loadData(filename);
            if (data.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No data loaded from " + filename);
                return;
            }
            new DataViewerGUI(data).setVisible(true);
        });
    }
}
