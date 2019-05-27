/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chart;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class XYSeriesDemo extends ApplicationFrame {

    int k = 2000;//Constante de dispersion turbulenta
    int x = 1000;//Distancia Viento x
    int y = 1000;//Distancia Viento y
    static int u;//velocidad del viento x
    static int v;//velocidad del viento y
    static int h;//Altura
    static int masa;//Masa del contaminante

    /**
     *
     * @param titulo titulo de la ventana
     * @param caso Establece que grafica se va a crear
     */
    public XYSeriesDemo(final String titulo, int caso) {
        //Asigna un titulo a la ventana
        super(titulo);
        //CRea la serie que va a graficar
        final XYSeries serieTiempo = createSeries("Contaminacion");
        // Hace un wrap de la serie
        final XYSeriesCollection data = new XYSeriesCollection(serieTiempo);
        // crea la grafica basado en los datos
        if (caso == 1) {
            // Anade la grafica a una ventana
            final JFreeChart ventanaTiempo = createChart(data, 1);
            final ChartPanel chartPanelTiempo = new ChartPanel(ventanaTiempo);
            // Asigna un tamano a la ventana
            chartPanelTiempo.setPreferredSize(new java.awt.Dimension(800, 450));
            setContentPane(chartPanelTiempo);
        } else {
            final JFreeChart ventanaXY = createChart(data, 2);
            final ChartPanel chartPanelXY = new ChartPanel(ventanaXY);
            // Asigna un tamano a la ventana
            chartPanelXY.setPreferredSize(new java.awt.Dimension(800, 450));
            setContentPane(chartPanelXY);
        }
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args ignored.
     */
    public static void main(final String[] args) {
        JTextField velocidadXField = new JTextField(5);
        JTextField velocidadYField = new JTextField(5);
        JTextField alturaField = new JTextField(5);
        JTextField MasaField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("velocidad del aire en x:"));
        myPanel.add(velocidadXField);
        myPanel.add(new JLabel("velocidad del aire en y:"));
        myPanel.add(velocidadYField);
        myPanel.add(new JLabel("Altura:"));
        myPanel.add(alturaField);
        myPanel.add(new JLabel("Masa del contaminante:"));
        myPanel.add(MasaField);
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.PAGE_AXIS));

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Ingrese los valores iniciales", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            u = Integer.parseInt(velocidadXField.getText());
            v = Integer.parseInt(velocidadYField.getText());
            h = Integer.parseInt(alturaField.getText());
            masa = Integer.parseInt(MasaField.getText());
            final XYSeriesDemo tiempo = new XYSeriesDemo("Modelo Gauseano vs tiempo", 1);
            final XYSeriesDemo yvsz = new XYSeriesDemo("Modelo Gauseano y vs z", 2);
            tiempo.pack();
            yvsz.pack();
            RefineryUtilities.centerFrameOnScreen(tiempo);
            tiempo.setVisible(true);
            yvsz.setVisible(true);
        }
    }

    //Calcula los valores para la grafica 
    private double calcularValores(double i) {
        double dispercionX = 2 * k * x;
        dispercionX /= u;
        double dispercionY = 2 * k * y;
        dispercionY /= v;
        dispercionX = Math.pow(dispercionX, 0.5);
        dispercionY = Math.pow(dispercionY, 0.5);
        double q = masa * i; //indice de emision
        double Y = 200;
        double X = 300;
        double contaminacion = (q / (2 * Math.PI * u * v * dispercionX * dispercionY)) * (Math.pow(Math.E, -(Y * Y) / (2 * dispercionY * dispercionY)) * (Math.pow(Math.E, -((X - h) * (X - h)) / (2 * dispercionX * dispercionX))));
        return contaminacion;
    }

    /**
     *
     * Metodo para crear una serie
     *
     * @param String Identificador
     * @return Serie de valores para usar en el grafico
     */
    private XYSeries createSeries(String contaminacion) {
        XYSeries series = new XYSeries(contaminacion);
        double iterador = 0;
        for (double i = 0; i < 10; i += 0.3) {
            iterador = calcularValores(i);
            System.out.println(iterador);
            series.add(i, iterador);
        }
        return series;
    }

    /**
     *
     * Metodo que permite crear el contenidio de la ventana
     *
     * @param data wraper de la serie
     * @return Informacion para anadir a la ventana
     */
    private JFreeChart createChart(XYSeriesCollection data, int caso) {
        JFreeChart chart;
        if (caso == 1) {
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
                    "Eje Y ( Altura )",
                    "Eje Z ( Profundidad )",
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
