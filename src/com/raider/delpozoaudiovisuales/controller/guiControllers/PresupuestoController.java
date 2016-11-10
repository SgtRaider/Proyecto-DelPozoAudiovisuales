package com.raider.delpozoaudiovisuales.controller.guiControllers;

import com.raider.delpozoaudiovisuales.model.logic.DbMethods;
import com.raider.delpozoaudiovisuales.model.objects.Cliente;
import com.raider.delpozoaudiovisuales.model.objects.Material;
import com.raider.delpozoaudiovisuales.model.objects.Presupuesto;
import com.raider.delpozoaudiovisuales.model.objects.Presupuesto_Material;
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
import java.util.ArrayList;

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

        presupuesto = new Presupuesto();

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
                    if(!presupuesto.getPresupuestoMaterial().isEmpty()) {

                        System.out.println(presupuesto.getPresupuestoMaterial().get(0).getMaterial().getNombre());
                        System.out.println(presupuesto.getPresupuestoMaterial().get(0).getCantidad());
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

    @Override
    public void valueChanged(ListSelectionEvent e) {

        if(gui.getTB().isRowSelected(gui.getTB().getSelectedRow())) {
            id = Integer.parseInt((String) gui.getTB().getValueAt(gui.getTB().getSelectedRow(),0));
            selectedRow = gui.getTB().getSelectedRow();
        }
    }
}
