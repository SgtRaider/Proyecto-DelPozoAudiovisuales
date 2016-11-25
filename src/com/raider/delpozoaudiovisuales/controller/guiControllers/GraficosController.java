package com.raider.delpozoaudiovisuales.controller.guiControllers;

import com.raider.delpozoaudiovisuales.model.database.logic.DbMethods;
import com.raider.delpozoaudiovisuales.view.GUIgraficos;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Raider on 06/11/2016.
 * Controlador de la clase:
 *  @see GUIgraficos
 * En esta clase se controlan y gestionan todos los eventos de la clase GUIgraficos,
 * para posteriormente ejecutar la acción correspondiente de la clase model.
 *
 * @since 0.1 Base Alpha
 */
public class GraficosController {

    private DbMethods dbm;
    private GUIgraficos gui;
    private ChartPanel chartPanel;

    /**
     * Constructor principal de la clase, se acciona al abrir visualizador de gráficos.
     * Se carga el gráfico inicial y se añade el listener para cargar el gráfico correspondiente.
     *
     * @param dbm de la clase DbMethods, con esta variable se gestionan todas las peticiones
     *            realizadas contra la base de datos.
     * @param gui de la clase GUIgraficos, es la clase donde se construye el frame, y mediante la cual se accede
     *             a todos los componentes de este, para gestionar eventos o interactuar con los datos.
     *
     * @see com.raider.delpozoaudiovisuales.model.database.logic.DbMethods
     * @see com.raider.delpozoaudiovisuales.view.GUIgraficos
     *
     * @since 0.1 Base Alpha
     *
     */
    public GraficosController(DbMethods dbm, GUIgraficos gui) {

        this.dbm = dbm;
        this.gui = gui;

        String type = (String) gui.getTipoCB().getSelectedItem();

        chartPanel = new ChartPanel(createJFreeChart(type));

        gui.getGraficoP().removeAll();
        gui.getGraficoP().add(chartPanel);
        gui.getGraficoP().updateUI();
        chartPanel.setVisible(true);

        gui.getCloseBT().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.getFrame().dispose();
            }
        });

        gui.getTipoCB().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String type = (String) gui.getTipoCB().getSelectedItem();

                chartPanel = new ChartPanel(createJFreeChart(type));

                gui.getGraficoP().removeAll();
                gui.getGraficoP().add(chartPanel);
                gui.getGraficoP().updateUI();
                chartPanel.setVisible(true);
            }
        });
    }

    /**
     * Metodo que consiste en el tratado de los datos y en la creación de un JFreeChart para posteriormente
     * incrustarlo en el ChartPanel correspondiente, para hacer el gráfico visible.
     *
     * @param type String que determina el tipo de grafico que se quiere mostrar, para sintetizar
     *             los datos de la forma que proceda para cada tipo de dato.
     *
     * @return devolvemos el JFreeChart, con los datos obtenidos de la db sintetizados.
     *
     * @see JFreeChart
     *
     * @since 0.1 Base Alpha
     *
     */
    private JFreeChart createJFreeChart(String type) {

        Date lastDate = dbm.getLastFactura();
        Date firstDate = dbm.getFirstFactura();

        Long intervalo = 24 * 60 * 60 * 1000L;
        String x = "";

        TimeSeries gananciasPagadas = new TimeSeries("Ganancias Pagado");
        TimeSeries gananciasNoPagadas = new TimeSeries("Ganancias Sin Pagar");
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        switch (type) {

            case "Mensual":

                intervalo = intervalo * 30;

                for (int i = getYear(firstDate); i <= getYear(lastDate); i++) {
                    for (int j = 1; j <= 12; j++) {
                        gananciasPagadas.add(new Month(j, i), dbm.getGananciasMesAno(j, i, true));
                        gananciasNoPagadas.add(new Month(j, i), dbm.getGananciasMesAno(j, i, false));
                    }
                }

                x = "Meses";

                break;

            case "Trimestral":

                intervalo = intervalo * 91;

                for (int i = getYear(firstDate); i <= getYear(lastDate); i++) {
                    for (int j = 1; j <= 4; j++) {
                        gananciasPagadas.add(new Month(((j * 3) - 2), i), dbm.getGananciasTrimestralesAno(j, i, true));
                        gananciasNoPagadas.add(new Month(((j * 3) - 2), i), dbm.getGananciasTrimestralesAno(j, i, false));
                    }
                }

                x = "Trimestres";

                break;

            case "Anual":

                intervalo = intervalo * 364;

                for (int i = getYear(firstDate); i <= getYear(lastDate); i++) {
                    gananciasPagadas.add(new Year(i), dbm.getGananciasAno(i, true));
                    gananciasNoPagadas.add(new Year(i), dbm.getGananciasAno(i, false));
                }

                x = "Años";

                break;
        }

        dataset.addSeries(gananciasPagadas);
        dataset.addSeries(gananciasNoPagadas);

        //XYDataset xyDataset = MovingAverage.createMovingAverage(dataset, "-Media", intervalo, 0L);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Gráfico " + type,
                x,
                "Ingresos en Euros",
                dataset,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);

        return chart;
    }

    /**
     * Metodo para obtener el trimestre de una fecha.
     *
     * @param date fecha de la cual se quiere saber el trimestre.
     *
     * @return devuelve un int con el trimestre del año.
     *
     * @since 0.1 Base Alpha
     *
     */
    private int getTrimestre(Date date) {

        int mes = getMonth(date);
        int trimestre = 0;

        if (mes > 1 & mes <= 3) {

            trimestre = 1;
        } else {

            if (mes > 3 & mes <= 6) {

                trimestre = 2;
            } else {

                if (mes > 6 & mes <= 9) {

                    trimestre = 3;
                } else {

                    if (mes > 9 & mes <= 12) {

                        trimestre = 4;
                    }
                }
            }
        }

        return trimestre;
    }

    /**
     * Metodo para obtener el año de un date.
     *
     * @param date fecha de la cual se quiere saber el año.
     *
     * @return devuelve un int con el año.
     *
     * @since 0.1 Base Alpha
     *
     */
    private int getYear(Date date) {
        return  Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
    }

    /**
     * Metodo para obtener el mes de un date.
     *
     * @param date fecha de la cual se quiere saber el mes.
     *
     * @return devuelve un int con el mes del año.
     *
     * @since 0.1 Base Alpha
     *
     */
    private int getMonth(Date date) {
        return  Integer.parseInt(new SimpleDateFormat("MM").format(date));
    }
}
