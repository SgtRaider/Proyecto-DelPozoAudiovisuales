package com.raider.delpozoaudiovisuales.controller.guiControllers;

import com.raider.delpozoaudiovisuales.model.database.logic.DbMethods;
import com.raider.delpozoaudiovisuales.model.objects.*;
import com.raider.delpozoaudiovisuales.util.Preferences;
import com.raider.delpozoaudiovisuales.util.PrintReport;
import com.raider.delpozoaudiovisuales.view.GUIPedido;
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
 *  @see GUIPedido
 * En esta clase se controlan y gestionan todos los eventos de la clase GUIPedido,
 * para posteriormente ejecutar la acción correspondiente de la clase model.
 *
 * @since 0.1 Base Alpha
 */
public class PedidoController implements ActionListener, ListSelectionListener{

    private DbMethods dbm;
    private GUIPedido gui;
    private Pedido pedido;
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
     * @param guip de la clase GUIPedido, es la clase donde se constuye el frame, y mediante la cual se accede
     *             a todos los componentes de este, para gestionar eventos o interactuar con los datos.
     *
     * @see com.raider.delpozoaudiovisuales.model.database.logic.DbMethods
     * @see com.raider.delpozoaudiovisuales.view.GUIPedido
     *
     * @since 0.1 Base Alpha
     *
     */
    public PedidoController(DbMethods dbm, GUIPedido guip) {

        this.dbm = dbm;
        this.gui = guip;
        gui.getObservacionesTA().setLineWrap(true);

        pedido = new Pedido();

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
     * @param guip de la clase GUIPedido, es la clase donde se constuye el frame, y mediante la cual se accede
     *             a todos los componentes de este, para gestionar eventos o interactuar con los datos.
     * @param pedido de la clase Pedido, de esta variable se cargan los datos en la gui, para ser visualizados
     *                o modificados.
     *
     * @see com.raider.delpozoaudiovisuales.model.database.logic.DbMethods
     * @see com.raider.delpozoaudiovisuales.view.GUIPedido
     * @see com.raider.delpozoaudiovisuales.model.objects.Pedido
     *
     * @since 0.1 Base Alpha
     *
     */
    public PedidoController(DbMethods dbm, GUIPedido guip, Pedido pedido) {

        this.dbm = dbm;
        this.gui = guip;
        gui.getObservacionesTA().setLineWrap(true);

        this.pedido = pedido;

        loadPedido(pedido);
        loadCliente(pedido.getCliente());
        calcular();

        gui.getNoLB().setText(String.valueOf(pedido.getNo_pedido()));

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
     * @param pedido_material clase con los datos necesarios para rellenar la fila,
     *                        tanto datos de la clase Pedido, como Material, y datos
     *                        específicos de esta clase.
     *
     * @see com.raider.delpozoaudiovisuales.model.objects.Pedido_Material
     *
     * @since 0.1 Base Alpha
     *
     */
    public void addRowMaterial(Pedido_Material pedido_material) {

        Material material = pedido_material.getMaterial();
        String[] fila = {String.valueOf(material.getId()), material.getSub_categoria(), material.getNombre(), material.getModelo(), material.getFabricante(), material.isComprobado() ? "Si" : "No", String.valueOf(material.getPrecio_dia()), String.valueOf(material.getPrecio_feria()), String.valueOf(pedido_material.getCantidad()), String.valueOf(pedido_material.getDias_uso())};
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

                    new GUIseleccionmaterial(dbm, 2, pedido, PedidoController.this).setVisible(true);
                    break;

                case "Cliente":

                    new GUIseleccioncliente(dbm, 2, pedido, PedidoController.this).setVisible(true);
                    break;

                case "Guardar":

                    if(Utilities.mensajeConfirmacion("¿Ha terminado de guardar el pedido?\n" +
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
     * Método de carga de los datos de la pedido sobre la view.
     *
     * @param pedido Clase pedido la cual contiene sus datos.
     *
     * @see Pedido
     *
     * @since 0.1 Base Alpha
     */
    public void loadPedido(Pedido pedido) {

        gui.getNoLB().setText(String.valueOf(pedido.getNo_pedido()));
        gui.getInicioDC().setDate(pedido.getFecha_inicio());
        gui.getFinDC().setDate(pedido.getFecha_fin());
        gui.getDescuentoCB().setSelected(pedido.isMostrar_descuento());
        gui.getPreciosCB().setSelected(pedido.isMostrar_precios());
        gui.getBoolCB().setSelected(pedido.isFinalizado());
        gui.getDescuentoTF().setText(String.valueOf(pedido.getDescuento()));
        gui.getSubtotalLB().setText(String.valueOf(pedido.getSub_total()));
        gui.getIvaLB().setText(String.valueOf(pedido.getIva()));
        gui.getTotalLB().setText(String.valueOf(pedido.getTotal()));
        gui.getObservacionesTA().setText(pedido.getObservaciones());
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

        if (!pedido.getPedidoMaterial().isEmpty()) {

            for(Pedido_Material pedido_material : pedido.getPedidoMaterial()) {
                addRowMaterial(pedido_material);
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
        pedido.setPedidoMaterial(new ArrayList<>());
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

            for (Pedido_Material pedido_material : pedido.getPedidoMaterial()) {

                if (pedido_material.getMaterial().getId() == id) {

                    pedido.getPedidoMaterial().remove(pedido_material);
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

        for (Pedido_Material pedido_material : pedido.getPedidoMaterial()) {

            subtotal += (pedido_material.getCantidad() * pedido_material.getDias_uso() * pedido_material.getMaterial().getPrecio_dia());
        }

        subtotalDesc = (descuento * subtotal);

        iva = (subtotal - subtotalDesc) * iva;

        total = (subtotal - subtotalDesc) + iva;

        pedido.setDescuento(descuento * 100);
        pedido.setSub_total(subtotal);
        pedido.setIva(iva);
        pedido.setTotal(total);
        gui.getTotalLB().setText(String.valueOf(Math.round(total*100.0)/100.0) + " €");
        gui.getIvaLB().setText(String.valueOf(Math.round(iva*100.0)/100.0) + " €");
        gui.getSubtotalLB().setText(String.valueOf(Math.round(subtotal*100.0)/100.0) + " €");
    }

    /**
     * Método en el cual se recogen todos los datos del pedido,
     * para que en caso de haberse realizado el pedido guardarlos en la base de datos como una factura,
     * y posteriormente, abrir la pantalla de impresión,o de envío de email para enviarlo al cliente
     * correspondiente. Ademas de guardar un pdf con la factura en ../Documents/Del_PozoGestor/PDFs
     * y la ruta temporal (De tiempo, año, mes...) correspondiente.
     *
     * @param pedido Clase Pedido de la cual se extraerán los datos para crear la nueva factura.
     *
     * @see PrintReport
     * @see com.raider.delpozoaudiovisuales.util.Email
     * @see GUIenviaremail
     *
     * @since 0.1 Base Alpha
     */
    public void pedidoRealizado(Pedido pedido) {

        pedido.setFinalizado(true);
        dbm.save(pedido);

        Factura factura = new Factura();

        if (pedido.getFactura() == null) {

            factura.setPedido(pedido);
            factura.setCliente(pedido.getCliente());
            factura.setDescuento(pedido.getDescuento());
            factura.setFecha_emision(new Date());
            factura.setFecha_inicio(pedido.getFecha_inicio());
            factura.setFecha_fin(pedido.getFecha_fin());
            factura.setIva(pedido.getIva());
            factura.setTotal(pedido.getTotal());
            factura.setSub_total(pedido.getSub_total());
            factura.setMostrar_descuento(pedido.isMostrar_descuento());
            factura.setMostrar_precios(pedido.isMostrar_precios());
            factura.setNo_factura(dbm.lastID(3));
            factura.setObservaciones(pedido.getObservaciones());

            for (Pedido_Material pedido_material : pedido.getPedidoMaterial()) {

                Factura_Material factura_material = new Factura_Material();
                factura_material.setFactura(factura);
                factura_material.setMaterial(pedido_material.getMaterial());
                factura_material.setCantidad(pedido_material.getCantidad());
                factura_material.setDias_uso(pedido_material.getDias_uso());

                factura.getFacturaMaterial().add(factura_material);
            }

            dbm.save(factura);
            PrintReport printReport = new PrintReport();

            try {

                printReport.printReport(factura.getFecha_emision(), "factura", factura, gui.getImprimirCB().isSelected());
            } catch (JRException e) {

                e.printStackTrace();
                Utilities.mensajeError("Error al generar el report.");
            }

            if (Utilities.mensajeConfirmacion("Factura generada correctamente.\n ¿Desea enviarla por correo?") == 0) {

                new GUIenviaremail(factura, "factura").setVisible(true);
            }
        }
    }

    /**
     * Método en el cual se recogen todos los datos del pedido,
     * para guardarlos en la base de datos, y posteriormente, abrir la pantalla de impresión,
     * o de envío de email para enviarlo al cliente correspondiente. Ademas de guardar un pdf
     * con el pedido en ../Documents/Del_PozoGestor/PDFs y la ruta temporal correspondiente.
     *
     * @see PrintReport
     * @see com.raider.delpozoaudiovisuales.util.Email
     * @see GUIenviaremail
     *
     * @since 0.1 Base Alpha
     */
    public void guardar() {

        pedido.setNo_pedido(Integer.parseInt(gui.getNoLB().getText()));
        if(modify) pedido.setFecha_emision(pedido.getFecha_emision()); else pedido.setFecha_emision(new Date());
        pedido.setFecha_inicio(gui.getInicioDC().getDate());
        pedido.setFecha_fin(gui.getFinDC().getDate());
        pedido.setMostrar_descuento(gui.getDescuentoCB().isSelected());
        pedido.setMostrar_precios(gui.getPreciosCB().isSelected());
        pedido.setObservaciones(gui.getObservacionesTA().getText());

        dbm.save(pedido);

        PrintReport printReport = new PrintReport();

        if (pedido.getPedidoMaterial().size() > 1) {

            try {

                printReport.printReport(pedido.getFecha_emision(), "pedido", pedido, gui.getImprimirCB().isSelected());
            } catch (JRException e) {

                e.printStackTrace();
                Utilities.mensajeError("Error al generar el report.");
            }

            if (gui.getCorreoCB().isSelected()) {

                new GUIenviaremail(pedido, "pedido").setVisible(true);
            }

            if (gui.getBoolCB().isSelected()) {

                pedidoRealizado(pedido);
            } else {
                pedido.setFinalizado(false);
            }

        } else {
            Utilities.mensajeError("Introduzca mas de un Material.");
        }
    }


    /**
     * Método que gestiona los eventos de selección de tabla.
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
