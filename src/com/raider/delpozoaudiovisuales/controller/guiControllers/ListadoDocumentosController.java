package com.raider.delpozoaudiovisuales.controller.guiControllers;

import com.raider.delpozoaudiovisuales.model.database.logic.DbMethods;
import com.raider.delpozoaudiovisuales.model.objects.Factura;
import com.raider.delpozoaudiovisuales.model.objects.Pedido;
import com.raider.delpozoaudiovisuales.model.objects.Presupuesto;
import com.raider.delpozoaudiovisuales.view.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Raider on 06/11/2016.
 * Controlador de la clase:
 *  @see GUIlistadodocumentos
 * En esta clase se controlan y gestionan todos los eventos de la clase GUIlistadodocumentos,
 * para posteriormente ejecutar la acción correspondiente de la clase model.
 *
 * @since 0.1 Base Alpha
 */
public class ListadoDocumentosController implements ListSelectionListener, ActionListener {

    private DbMethods dbm;
    private GUIlistadodocumentos gui;
    private BaseWindow mw;

    private String docTyp = "";
    private int id = 0;

    private DefaultTableModel defaultTableModel;

    /**
     * Constructor principal de la clase, crea una tabla con todos los documentos(Presupuestos, Pedidos y Facturas)
     * de la base de datos, para poder acceder a toda su información, así como modificarla, imprimirla o enviarla
     * al cliente en cuestión.
     *
     * @param dbm de la clase DbMethods, con esta variable se gestionan todas las peticiones
     *            realizadas contra la base de datos.
     * @param gui de la clase GUIlistadodocumentos, es la clase donde se constuye el frame,
     *            y la tabla en la cual se van a mostrar todos los documentos.
     *
     * @param mw de la clase BaseWindow, es la gui principal.
     *
     * @see com.raider.delpozoaudiovisuales.model.database.logic.DbMethods
     * @see com.raider.delpozoaudiovisuales.view.GUIFactura
     * @see com.raider.delpozoaudiovisuales.view.BaseWindow
     *
     * @since 0.1 Base Alpha
     *
     */
    public ListadoDocumentosController(DbMethods dbm, GUIlistadodocumentos gui, BaseWindow mw) {
        this.dbm = dbm;
        this.gui = gui;
        this.mw = mw;

        this.gui.getAbrirButton().addActionListener(this);
        this.gui.getCancelarButton().addActionListener(this);

        createTable();
        loadDocuments();
    }

    /**
     * Método de construcción y renderizado de las columnas de la tabla,
     * crea la tabla para su posterior uso.
     *
     * @since 0.1 Base Alpha
     *
     */
    private void createTable() {

        gui.getTB().getSelectionModel().addListSelectionListener(this);
        defaultTableModel = new DefaultTableModel();
        gui.getTB().setModel(defaultTableModel);

        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Nº de Documento");
        defaultTableModel.addColumn("Tipo Documento");
        defaultTableModel.addColumn("Empresa");
        defaultTableModel.addColumn("Fecha de emisión");
        defaultTableModel.addColumn("Inicio del Evento");
        defaultTableModel.addColumn("Fin del Evento");

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        gui.getTB().setDefaultRenderer(Object.class, centerRenderer);
        gui.getTB().setCellSelectionEnabled(false);
        gui.getTB().setRowSelectionAllowed(true);
        gui.getTB().setColumnSelectionAllowed(false);
    }

    /**
     * Método el cual añade filas a en la tabla, a partir de los datos del parámetro.
     *
     * @param source Clase genérica, la cual sera casteada dependiendo de la key,
     *               para cargar los datos en la tabla, y poderlos identificar,
     *               dependiendo del tipo que sea.
     *
     * @param key String la cual va a determinar que tipo de objeto es source.
     *
     * @see Presupuesto
     * @see Pedido
     * @see Factura
     *
     * @since 0.1 Base Alpha
     *
     */
    public void addRow(Object source, String key) {

        switch (key) {

            case "presupuesto":
                Presupuesto presupuesto = (Presupuesto) source;
                String[] filaPresupuesto = {String.valueOf(presupuesto.getId()), String.valueOf(presupuesto.getNo_presupuesto()), "Presupuesto", presupuesto.getCliente().getEmpresa(), new SimpleDateFormat("dd/MM/yyyy").format(presupuesto.getFecha_emision()).toString(), new SimpleDateFormat("dd/MM//yyyy").format(presupuesto.getFecha_inicio()).toString(), new SimpleDateFormat("dd/MM//yyyy").format(presupuesto.getFecha_fin()).toString()};
                defaultTableModel.addRow(filaPresupuesto);
                break;

            case "pedido":
                Pedido pedido = (Pedido) source;
                String[] filaPedido = {String.valueOf(pedido.getId()), String.valueOf(pedido.getNo_pedido()), "Pedido", pedido.getCliente().getEmpresa(), new SimpleDateFormat("dd/MM/yyyy").format(pedido.getFecha_emision()).toString(), new SimpleDateFormat("dd/MM//yyyy").format(pedido.getFecha_inicio()).toString(), new SimpleDateFormat("dd/MM//yyyy").format(pedido.getFecha_fin()).toString()};
                defaultTableModel.addRow(filaPedido);
                break;

            case "factura":
                Factura factura = (Factura) source;
                String[] filaFactura = {String.valueOf(factura.getId()), String.valueOf(factura.getNo_factura()), "Factura", factura.getCliente().getEmpresa(), new SimpleDateFormat("dd/MM/yyyy").format(factura.getFecha_emision()).toString(), new SimpleDateFormat("dd/MM//yyyy").format(factura.getFecha_inicio()).toString(), new SimpleDateFormat("dd/MM//yyyy").format(factura.getFecha_fin()).toString()};
                defaultTableModel.addRow(filaFactura);
                break;
        }
    }

    /**
     * Método el cual llama a DbMethods para obtener los registros de Presupeusto, Pedido y Factura, para añadirlos
     * posteriormente con el metodo anterior.
     *
     * @see Presupuesto
     * @see Pedido
     * @see Factura
     *
     * @since 0.1 Base Alpha
     */
    public void loadDocuments() {

        defaultTableModel.setNumRows(0);

        HashMap<String, List> listaResgistros = new HashMap<>();
        listaResgistros.put("presupuesto", dbm.list("presupuesto", new HashMap<>()));
        listaResgistros.put("pedido", dbm.list("pedido", new HashMap<>()));
        listaResgistros.put("factura", dbm.list("factura", new HashMap<>()));

        for (String key: listaResgistros.keySet()) {

            switch (key) {

                case "presupuesto":

                    for (Presupuesto presupuesto: (List<Presupuesto>)listaResgistros.get(key)){
                        addRow(presupuesto, key);
                    }

                    break;

                case "pedido":

                    for (Pedido pedido: (List<Pedido>)listaResgistros.get(key)){
                        addRow(pedido, key);
                    }

                    break;

                case "factura":

                    for (Factura factura: (List<Factura>)listaResgistros.get(key)){
                        addRow(factura, key);
                    }

                    break;
            }
        }
    }

    /**
     * Método el cual crea la gui del documento correspondiente, dependiendo de la fila seleccionada.
     *
     * @see Presupuesto
     * @see GUIPresupuesto
     * @see Pedido
     * @see GUIPedido
     * @see Factura
     * @see GUIFactura
     *
     * @since 0.1 Base Alpha
     */
    private void abrir() {

        if (docTyp != "" & id != 0){

            switch (docTyp.toLowerCase()){

                case "presupuesto":
                    Presupuesto presupuesto = (Presupuesto) dbm.getById(docTyp.toLowerCase(), id);
                    new GUIPresupuesto(dbm, mw, presupuesto);
                    break;

                case "pedido":
                    Pedido pedido = (Pedido) dbm.getById(docTyp.toLowerCase(), id);
                    new GUIPedido(dbm, mw, pedido);
                    break;

                case "factura":
                    Factura factura = (Factura) dbm.getById(docTyp.toLowerCase(), id);
                    new GUIFactura(dbm, mw, factura);
                    break;
            }
        }
    }

    /**
     * Método que cierra este frame.
     *
     * @since 0.1 Base Alpha
     */
    private void cancelar() {
        gui.getFrame().dispose();
    }

    /**
     * Método que gestiona los eventos de los metodos:
     *
     * @see ListadoDocumentosController#abrir
     * @see ListadoDocumentosController#cancelar
     *
     * @since 0.1 Base Alpha
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source.getClass() == JButton.class) {

            String actionCommand = ((JButton) e.getSource()).getActionCommand();

            switch (actionCommand) {

                case "Abrir":
                    abrir();
                    break;

                case "Cancelar":
                    cancelar();
                    break;
            }
        }
    }

    /**
     * Método que gestiona los eventos de selección de tabla,
     * y obtiene las variables del id y el tipo de documento,
     * para mas tarde cargar la gui que corresponda.
     *
     * @since 0.1 Base Alpha
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {

        if(gui.getTB().isRowSelected(gui.getTB().getSelectedRow())) {

            id = Integer.parseInt((String) gui.getTB().getValueAt(gui.getTB().getSelectedRow(),0));
            docTyp = (String) gui.getTB().getValueAt(gui.getTB().getSelectedRow(),2);
        }
    }
}
