package com.raider.delpozoaudiovisuales.controller.guiControllers;

import com.raider.delpozoaudiovisuales.model.logic.DbMethods;
import com.raider.delpozoaudiovisuales.model.objects.*;
import com.raider.delpozoaudiovisuales.util.Preferences;
import com.raider.delpozoaudiovisuales.view.GUIPresupuesto;
import com.raider.delpozoaudiovisuales.view.GUIseleccioncliente;
import com.raider.delpozoaudiovisuales.view.GUIseleccionmaterial;
import raider.Util.Utilities;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Raider on 06/11/2016.
 */
public class PresupuestoController implements ActionListener, ListSelectionListener{

    private DbMethods dbm;
    private GUIPresupuesto gui;
    private Presupuesto presupuesto;
    private int id;
    private int selectedRow;

    private DefaultTableModel defaultTableModel;

    public PresupuestoController(DbMethods dbm, GUIPresupuesto guip) {

        this.dbm = dbm;
        this.gui = guip;
        gui.getObservacionesTA().setLineWrap(true);

        presupuesto = new Presupuesto();

        gui.getNoLB().setText(String.valueOf(dbm.lastID(1)));

        createTable();
        loadMaterial();
        addListeners();
    }

    public PresupuestoController(DbMethods dbm, GUIPresupuesto guip, Presupuesto presupuesto) {

        this.dbm = dbm;
        this.gui = guip;

        this.presupuesto = presupuesto;

        loadPresupuesto(presupuesto);

        createTable();
        loadMaterial();
        addListeners();
    }

    private void createTable() {

        gui.getTB().getSelectionModel().addListSelectionListener(this);
        defaultTableModel = new DefaultTableModel();
        gui.getTB().setModel(defaultTableModel);

        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("SubCategoria");
        defaultTableModel.addColumn("Nombre");
        defaultTableModel.addColumn("Modelo");
        defaultTableModel.addColumn("Fabricante");
        defaultTableModel.addColumn("Comprobado");
        defaultTableModel.addColumn("Precio Día");
        defaultTableModel.addColumn("Precio Feria");
        defaultTableModel.addColumn("Cantidad");
        defaultTableModel.addColumn("Días");

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        gui.getTB().setDefaultRenderer(Object.class, centerRenderer);
        gui.getTB().setCellSelectionEnabled(false);
        gui.getTB().setRowSelectionAllowed(true);
        gui.getTB().setColumnSelectionAllowed(false);
    }

    public void addRowMaterial(Presupuesto_Material presupuesto_material) {

        Material material = presupuesto_material.getMaterial();

        String[] fila = {String.valueOf(material.getId()), material.getSub_categoria(), material.getNombre(), material.getModelo(), material.getFabricante(), material.isComprobado() ? "Si" : "No", String.valueOf(material.getPrecio_dia()), String.valueOf(material.getPrecio_feria()), String.valueOf(presupuesto_material.getCantidad()), String.valueOf(presupuesto_material.getDias_uso())};
        defaultTableModel.addRow(fila);
    }

    private void addListeners() {

        gui.getCalcularBT().addActionListener(this);
        gui.getDelmaterialBT().addActionListener(this);
        gui.getDelmaterialesBT().addActionListener(this);
        gui.getAddmaterialBT().addActionListener(this);
        gui.getCancelarBT().addActionListener(this);
        gui.getClienteBT().addActionListener(this);
        gui.getGuardarBT().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source.getClass() == JButton.class) {

            String actionCommand = ((JButton) e.getSource()).getActionCommand();

            switch (actionCommand) {

                case "Calcular":

                    calcular();
                    break;

                case "Eliminar Material":

                    removeMaterial();
                    break;

                case "Eliminar Materiales":

                    removeMateriales();
                    break;

                case "Añadir Material":

                    new GUIseleccionmaterial(dbm, 1, presupuesto, PresupuestoController.this).setVisible(true);
                    break;

                case "Cliente":

                    new GUIseleccioncliente(dbm, 1, presupuesto, PresupuestoController.this).setVisible(true);
                    break;

                case "Guardar":

                    if(Utilities.mensajeConfirmacion("¿Ha terminado de guardar el presupuesto?\n" +
                            " Recuerde presionar el botón calcular\n" +
                            " para recalcular y actualizar lso valores\n" +
                            " del presupuesto")==0) {

                        guardar();

                        if (gui.getFrame() == null) {

                            gui.getMw().getBasePanel().removeAll();
                            gui.getMw().getBasePanel().updateUI();
                        } else {

                            gui.getFrame().dispose();
                        }
                    }
                    break;

                case "Cancelar":

                    if (gui.getFrame() == null) {

                        gui.getMw().getBasePanel().removeAll();
                        gui.getMw().getBasePanel().updateUI();
                    } else {

                        gui.getFrame().dispose();
                    }
                    break;
            }
        }
    }

    public void loadCliente(Cliente cliente) {

        gui.getEmpresaLB().setText(cliente.getEmpresa());
        gui.getNombre_contactoLB().setText(cliente.getNombre_contacto());
        gui.getApellidos_contactoLB().setText(cliente.getApellidos_contacto());
        gui.getTelefonoLB().setText(cliente.getTelefono_contacto());
        gui.getCifLB().setText(cliente.getCif());
        gui.getPaisLB().setText(cliente.getPais());
        gui.getProvinciaLB().setText(cliente.getProvincia());
        gui.getCpLB().setText(cliente.getCp());
        gui.getDireccionLB().setText(cliente.getDireccion());
    }

    public void loadPresupuesto(Presupuesto presupuesto) {

        gui.getNoLB().setText(String.valueOf(presupuesto.getNo_presupuesto()));
        gui.getValidezDC().setDate(presupuesto.getFecha_validez());
        gui.getInicioDC().setDate(presupuesto.getFecha_inicio());
        gui.getFinDC().setDate(presupuesto.getFecha_fin());
        gui.getDescuentoCB().setSelected(presupuesto.isMostrar_descuento());
        gui.getPreciosCB().setSelected(presupuesto.isMostrar_precios());
        gui.getBoolCB().setSelected(presupuesto.isAprovado());
        gui.getDescuentoTF().setText(String.valueOf(presupuesto.getDescuento()));
        gui.getSubtotalLB().setText(String.valueOf(presupuesto.getSub_total()));
        gui.getIvaLB().setText(String.valueOf(presupuesto.getIva()));
        gui.getTotalLB().setText(String.valueOf(presupuesto.getTotal()));
        gui.getObservacionesTA().setText(presupuesto.getObservaciones());
    }

    public void loadMaterial() {

        defaultTableModel.setNumRows(0);

        if (!presupuesto.getPresupuestoMaterial().isEmpty()) {

            for(Presupuesto_Material presupuesto_material : presupuesto.getPresupuestoMaterial()) {
                addRowMaterial(presupuesto_material);
            }
        }
    }

    private void removeMateriales() {

        defaultTableModel.setNumRows(0);
        presupuesto.setPresupuestoMaterial(new ArrayList<>());
        loadMaterial();
    }

    private void removeMaterial() {

        if(gui.getTB().isRowSelected(selectedRow)) {

            defaultTableModel.removeRow(selectedRow);

            for (Presupuesto_Material presupuesto_material : presupuesto.getPresupuestoMaterial()) {

                if (presupuesto_material.getMaterial().getId() == id) {

                    presupuesto.getPresupuestoMaterial().remove(presupuesto_material);
                    break;
                }
            }

            loadMaterial();
        }
    }

    public void calcular() {

        float iva = Float.valueOf(Preferences.getPropertiesUnprotected().get("util.iva"));
        float subtotal = 0;
        float total = 0;
        float descuento = Float.parseFloat(gui.getDescuentoTF().getText().toString().isEmpty() ? "0" : gui.getDescuentoTF().getText().toString()) / 100;

        for (Presupuesto_Material presupuesto_material : presupuesto.getPresupuestoMaterial()) {

            subtotal += (presupuesto_material.getCantidad() * presupuesto_material.getDias_uso() * presupuesto_material.getMaterial().getPrecio_dia());
        }

        subtotal = subtotal - (descuento * subtotal);

        iva = subtotal * iva;

        total = subtotal + iva;

        presupuesto.setDescuento(descuento);
        presupuesto.setSub_total(subtotal);
        presupuesto.setIva(iva);
        presupuesto.setTotal(total);
        gui.getTotalLB().setText(String.valueOf(Math.round(total*100.0)/100.0) + " €");
        gui.getIvaLB().setText(String.valueOf(Math.round(iva*100.0)/100.0) + " €");
        gui.getSubtotalLB().setText(String.valueOf(Math.round(subtotal*100.0)/100.0) + " €");
    }

    public void presupuestoAprovado(Presupuesto presupuesto) {

        presupuesto.setAprovado(true);
        dbm.save(presupuesto);

        Pedido pedido = new Pedido();

        pedido.setPresupuesto(presupuesto);
        pedido.setCliente(presupuesto.getCliente());
        pedido.setDescuento(presupuesto.getDescuento());
        pedido.setFecha_emision(new Date());
        pedido.setFecha_inicio(presupuesto.getFecha_inicio());
        pedido.setFecha_fin(presupuesto.getFecha_fin());
        pedido.setIva(presupuesto.getIva());
        pedido.setTotal(presupuesto.getTotal());
        pedido.setSub_total(presupuesto.getSub_total());
        pedido.setMostrar_descuento(presupuesto.isMostrar_descuento());
        pedido.setMostrar_precios(presupuesto.isMostrar_precios());
        pedido.setNo_pedido(dbm.lastID(2));
        pedido.setObservaciones(presupuesto.getObservaciones());

        for (Presupuesto_Material presupuesto_material : presupuesto.getPresupuestoMaterial()) {

            Pedido_Material pedido_material = new Pedido_Material();
            pedido_material.setPedido(pedido);
            pedido_material.setMaterial(presupuesto_material.getMaterial());
            pedido_material.setCantidad(presupuesto_material.getCantidad());
            pedido_material.setDias_uso(presupuesto_material.getDias_uso());

            pedido.getPedidoMaterial().add(pedido_material);
        }

        dbm.save(pedido);

        //TODO crear report.

        //if(Utilities.mensajeConfirmacion("Pedido generado correctamente.\n ¿Desea enviar el pedido por correo?")==0) {

            //TODO enviar correo.
        //}
    }

    public void guardar() {

        presupuesto.setNo_presupuesto(Integer.parseInt(gui.getNoLB().getText()));
        presupuesto.setFecha_emision(new Date());
        presupuesto.setFecha_validez(gui.getValidezDC().getDate());
        presupuesto.setFecha_inicio(gui.getInicioDC().getDate());
        presupuesto.setFecha_fin(gui.getFinDC().getDate());
        presupuesto.setMostrar_descuento(gui.getDescuentoCB().isSelected());
        presupuesto.setMostrar_precios(gui.getPreciosCB().isSelected());
        presupuesto.setObservaciones(gui.getObservacionesTA().getText());

        dbm.save(presupuesto);

        //TODO crear report.

        //if(Utilities.mensajeConfirmacion("Pedido generado correctamente.\n ¿Desea enviar el presupesto por correo?")==0) {

            //TODO enviar correo.

        //}

        if (gui.getBoolCB().isSelected()) {
            presupuestoAprovado(presupuesto);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        if(gui.getTB().isRowSelected(gui.getTB().getSelectedRow())) {
            id = Integer.parseInt((String) gui.getTB().getValueAt(gui.getTB().getSelectedRow(),0));
            selectedRow = gui.getTB().getSelectedRow();
        }
    }
}
