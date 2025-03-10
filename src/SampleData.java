public class SampleData {
    private String name;
    private String category;
    private int metric1;
    private double metric2;

    public SampleData(String name, String category, int metric1, double metric2) {
        this.name = name;
        this.category = category;
        this.metric1 = metric1;
        this.metric2 = metric2;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getMetric1() { return metric1; }
    public double getMetric2() { return metric2; }

    @Override
    public String toString() {
        return "SampleData{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", metric1=" + metric1 +
                ", metric2=" + metric2 +
                '}';
    }
}
