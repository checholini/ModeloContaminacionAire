/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chart;

import org.jfree.chart.ChartFactory;
 import org.jfree.chart.ChartPanel;
 import org.jfree.chart.JFreeChart;
 import org.jfree.chart.plot.PlotOrientation;
 import org.jfree.data.xy.XYSeries;
 import org.jfree.data.xy.XYSeriesCollection;
 import org.jfree.ui.ApplicationFrame;
 import org.jfree.ui.RefineryUtilities;


public class XYSeriesDemo extends ApplicationFrame {

/**
 * A demonstration application showing an XY series containing a null value.
 *
 * @param title  the frame title.
 */
    public XYSeriesDemo(final String title) {
        super(title);
        double contaminacion = 0; 
        final XYSeries series = new XYSeries("Contaminacion");
        for (double i = 0; i < 10; i+=0.3) {
            contaminacion = calcularValores(i);
            System.out.println(contaminacion);
            series.add(i, contaminacion);
        }
        final XYSeriesCollection data = new XYSeriesCollection(series);
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Contaminacion del aire", 
            "Tiempo", 
            "Valor Contaminacion",
            data,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 450));
        setContentPane(chartPanel);

    }


/**
 * Starting point for the demonstration application.
 *
 * @param args  ignored.
 */
    public static void main(final String[] args) {

        final XYSeriesDemo demo = new XYSeriesDemo("Modelo Gauseano");
        final XYSeriesDemo demo1 = new XYSeriesDemo("Modelo Gauseano");
        demo.pack();
        demo1.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        demo1.setVisible(true);

    }

    private double calcularValores(double i) {
        int k = 2000;//Constante de dispersion turbulenta
        int x = 1000;//Distancia Viento x
        int y = 1000;//Distancia Viento y
        int u = 10;//velocidad del viento x
        int v = 10;//velocidad del viento y
        int h = 4000;//Altura
        int masa = 1200000000;//Masa del contaminante
        double dispercionX = 2*k*x;
        dispercionX /= u;
        double dispercionY = 2*k*y;
        dispercionY /= v;
        dispercionX = Math.pow(dispercionX, 0.5);
        dispercionY = Math.pow(dispercionY, 0.5);
        double q = masa*i; //indice de emision
        double Y = 200;
        double X = 300;
        double contaminacion = (q/(2*Math.PI*u*v*dispercionX*dispercionY))*(Math.pow(Math.E, -(Y*Y)/(2*dispercionY*dispercionY))*(Math.pow(Math.E, -((X-h)*(X-h))/(2*dispercionX*dispercionX))));
        return contaminacion;
    }

}
