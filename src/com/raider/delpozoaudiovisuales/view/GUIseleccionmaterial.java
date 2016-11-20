package com.raider.delpozoaudiovisuales.view;

import com.raider.delpozoaudiovisuales.controller.guiControllers.FacturaController;
import com.raider.delpozoaudiovisuales.controller.guiControllers.PedidoController;
import com.raider.delpozoaudiovisuales.controller.guiControllers.PresupuestoController;
import com.raider.delpozoaudiovisuales.model.database.logic.DbMethods;
import com.raider.delpozoaudiovisuales.model.objects.*;
import raider.Util.Utilities;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class GUIseleccionmaterial extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JList listMaterial;
    private JTextField buscarTF;
    private JTextField diasTF;
    private JTextField cantidadTF;
    private DefaultListModel<Material> defaultListModel;

    private Material material = null;

    private Presupuesto presupuesto;
    private Pedido pedido;
    private Factura factura;

    private PresupuestoController presupuestoController;
    private PedidoController pedidoController;
    private FacturaController facturaController;

    private DbMethods dbm;
    private int gui;

    public GUIseleccionmaterial(DbMethods dbm, int gui, Object clase, Object controller) {

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
        listMaterial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listMaterial.setModel(defaultListModel);
        listMaterial.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {

                material = (Material) listMaterial.getSelectedValue();
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
                    listMaterial.setSelectedIndex(0);
                } else {

                    String busqueda = buscarTF.getText().toString();
                    HashMap<String, String> campos = new HashMap<String, String>();
                    campos.put("sub_categoria", busqueda);
                    campos.put("nombre", busqueda);
                    loadList(campos);
                    listMaterial.setSelectedIndex(0);
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

        setTitle("Seleccionar material");
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setPreferredSize(new Dimension(450, 400));
        setMinimumSize(new Dimension(450, 400));
        setResizable(false);
        setModal(true);
        pack();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);

        loadList();
    }

    private void onOK() {

        if(material != null) {

            Material material = (Material) listMaterial.getSelectedValue();
            String dias = diasTF.getText().toString();
            String cantidad = cantidadTF.getText().toString();

            if(!dias.isEmpty() && !cantidad.isEmpty()) {

                try {

                    switch (gui) {

                        case 1:

                            Presupuesto_Material presupuesto_material = new Presupuesto_Material();
                            presupuesto_material.setCantidad(Integer.parseInt(cantidad));
                            presupuesto_material.setDias_uso(Integer.parseInt(dias));
                            presupuesto_material.setMaterial(material);
                            presupuesto_material.setPresupuesto(presupuesto);
                            if(existsMaterialPresupuesto(presupuesto, material)) presupuesto.getPresupuestoMaterial().add(presupuesto_material);
                            else Utilities.mensajeError("Ese material ya ha sido seleccionado, y añadido al presupuesto.");
                            presupuestoController.loadMaterial();
                            break;

                        case 2:

                            Pedido_Material pedido_material = new Pedido_Material();
                            pedido_material.setCantidad(Integer.parseInt(cantidad));
                            pedido_material.setDias_uso(Integer.parseInt(dias));
                            pedido_material.setMaterial(material);
                            pedido_material.setPedido(pedido);
                            if(existsMaterialPedido(pedido, material)) pedido.getPedidoMaterial().add(pedido_material);
                            else Utilities.mensajeError("Ese material ya ha sido seleccionado, y añadido al pedido.");
                            pedidoController.loadMaterial();
                            break;

                        case 3:

                            Factura_Material factura_material = new Factura_Material();
                            factura_material.setCantidad(Integer.parseInt(cantidad));
                            factura_material.setDias_uso(Integer.parseInt(dias));
                            factura_material.setMaterial(material);
                            factura_material.setFactura(factura);
                            if(existsMaterialFactura(factura, material)) factura.getFacturaMaterial().add(factura_material);
                            else Utilities.mensajeError("Ese material ya ha sido seleccionado, y añadido al presupuesto.");
                            facturaController.loadMaterial();
                            break;
                    }

                    dispose();

                } catch (NumberFormatException nfe) {

                    nfe.printStackTrace();
                    Utilities.mensajeError("Por favor, introduzca caracteres numéricos en el campo cantidad o dias de uso.");
                }

            } else {

                Utilities.mensajeError("Faltan por rellenar los días de uso o la cantidad de materiales.");
            }

        } else {

            Utilities.mensajeError("Seleccione una opción.");
        }
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void loadList() {

        java.util.List<Material> materialList = dbm.list("material", new HashMap<String, String>());

        defaultListModel.clear();

        if (materialList != null) {

            for (Material material: materialList){

                defaultListModel.addElement(material);
            }
        }
    }

    private void loadList(HashMap<String,String> campoBusqueda) {

        java.util.List<Material> materialList = dbm.list("material", campoBusqueda);

        defaultListModel.clear();

        if (materialList != null) {

            for (Material material: materialList){

                defaultListModel.addElement(material);
            }
        }
    }

    private boolean existsMaterialPresupuesto(Presupuesto presupuesto, Material material){

        for (Presupuesto_Material presupuesto_material:presupuesto.getPresupuestoMaterial()) {

            if (presupuesto_material.getMaterial().equals(material)) return false;
        }

        return true;
    }

    private boolean existsMaterialPedido(Pedido pedido, Material material){

        for (Pedido_Material pedido_material:pedido.getPedidoMaterial()) {

            if (pedido_material.getMaterial().equals(material)) return false;
        }

        return true;
    }

    private boolean existsMaterialFactura(Factura factura, Material material){

        for (Factura_Material factura_material:factura.getFacturaMaterial()) {

            if (factura_material.getMaterial().equals(material)) return false;
        }

        return true;
    }
}
