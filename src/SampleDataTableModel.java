import javax.swing.table.AbstractTableModel;
import java.util.List;

public class SampleDataTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Name", "Category", "Metric1", "Metric2"};
    private List<SampleData> data;

    public SampleDataTableModel(List<SampleData> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SampleData sd = data.get(rowIndex);
        switch (columnIndex) {
            case 0: return sd.getName();
            case 1: return sd.getCategory();
            case 2: return sd.getMetric1();
            case 3: return sd.getMetric2();
            default: return null;
        }
    }

    public SampleData getSampleDataAt(int rowIndex) {
        return data.get(rowIndex);
    }

    public void setData(List<SampleData> data) {
        this.data = data;
        fireTableDataChanged();
    }
}
