package com.raider.delpozoaudiovisuales.view;

import com.raider.delpozoaudiovisuales.controller.guiControllers.FacturaController;
import com.raider.delpozoaudiovisuales.controller.guiControllers.PedidoController;
import com.raider.delpozoaudiovisuales.controller.guiControllers.PresupuestoController;
import com.raider.delpozoaudiovisuales.model.database.logic.DbMethods;
import com.raider.delpozoaudiovisuales.model.objects.Cliente;
import com.raider.delpozoaudiovisuales.model.objects.Factura;
import com.raider.delpozoaudiovisuales.model.objects.Pedido;
import com.raider.delpozoaudiovisuales.model.objects.Presupuesto;
import raider.Util.Utilities;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GUIseleccioncliente extends JDialog{

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList listCliente;
    private JTextField buscarTF;
    private DefaultListModel<Cliente> defaultListModel;

    private Cliente cliente = null;

    private Presupuesto presupuesto;
    private Pedido pedido;
    private Factura factura;

    private PresupuestoController presupuestoController;
    private PedidoController pedidoController;
    private FacturaController facturaController;

    private DbMethods dbm;
    private int gui;

    public GUIseleccioncliente(DbMethods dbm, int gui, Object clase, Object controller) {

        this.dbm = dbm;
        this.gui = gui;

        switch (gui) {

            case 1:

                presupuestoController = (PresupuestoController) controller;
                presupuesto = (Presupuesto)clase;
                break;

            case 2:

                pedidoController = (PedidoController) controller;
                pedido = (Pedido)clase;
                break;

            case 3:

                facturaController = (FacturaController) controller;
                factura = (Factura)clase;
                break;
        }

        defaultListModel = new DefaultListModel<>();
        listCliente.setModel(defaultListModel);
        listCliente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listCliente.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {

                cliente = (Cliente) listCliente.getSelectedValue();
            }
        });

        buscarTF.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

                if (buscarTF.getText().toString().isEmpty()) {

                    loadList();
                    listCliente.setSelectedIndex(0);
                } else {

                    String busqueda = buscarTF.getText().toString();
                    HashMap<String, String> campos = new HashMap<String, String>();
                    campos.put("empresa", busqueda);
                    campos.put("nombre_contacto", busqueda);
                    loadList(campos);
                    listCliente.setSelectedIndex(0);
                }
            }
        });


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        setTitle("Seleccionar cliente");
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setPreferredSize(new Dimension(300, 400));
        setMinimumSize(new Dimension(300, 400));
        setResizable(false);
        setModal(true);
        pack();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);

        loadList();
    }


    private void loadList() {

        java.util.List<Cliente> clienteList = dbm.list("cliente", new HashMap<String, String>());

        defaultListModel.clear();

        if (clienteList != null) {

            for (Cliente cliente: clienteList){

                defaultListModel.addElement(cliente);
            }
        }
    }

    private void loadList(HashMap<String,String> campoBusqueda) {

        java.util.List<Cliente> clienteList = dbm.list("cliente", campoBusqueda);

        defaultListModel.clear();

        if (clienteList != null) {

            for (Cliente cliente: clienteList){

                defaultListModel.addElement(cliente);
            }
        }
    }

    private void onOK() {

        if(cliente != null) {

            switch (gui) {

                case 1:

                    presupuesto.setCliente((Cliente) listCliente.getSelectedValue());
                    presupuestoController.loadCliente(presupuesto.getCliente());
                    break;

                case 2:

                    pedido.setCliente((Cliente) listCliente.getSelectedValue());
                    pedidoController.loadCliente(presupuesto.getCliente());
                    break;

                case 3:

                    factura.setCliente((Cliente) listCliente.getSelectedValue());
                    facturaController.loadCliente(presupuesto.getCliente());
                    break;
            }

            dispose();

        } else {

            Utilities.mensajeError("Seleccione una opción.");
        }
    }

    private void onCancel() {

        dispose();
    }
}
