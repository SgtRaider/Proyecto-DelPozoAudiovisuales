package com.raider.delpozoaudiovisuales.controller.guiControllers;

import com.raider.delpozoaudiovisuales.model.database.logic.DbMethods;
import com.raider.delpozoaudiovisuales.model.objects.*;
import com.raider.delpozoaudiovisuales.view.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by pmata_ext on 18/11/2016.
 */
public class ListadoDocumentosController implements ListSelectionListener, ActionListener {

    private DbMethods dbm;
    private GUIlistadodocumentos gui;
    private BaseWindow mw;

    private String docTyp = "";
    private int id = 0;

    private DefaultTableModel defaultTableModel;

    public ListadoDocumentosController(DbMethods dbm, GUIlistadodocumentos gui, BaseWindow mw) {
        this.dbm = dbm;
        this.gui = gui;
        this.mw = mw;

        this.gui.getAbrirButton().addActionListener(this);
        this.gui.getCancelarButton().addActionListener(this);

        createTable();
        loadDocuments();
    }

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

    private void cancelar() {
        gui.getFrame().dispose();
    }

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

    @Override
    public void valueChanged(ListSelectionEvent e) {

        if(gui.getTB().isRowSelected(gui.getTB().getSelectedRow())) {

            id = Integer.parseInt((String) gui.getTB().getValueAt(gui.getTB().getSelectedRow(),0));
            docTyp = (String) gui.getTB().getValueAt(gui.getTB().getSelectedRow(),2);
        }
    }
}
