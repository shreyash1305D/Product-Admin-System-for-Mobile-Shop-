import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.Arrays;
import java.util.Random;

public class HistogramDensityPlot extends JFrame {

    public HistogramDensityPlot(String title) {
        super(title);

        // Generate Gaussian data
        double[] data = new double[1000];
        Random random = new Random();
        for (int i = 0; i < data.length; i++) {
            data[i] = random.nextGaussian() * 10 + 50;  // μ = 50, σ = 10
        }

        // Histogram dataset
        HistogramDataset histogramDataset = new HistogramDataset();
        histogramDataset.setType(HistogramType.RELATIVE_FREQUENCY); // Normalize
        histogramDataset.addSeries("Histogram", data, 30); // 30 bins

        // Create histogram chart
        JFreeChart chart = ChartFactory.createHistogram(
            "Histogram with Density Plot",
            "Value",
            "Relative Frequency",
            histogramDataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        // Get the plot object
        XYPlot plot = chart.getXYPlot();
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

        // Create density series using KDE
        XYSeries densitySeries = new XYSeries("Density");
        double bandwidth = 5.0;
        double min = Arrays.stream(data).min().orElse(0);
        double max = Arrays.stream(data).max().orElse(100);
        double step = (max - min) / 100;

        for (double x = min; x <= max; x += step) {
            double density = 0;
            for (double xi : data) {
                density += gaussianKernel(x, xi, bandwidth);
            }
            density /= (data.length * bandwidth);
            densitySeries.add(x, density);
        }

        // Add density dataset
        XYSeriesCollection densityDataset = new XYSeriesCollection();
        densityDataset.addSeries(densitySeries);
        plot.setDataset(1, densityDataset);
        plot.setRenderer(1, new StandardXYItemRenderer());

        // Adjust axes
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setRange(min - 10, max + 10);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);

        // Display
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    // Gaussian kernel function
    private double gaussianKernel(double x, double xi, double bandwidth) {
        double exponent = -Math.pow(x - xi, 2) / (2 * Math.pow(bandwidth, 2));
        return (1 / (Math.sqrt(2 * Math.PI) * bandwidth)) * Math.exp(exponent);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HistogramDensityPlot example = new HistogramDensityPlot("Histogram + Density Plot");
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}