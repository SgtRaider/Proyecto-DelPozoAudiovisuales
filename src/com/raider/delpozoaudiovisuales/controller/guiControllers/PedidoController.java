package com.raider.delpozoaudiovisuales.controller.guiControllers;

import com.raider.delpozoaudiovisuales.model.logic.DbMethods;
import com.raider.delpozoaudiovisuales.model.objects.Cliente;
import com.raider.delpozoaudiovisuales.model.objects.Material;
import com.raider.delpozoaudiovisuales.view.GUIPedido;
import com.raider.delpozoaudiovisuales.view.GUIPresupuesto;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Raider on 06/11/2016.
 */
public class PedidoController implements ActionListener, ListSelectionListener{

    private DbMethods dbm;
    private GUIPedido gui;

    private DefaultTableModel defaultTableModel;

    public PedidoController(DbMethods dbm, GUIPedido guip) {
        this.dbm = dbm;
        this.gui = guip;

        addListeners();
    }

    private void addListeners() {
        gui.getCancelarBT().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source.getClass() == JButton.class) {

            String actionCommand = ((JButton) e.getSource()).getActionCommand();

            switch (actionCommand) {

                case "Guardar":
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

    private void createTable() {

        defaultTableModel = new DefaultTableModel();
        gui.getTB().setModel(defaultTableModel);

        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("SubCategoria");
        defaultTableModel.addColumn("Nombre");
        defaultTableModel.addColumn("Modelo");
        defaultTableModel.addColumn("Fabricante");
        defaultTableModel.addColumn("Comprovado");
        defaultTableModel.addColumn("Precio Día");
        defaultTableModel.addColumn("Precio Feria");
        defaultTableModel.addColumn("Cantidad");
        defaultTableModel.addColumn("Días");
    }

    public void addRowMaterial(Material material) {

        String[] fila = {};
        defaultTableModel.addRow(fila);
    }

    public void loadMaterial() {

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
