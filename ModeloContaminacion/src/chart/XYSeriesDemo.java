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
    public XYSeriesDemo(final String title, int caso) {
        //Asigna un titulo a la ventana
        super(title);
        //CRea la serie que va a graficar
        final XYSeries serieTiempo = createSeries("Contaminacion");
        // Hace un wrap de la serie
        final XYSeriesCollection data = new XYSeriesCollection(serieTiempo);
        // crea la grafica basado en los datos
        if(caso == 1){
                // Anade la grafica a una ventana
                final JFreeChart ventanaTiempo = createChart(data,1 );
                final ChartPanel chartPanelTiempo = new ChartPanel(ventanaTiempo); 
                // Asigna un tamano a la ventana
                chartPanelTiempo.setPreferredSize(new java.awt.Dimension(800, 450));
                setContentPane(chartPanelTiempo);
        } else {
                 final JFreeChart ventanaXY = createChart(data,2 );
                 final ChartPanel chartPanelXY = new ChartPanel(ventanaXY);
                // Asigna un tamano a la ventana
                chartPanelXY.setPreferredSize(new java.awt.Dimension(800, 450));
                setContentPane(chartPanelXY);
        }
    }


/**
 * Starting point for the demonstration application.
 *
 * @param args  ignored.
 */
    public static void main(final String[] args) {

        final XYSeriesDemo tiempo = new XYSeriesDemo("Modelo Gauseano vs tiempo",1);
        final XYSeriesDemo yvsz = new XYSeriesDemo("Modelo Gauseano y vs z",2);
        tiempo.pack();
        yvsz.pack();
        RefineryUtilities.centerFrameOnScreen(tiempo);
        tiempo.setVisible(true);
        yvsz.setVisible(true);

    }

    //Calcula los valores para la grafica 
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

    /**
     * 
     * Metodo para crear una serie
     * @param String Identificador 
     * @return Serie de valores para usar en el grafico
     */
    private XYSeries createSeries(String contaminacion) {
        XYSeries series=  new XYSeries(contaminacion);
        double iterador = 0;
        for (double i = 0; i < 10; i+=0.3) {
            iterador = calcularValores(i);
            System.out.println(iterador);
            series.add(i, iterador);
        }
        return series;
    }

    /**
     * 
     *  Metodo que permite crear el contenidio de la ventana
     * @param data wraper de la serie
     * @return Informacion para anadir a la ventana
     */
    private JFreeChart createChart(XYSeriesCollection data, int caso) {
        JFreeChart chart; 
        if(caso == 1){
                chart = ChartFactory.createXYLineChart(
                        "Contaminacion del aire", 
                        "Tiempo", 
                        "Valor Contaminacion",
                        data,
                        PlotOrientation.VERTICAL,
                        true,
                        true,
                        false
                );
        } else {
                chart = ChartFactory.createXYLineChart(
                        "Contaminacion del aire", 
                        "Y", 
                        "Z",
                        data,
                        PlotOrientation.VERTICAL,
                        true,
                        true,
                        false
                );
        }        
        return chart;
    }

}
