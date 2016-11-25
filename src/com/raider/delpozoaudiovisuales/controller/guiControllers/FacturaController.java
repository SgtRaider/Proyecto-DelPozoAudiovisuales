package com.raider.delpozoaudiovisuales.controller.guiControllers;

import com.raider.delpozoaudiovisuales.model.database.logic.DbMethods;
import com.raider.delpozoaudiovisuales.model.objects.Cliente;
import com.raider.delpozoaudiovisuales.model.objects.Factura;
import com.raider.delpozoaudiovisuales.model.objects.Factura_Material;
import com.raider.delpozoaudiovisuales.model.objects.Material;
import com.raider.delpozoaudiovisuales.util.Preferences;
import com.raider.delpozoaudiovisuales.util.PrintReport;
import com.raider.delpozoaudiovisuales.view.GUIFactura;
import com.raider.delpozoaudiovisuales.view.GUIenviaremail;
import com.raider.delpozoaudiovisuales.view.GUIseleccioncliente;
import com.raider.delpozoaudiovisuales.view.GUIseleccionmaterial;
import net.sf.jasperreports.engine.JRException;
import raider.Util.Utilities;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Raider on 06/11/2016.
 * Controlador de la clase:
 *  @see GUIFactura
 * En esta clase se controlan y gestionan todos los eventos de la clase GUIFactura,
 * para posteriormente ejecutar la acción correspondiente de la clase model.
 *
 * @since 0.1 Base Alpha
 */
public class FacturaController implements ActionListener, ListSelectionListener{

    private DbMethods dbm;
    private GUIFactura gui;
    private Factura factura;
    private int id;
    private int selectedRow;
    private boolean modify;

    private DefaultTableModel defaultTableModel;

    /**
     * Constructor principal de la clase, se acciona al abrir el formulario de creación,
     * para poder gestionar los eventos posteriormente.
     * Se hacen llamadas a varios metodos para iniciar y cargar la tabla, y a su vez añadir los listeners al view.
     *
     * @param dbm de la clase DbMethods, con esta variable se gestionan todas las peticiones
     *            realizadas contra la base de datos.
     * @param guip de la clase GUIFactura, es la clase donde se constuye el frame, y mediante la cual se accede
     *             a todos los componentes de este, para gestionar eventos o interactuar con los datos.
     *
     * @see com.raider.delpozoaudiovisuales.model.database.logic.DbMethods
     * @see com.raider.delpozoaudiovisuales.view.GUIFactura
     *
     * @since 0.1 Base Alpha
     *
     */
    public FacturaController(DbMethods dbm, GUIFactura guip) {

        this.dbm = dbm;
        this.gui = guip;
        gui.getObservacionesTA().setLineWrap(true);

        factura = new Factura();

        gui.getNoLB().setText(String.valueOf(dbm.lastID(2)));

        createTable();
        loadMaterial();
        addListeners();
        modify = false;
    }

    /**
     * Constructor secundario de la clase, se acciona al abrir el formulario de modificacion/visualización
     * de la información previamente creada, para poder gestionar los eventos posteriormente.
     * Se hacen llamadas a varios metodos para iniciar y cargar la tabla, y a su vez añadir los listeners al view.
     *
     * @param dbm de la clase DbMethods, con esta variable se gestionan todas las peticiones
     *            realizadas contra la base de datos.
     * @param guip de la clase GUIFactura, es la clase donde se constuye el frame, y mediante la cual se accede
     *             a todos los componentes de este, para gestionar eventos o interactuar con los datos.
     * @param factura de la clase Factura, de esta variable se cargan los datos en la gui, para ser visualizados
     *                o modificados.
     *
     * Clases:
     *
     * @see com.raider.delpozoaudiovisuales.model.database.logic.DbMethods
     * @see com.raider.delpozoaudiovisuales.view.GUIFactura
     * @see com.raider.delpozoaudiovisuales.model.objects.Factura
     *
     * @since 0.1 Base Alpha
     *
     */
    public FacturaController(DbMethods dbm, GUIFactura guip, Factura factura) {

        this.dbm = dbm;
        this.gui = guip;
        gui.getObservacionesTA().setLineWrap(true);

        this.factura = factura;

        loadFactura(factura);
        loadCliente(factura.getCliente());
        calcular();

        gui.getNoLB().setText(String.valueOf(factura.getNo_factura()));

        createTable();
        loadMaterial();
        addListeners();
        modify = true;
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


    /**
     * Método el cual añade filas a en la tabla, a partir de los datos del parámetro.
     *
     * @param facturaMaterial clase con los datos necesarios para rellenar la fila,
     *                        tanto datos de la clase Factura, como Material, y datos
     *                        específicos de esta clase.
     *
     * @see com.raider.delpozoaudiovisuales.model.objects.Factura_Material
     *
     * @since 0.1 Base Alpha
     *
     */
    public void addRowMaterial(Factura_Material facturaMaterial) {

        Material material = facturaMaterial.getMaterial();
        String[] fila = {String.valueOf(material.getId()), material.getSub_categoria(), material.getNombre(), material.getModelo(), material.getFabricante(), material.isComprobado() ? "Si" : "No", String.valueOf(material.getPrecio_dia()), String.valueOf(material.getPrecio_feria()), String.valueOf(facturaMaterial.getCantidad()), String.valueOf(facturaMaterial.getDias_uso())};
        defaultTableModel.addRow(fila);
    }

    /**
     * Método el cual añade los listeners a la view.
     *
     * @since 0.1 Base Alpha
     *
     */
    private void addListeners() {

        gui.getCalcularBT().addActionListener(this);
        gui.getDelmaterialBT().addActionListener(this);
        gui.getDelmaterialesBT().addActionListener(this);
        gui.getAddmaterialBT().addActionListener(this);
        gui.getCancelarBT().addActionListener(this);
        gui.getClienteBT().addActionListener(this);
        gui.getGuardarBT().addActionListener(this);
    }

    /**
     * Método que gestiona los eventos click,
     * sobre los botones de la view.
     *
     * @param e
     *
     * @since 0.1 Base Alpha
     */
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

                    new GUIseleccionmaterial(dbm, 3, factura, FacturaController.this).setVisible(true);
                    break;

                case "Cliente":

                    new GUIseleccioncliente(dbm, 3, factura, FacturaController.this).setVisible(true);
                    break;

                case "Guardar":

                    if(Utilities.mensajeConfirmacion("¿Ha terminado de guardar la factura?\n" +
                            " Recuerde presionar el botón calcular\n" +
                            " para recalcular y actualizar lso valores\n" +
                            " de la factura")==0) {

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

    /**
     * Método de carga de los datos del cliente sobre la view.
     *
     * @param cliente Clase cliente la cual contiene sus datos.
     *
     * @see Cliente
     *
     * @since 0.1 Base Alpha
     */
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

    /**
     * Método de carga de los datos de la factura sobre la view.
     *
     * @param factura Clase factura la cual contiene sus datos.
     *
     * @see Factura
     *
     * @since 0.1 Base Alpha
     */
    public void loadFactura(Factura factura) {

        gui.getNoLB().setText(String.valueOf(factura.getNo_factura()));
        gui.getInicioDC().setDate(factura.getFecha_inicio());
        gui.getFinDC().setDate(factura.getFecha_fin());
        gui.getDescuentoCB().setSelected(factura.isMostrar_descuento());
        gui.getPreciosCB().setSelected(factura.isMostrar_precios());
        gui.getBoolCB().setSelected(factura.isPagado());
        gui.getDescuentoTF().setText(String.valueOf(factura.getDescuento()));
        gui.getSubtotalLB().setText(String.valueOf(factura.getSub_total()));
        gui.getIvaLB().setText(String.valueOf(factura.getIva()));
        gui.getTotalLB().setText(String.valueOf(factura.getTotal()));
        gui.getObservacionesTA().setText(factura.getObservaciones());
    }

    /**
     * Método de carga de mateiales contenidos en el presupeusto,
     * sobre la tabla.
     *
     * @see Material
     *
     * @since 0.1 Base Alpha
     */
    public void loadMaterial() {

        defaultTableModel.setNumRows(0);

        if (!factura.getFacturaMaterial().isEmpty()) {

            for(Factura_Material factura_material : factura.getFacturaMaterial()) {
                addRowMaterial(factura_material);
            }
        }
    }

    /**
     * Método de eliminación de todos los materiales de la tabla.
     *
     * @see Material
     *
     * @since 0.1 Base Alpha
     */
    private void removeMateriales() {

        defaultTableModel.setNumRows(0);
        factura.setFacturaMaterial(new ArrayList<>());
        loadMaterial();
    }

    /**
     * Método de eliminación, únicamente del material seleccionado.
     *
     * @see Material
     *
     * @since 0.1 Base Alpha
     */
    private void removeMaterial() {

        if(gui.getTB().isRowSelected(selectedRow)) {

            defaultTableModel.removeRow(selectedRow);

            for (Factura_Material factura_material : factura.getFacturaMaterial()) {

                if (factura_material.getMaterial().getId() == id) {

                    factura.getFacturaMaterial().remove(factura_material);
                    break;
                }
            }

            loadMaterial();
        }
    }

    /**
     * Método que calcula el subtotal, el iva y el total a partir de los materiales, el descuento y el iva,
     * establecido en los parámetros.
     *
     * @since 0.1 Base Alpha
     */
    public void calcular() {

        float iva = Float.valueOf(Preferences.getPropertiesUnprotected().get("util.iva"));
        float subtotal = 0;
        float subtotalDesc = 0;
        float total = 0;
        float descuento = Float.parseFloat(gui.getDescuentoTF().getText().toString().isEmpty() ? "0" : gui.getDescuentoTF().getText().toString()) / 100;

        for (Factura_Material factura_material : factura.getFacturaMaterial()) {

            subtotal += (factura_material.getCantidad() * factura_material.getDias_uso() * factura_material.getMaterial().getPrecio_dia());
        }

        subtotalDesc = (descuento * subtotal);

        iva = (subtotal - subtotalDesc) * iva;

        total = (subtotal - subtotalDesc) + iva;

        factura.setDescuento(descuento * 100);
        factura.setSub_total(subtotal);
        factura.setIva(iva);
        factura.setTotal(total);
        gui.getTotalLB().setText(String.valueOf(Math.round(total*100.0)/100.0) + " €");
        gui.getIvaLB().setText(String.valueOf(Math.round(iva*100.0)/100.0) + " €");
        gui.getSubtotalLB().setText(String.valueOf(Math.round(subtotal*100.0)/100.0) + " €");
    }

    /**
     * Método en el cual se recogen todos los datos de la factura,
     * para guardarlos en la base de datos, y posteriormente, abrir la pantalla de impresión,
     * o de envío de email para enviarlo al cliente correspondiente. Ademas de guardar un pdf
     * con la factura en ../Documents/Del_PozoGestor/PDFs y la ruta temporal correspondiente.
     *
     * @see PrintReport
     * @see com.raider.delpozoaudiovisuales.util.Email
     * @see GUIenviaremail
     *
     * @since 0.1 Base Alpha
     */
    public void guardar() {

        factura.setNo_factura(Integer.parseInt(gui.getNoLB().getText()));
        if(modify) factura.setFecha_emision(factura.getFecha_emision()); else factura.setFecha_emision(new Date());
        if(gui.getBoolCB().isSelected()) factura.setFecha_pago(new Date()); else factura.setFecha_pago(null);
        factura.setFecha_inicio(gui.getInicioDC().getDate());
        factura.setFecha_fin(gui.getFinDC().getDate());
        factura.setMostrar_descuento(gui.getDescuentoCB().isSelected());
        factura.setMostrar_precios(gui.getPreciosCB().isSelected());
        factura.setObservaciones(gui.getObservacionesTA().getText());
        factura.setPagado(gui.getBoolCB().isSelected());

        dbm.save(factura);

        PrintReport printReport = new PrintReport();

        if (factura.getFacturaMaterial().size() > 1) {


            try {

                printReport.printReport(factura.getFecha_emision(), "factura", factura, gui.getImprimirCB().isSelected());
            } catch (JRException e) {

                e.printStackTrace();
                Utilities.mensajeError("Error al generar el report.");
            }

            if(gui.getCorreoCB().isSelected()) {

                new GUIenviaremail(factura, "factura").setVisible(true);
            }

        } else {
            Utilities.mensajeError("Introduzca mas de un Material.");
        }
    }

    /**
     * Método selector de fila, mediante los eventos value changed.
     *
     * @since 0.1 Base Alpha
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {

        if(gui.getTB().isRowSelected(gui.getTB().getSelectedRow())) {

            id = Integer.parseInt((String) gui.getTB().getValueAt(gui.getTB().getSelectedRow(),0));
            selectedRow = gui.getTB().getSelectedRow();
        }
    }
}
