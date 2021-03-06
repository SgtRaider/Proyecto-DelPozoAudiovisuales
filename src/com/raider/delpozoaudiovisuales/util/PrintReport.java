package com.raider.delpozoaudiovisuales.util;

import com.raider.delpozoaudiovisuales.model.objects.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Raider on 06/11/2016.
 * Clase encargada de crear, guardar y visualizar los reports,
 * asi como organizar los datos para este propósito.
 *
 * @since 0.1 Base Alpha
 */
public class PrintReport {

    /**
     * Método que crea, guarda, y visualiza el report de una clase determinada.
     *
     * @param docDate Date con la fecha de creación del fichero.
     * @param type String con el tipo de documento.
     * @param source Objeto origen, del cual procesar los datos.
     * @param mostrar boolean el cual determina si enseñar la pantalla de impresión, o no.
     *
     * @return File con la ruta del archivo
     *
     * @exception JRException
     *
     * @since 0.1 Base Alpha
     *
     */
    public File printReport(Date docDate, String type, Object source, boolean mostrar) throws JRException {

        File file = null;
        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        FileManager fileManager = new FileManager();
        String nombreCliente = "";
        int no = 0;

        switch (type) {

            case "presupuesto":
                file = new File(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor" + File.separator + "Jaspers" + File.separator +  "Presupuesto_Venta_jasper_report.jasper");
                nombreCliente = ((Presupuesto) source).getCliente().getEmpresa();
                no = ((Presupuesto) source).getNo_presupuesto();
                break;

            case "presupuesto_agrupado":

                file = new File(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor" + File.separator + "Jaspers" + File.separator + "Presupuesto_Agrupado_Venta_jasper_report.jasper");
                nombreCliente = ((Presupuesto) source).getCliente().getEmpresa();
                no = ((Presupuesto) source).getNo_presupuesto();
                break;

            case "pedido":

                file = new File(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor" + File.separator + "Jaspers" + File.separator + "Pedido_Venta_jasper_report.jasper");
                nombreCliente = ((Pedido) source).getCliente().getEmpresa();
                no = ((Pedido) source).getNo_pedido();
                break;

            case "pedido_agrupado":

                file = new File(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor" + File.separator + "Jaspers" + File.separator + "Pedido_Agrupado_Venta_jasper_report.jasper");
                nombreCliente = ((Pedido) source).getCliente().getEmpresa();
                no = ((Pedido) source).getNo_pedido();
                break;

            case "factura":

                file = new File(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor" + File.separator + "Jaspers" + File.separator + "Factura_Venta_jasper_report.jasper");
                nombreCliente = ((Factura) source).getCliente().getEmpresa();
                no = ((Factura) source).getNo_factura();
                break;

            case "factura_agrupado":

                file = new File(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor" + File.separator + "Jaspers" + File.separator + "Factura_Agrupada_Venta_jasper_report.jasper");
                nombreCliente = ((Factura) source).getCliente().getEmpresa();
                no = ((Factura) source).getNo_factura();
                break;

            default:
                file = new File(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor");
                break;
        }

        File contentDir = fileManager.createMonthYearhDir(new SimpleDateFormat("yyyy").format(docDate).toString(), new SimpleDateFormat("MM").format(docDate).toString());

        //TODO Controlar que los datos inherentes al archivo no se puedan modificar

        type = type.substring(0, 1).toUpperCase() + type.substring(1);
        nombreCliente = nombreCliente.substring(0, 1).toUpperCase() + nombreCliente.substring(1);
        String dia = new SimpleDateFormat("dd").format(docDate).toString();

        File pdf = new File(contentDir.getPath() + File.separator + type + "." + no + "_" + nombreCliente + "_" + dia + ".pdf");

        JasperExportManager.exportReportToPdfFile(getJasperPrint(type, source, file), pdf.getPath());

        if (mostrar) JasperViewer.viewReport(getJasperPrint(type, source, file), false);

        return pdf;
    }

    /**
     * Método que crea el JasperPrint con los datos
     *
     * @param type String con el tipo de documento.
     * @param source Objeto origen, del cual procesar los datos.
     * @param file Fichero en el que se encuentra el .jasper
     *
     * @return JasperPrint
     *
     * @exception JRException
     *
     * @see JasperPrint
     *
     * @since 0.1 Base Alpha
     *
     */
    public JasperPrint getJasperPrint(String type, Object source, File file) throws JRException {
        JasperReport report = (JasperReport) JRLoader.loadObject(file);
        return JasperFillManager.fillReport(report, getMap(type, source), getDataSource(type, source));
    }

    /**
     * Método que obtiene los datos del objeto para sintetizar el JRDataSource.
     *
     * @param type String con el tipo de documento.
     * @param source Objeto origen, del cual procesar los datos.
     *
     * @return JRDataSource
     *
     * @see JRDataSource
     *
     * @since 0.1 Base Alpha
     *
     */
    public JRDataSource getDataSource(String type, Object source) {

        switch (type) {

            case "Presupuesto":

                Presupuesto presupuesto = (Presupuesto) source;
                MaterialPresupuestoDatasource materialPresupuestoDatasource = new MaterialPresupuestoDatasource();

                for (Presupuesto_Material presupuesto_material : presupuesto.getPresupuestoMaterial()) {

                    materialPresupuestoDatasource.addPresupuestomaterial(presupuesto_material);
                }

                return materialPresupuestoDatasource;

            case "Pedido":

                Pedido pedido = (Pedido) source;
                MaterialPedidoDatasource materialPedidoDatasource = new MaterialPedidoDatasource();

                for (Pedido_Material pedido_material : pedido.getPedidoMaterial()) {

                    materialPedidoDatasource.addPedidomaterial(pedido_material);
                }

                return materialPedidoDatasource;

            case "Factura":

                Factura factura = (Factura) source;
                MaterialFacturaDatasource materialFacturaDatasource = new MaterialFacturaDatasource();

                for (Factura_Material factura_material : factura.getFacturaMaterial()) {

                    materialFacturaDatasource.addFacturamaterial(factura_material);
                }

                return materialFacturaDatasource;

            default:
                return null;
        }
    }

    /**
     * Método que prepara los parametros en un Map para cargarlos
     * en el Jasper
     *
     * @param type String con el tipo de documento.
     * @param source Objeto origen, del cual procesar los datos.
     *
     * @return Map
     *
     * @since 0.1 Base Alpha
     *
     */
    public Map<String, Object> getMap(String type, Object source) {

        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        String imagePath = "";
        try {
            imagePath = new File(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor"+ File.separator + "Jaspers" + File.separator + "logo-delpozo.jpg").getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();

        }
        Map<String, Object> parameters;

        switch (type) {

            case "Presupuesto":

                Presupuesto presupuesto = (Presupuesto) source;

                parameters = new HashMap<>();
                parameters.put("nopresupuesto", presupuesto.getNo_presupuesto());
                parameters.put("fecha_validez", presupuesto.getFecha_validez());
                parameters.put("fecha_emision", presupuesto.getFecha_emision());
                parameters.put("fecha_inicio", presupuesto.getFecha_inicio());
                parameters.put("fecha_fin", presupuesto.getFecha_fin());
                parameters.put("empresaCliente", presupuesto.getCliente().getEmpresa());
                parameters.put("cifCliente", presupuesto.getCliente().getCif());
                parameters.put("direccionCliente", presupuesto.getCliente().getDireccion());
                parameters.put("cpCliente", presupuesto.getCliente().getCp());
                parameters.put("ciudadCliente", presupuesto.getCliente().getCiudad());
                parameters.put("imagePath", imagePath);
                parameters.put("observaciones", presupuesto.getObservaciones());
                parameters.put("descuentoGen", presupuesto.getDescuento()/100f);
                parameters.put("iva", Float.valueOf(Preferences.getPropertiesUnprotected().get("util.iva")));

                return parameters;

            case "Pedido":

                Pedido pedido = (Pedido) source;

                parameters = new HashMap<>();

                parameters.put("nopedido", pedido.getNo_pedido());
                parameters.put("fecha_emision", pedido.getFecha_emision());
                parameters.put("fecha_inicio", pedido.getFecha_inicio());
                parameters.put("fecha_fin", pedido.getFecha_fin());
                parameters.put("empresaCliente", pedido.getCliente().getEmpresa());
                parameters.put("cifCliente", pedido.getCliente().getCif());
                parameters.put("direccionCliente", pedido.getCliente().getDireccion());
                parameters.put("cpCliente", pedido.getCliente().getCp());
                parameters.put("ciudadCliente", pedido.getCliente().getCiudad());
                parameters.put("imagePath", imagePath);
                parameters.put("observaciones", pedido.getObservaciones());
                parameters.put("descuentoGen", pedido.getDescuento()/100f);
                parameters.put("iva", Float.valueOf(Preferences.getPropertiesUnprotected().get("util.iva")));

                return parameters;

            case "Factura":

                Factura factura = (Factura) source;

                parameters = new HashMap<>();
                parameters.put("descuentoGen", factura.getDescuento()/100f);
                parameters.put("nofactura", factura.getNo_factura());
                parameters.put("fecha_pago", factura.getFecha_pago());
                parameters.put("fecha_emision", factura.getFecha_emision());
                parameters.put("fecha_inicio", factura.getFecha_inicio());
                parameters.put("fecha_fin", factura.getFecha_fin());
                parameters.put("iva", Float.valueOf(Preferences.getPropertiesUnprotected().get("util.iva")));
                parameters.put("empresaCliente", factura.getCliente().getEmpresa());
                parameters.put("cifCliente", factura.getCliente().getCif());
                parameters.put("direccionCliente", factura.getCliente().getDireccion());
                parameters.put("cpCliente", factura.getCliente().getCp());
                parameters.put("ciudadCliente", factura.getCliente().getCiudad());
                parameters.put("imagePath", imagePath);
                parameters.put("observaciones", factura.getObservaciones());

                return parameters;

            default:
                return new HashMap<String, Object>();
        }
    }
}
