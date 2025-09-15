import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.ui.GradientPaintScale;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class DensityHeatmapPlot extends JFrame {

    public DensityHeatmapPlot(String title) {
        super(title);

        // Generate synthetic data (sales and cost)
        double[] sales = new double[1000];
        double[] cost = new double[1000];
        Random rand = new Random();

        for (int i = 0; i < sales.length; i++) {
            sales[i] = 10 + rand.nextDouble() * 25; // Sales: 10 to 35
            cost[i] = 5 + rand.nextDouble() * 25;   // Cost: 5 to 30
        }

        // Bin data into 2D grid
        int xBins = 30;
        int yBins = 30;
        double[][] grid = new double[xBins][yBins];
        double xMin = 10, xMax = 35;
        double yMin = 5, yMax = 30;
        double xStep = (xMax - xMin) / xBins;
        double yStep = (yMax - yMin) / yBins;

        for (int i = 0; i < sales.length; i++) {
            int xIndex = (int) ((sales[i] - xMin) / xStep);
            int yIndex = (int) ((cost[i] - yMin) / yStep);
            if (xIndex >= 0 && xIndex < xBins && yIndex >= 0 && yIndex < yBins) {
                grid[xIndex][yIndex]++;
            }
        }

        // Prepare dataset for XYBlockRenderer
        double[] xValues = new double[xBins * yBins];
        double[] yValues = new double[xBins * yBins];
        double[] zValues = new double[xBins * yBins];
        int index = 0;
        for (int i = 0; i < xBins; i++) {
            for (int j = 0; j < yBins; j++) {
                xValues[index] = xMin + i * xStep;
                yValues[index] = yMin + j * yStep;
                zValues[index] = grid[i][j];
                index++;
            }
        }

        DefaultXYZDataset dataset = new DefaultXYZDataset();
        dataset.addSeries("Density", new double[][]{xValues, yValues, zValues});

        // Create plot
        NumberAxis xAxis = new NumberAxis("Sales (binned)");
        NumberAxis yAxis = new NumberAxis("Cost (binned)");
        XYBlockRenderer renderer = new XYBlockRenderer();
        renderer.setBlockWidth(xStep);
        renderer.setBlockHeight(yStep);

        // Color scale
        PaintScale scale = new GradientPaintScale(1, 6, Color.WHITE, Color.BLUE);
        renderer.setPaintScale(scale);

        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        plot.setBackgroundPaint(Color.WHITE);

        JFreeChart chart = new JFreeChart("2D Density Plot (Sales vs Cost)", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DensityHeatmapPlot demo = new DensityHeatmapPlot("2D Density Plot");
            demo.setSize(800, 600);
            demo.setLocationRelativeTo(null);
            demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            demo.setVisible(true);
        });
    }
}
