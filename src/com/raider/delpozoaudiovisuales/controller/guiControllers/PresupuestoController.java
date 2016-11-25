package com.raider.delpozoaudiovisuales.controller.guiControllers;

import com.raider.delpozoaudiovisuales.model.database.logic.DbMethods;
import com.raider.delpozoaudiovisuales.model.objects.*;
import com.raider.delpozoaudiovisuales.util.Preferences;
import com.raider.delpozoaudiovisuales.util.PrintReport;
import com.raider.delpozoaudiovisuales.view.GUIPresupuesto;
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
 *  @see GUIPresupuesto
 * En esta clase se controlan y gestionan todos los eventos de la clase GUIPresupuesto,
 * para posteriormente ejecutar la acción correspondiente de la clase model.
 *
 * @since 0.1 Base Alpha
 */
public class PresupuestoController implements ActionListener, ListSelectionListener{

    private DbMethods dbm;
    private GUIPresupuesto gui;
    private Presupuesto presupuesto;
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
     * @param guip de la clase GUIPresupuesto, es la clase donde se constuye el frame, y mediante la cual se accede
     *             a todos los componentes de este, para gestionar eventos o interactuar con los datos.
     *
     * Clases:
     * @see com.raider.delpozoaudiovisuales.model.database.logic.DbMethods
     * @see com.raider.delpozoaudiovisuales.view.GUIPresupuesto
     *
     * @since 0.1 Base Alpha
     *
     */
    public PresupuestoController(DbMethods dbm, GUIPresupuesto guip) {

        this.dbm = dbm;
        this.gui = guip;
        gui.getObservacionesTA().setLineWrap(true);

        presupuesto = new Presupuesto();

        gui.getNoLB().setText(String.valueOf(dbm.lastID(1)));

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
     * @param guip de la clase GUIPrespupuesto, es la clase donde se constuye el frame, y mediante la cual se accede
     *             a todos los componentes de este, para gestionar eventos o interactuar con los datos.
     * @param presupuesto de la clase Presupuesto, de esta variable se cargan los datos en la gui, para ser visualizados
     *                o modificados.
     *
     * Clases:
     *
     * @see com.raider.delpozoaudiovisuales.model.database.logic.DbMethods
     * @see com.raider.delpozoaudiovisuales.view.GUIPresupuesto
     * @see com.raider.delpozoaudiovisuales.model.objects.Presupuesto
     *
     * @since 0.1 Base Alpha
     *
     */
    public PresupuestoController(DbMethods dbm, GUIPresupuesto guip, Presupuesto presupuesto) {

        this.dbm = dbm;
        this.gui = guip;
        gui.getObservacionesTA().setLineWrap(true);

        this.presupuesto = presupuesto;

        loadPresupuesto(presupuesto);
        loadCliente(presupuesto.getCliente());
        calcular();

        gui.getNoLB().setText(String.valueOf(presupuesto.getNo_presupuesto()));

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
     * @param presupuesto_material clase con los datos necesarios para rellenar la fila,
     *                        tanto datos de la clase Pedido, como Material, y datos
     *                        específicos de esta clase.
     *
     * @see com.raider.delpozoaudiovisuales.model.objects.Presupuesto_Material
     *
     * @since 0.1 Base Alpha
     *
     */
    public void addRowMaterial(Presupuesto_Material presupuesto_material) {

        Material material = presupuesto_material.getMaterial();
        String[] fila = {String.valueOf(material.getId()), material.getSub_categoria(), material.getNombre(), material.getModelo(), material.getFabricante(), material.isComprobado() ? "Si" : "No", String.valueOf(material.getPrecio_dia()), String.valueOf(material.getPrecio_feria()), String.valueOf(presupuesto_material.getCantidad()), String.valueOf(presupuesto_material.getDias_uso())};
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
     * @param presupuesto Clase presupuesto la cual contiene sus datos.
     *
     * @see Pedido
     *
     * @since 0.1 Base Alpha
     */
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

        if (!presupuesto.getPresupuestoMaterial().isEmpty()) {

            for(Presupuesto_Material presupuesto_material : presupuesto.getPresupuestoMaterial()) {
                addRowMaterial(presupuesto_material);
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
        presupuesto.setPresupuestoMaterial(new ArrayList<>());
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

            for (Presupuesto_Material presupuesto_material : presupuesto.getPresupuestoMaterial()) {

                if (presupuesto_material.getMaterial().getId() == id) {

                    presupuesto.getPresupuestoMaterial().remove(presupuesto_material);
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

        for (Presupuesto_Material presupuesto_material : presupuesto.getPresupuestoMaterial()) {

            subtotal += (presupuesto_material.getCantidad() * presupuesto_material.getDias_uso() * presupuesto_material.getMaterial().getPrecio_dia());
        }

        subtotalDesc = (descuento * subtotal);

        iva = (subtotal - subtotalDesc) * iva;

        total = (subtotal - subtotalDesc) + iva;

        presupuesto.setDescuento(descuento * 100);
        presupuesto.setSub_total(subtotal);
        presupuesto.setIva(iva);
        presupuesto.setTotal(total);
        gui.getTotalLB().setText(String.valueOf(Math.round(total*100.0)/100.0) + " €");
        gui.getIvaLB().setText(String.valueOf(Math.round(iva*100.0)/100.0) + " €");
        gui.getSubtotalLB().setText(String.valueOf(Math.round(subtotal*100.0)/100.0) + " €");
    }

    /**
     * Método en el cual se recogen todos los datos del presupuesto,
     * para que en caso de haberse realizado el pedido guardarlos en la base de datos como un pedido,
     * y posteriormente, abrir la pantalla de impresión,o de envío de email para enviarlo al cliente
     * correspondiente. Ademas de guardar un pdf con la pedido en ../Documents/Del_PozoGestor/PDFs
     * y la ruta temporal (De tiempo, año, mes...) correspondiente.
     *
     * @param presupuesto Clase Presupuesto de la cual se extraerán los datos para crear el nuevo pedido.
     *
     * @see PrintReport
     * @see com.raider.delpozoaudiovisuales.util.Email
     * @see GUIenviaremail
     *
     * @since 0.1 Base Alpha
     */
    public void presupuestoAprovado(Presupuesto presupuesto) {

        presupuesto.setAprovado(true);
        dbm.save(presupuesto);

        Pedido pedido = new Pedido();

        if (presupuesto.getPedido() == null) {

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
            PrintReport printReport = new PrintReport();

            try {

                printReport.printReport(presupuesto.getFecha_emision(), "pedido", pedido, gui.getImprimirCB().isSelected());
            } catch (JRException e) {

                e.printStackTrace();
                Utilities.mensajeError("Error al generar el report.");
            }

            if (Utilities.mensajeConfirmacion("Pedido generado correctamente.\n ¿Desea enviar el pedido por correo?") == 0) {

                new GUIenviaremail(pedido, "pedido").setVisible(true);
            }
        }
    }

    /**
     * Método en el cual se recogen todos los datos del presupuesto,
     * para guardarlos en la base de datos, y posteriormente, abrir la pantalla de impresión,
     * o de envío de email para enviarlo al cliente correspondiente. Ademas de guardar un pdf
     * con el presupuesto en ../Documents/Del_PozoGestor/PDFs y la ruta temporal correspondiente.
     *
     * @see PrintReport
     * @see com.raider.delpozoaudiovisuales.util.Email
     * @see GUIenviaremail
     *
     * @since 0.1 Base Alpha
     */
    public void guardar() {

        presupuesto.setNo_presupuesto(Integer.parseInt(gui.getNoLB().getText()));
        if (modify) presupuesto.setFecha_emision(presupuesto.getFecha_emision());
        else presupuesto.setFecha_emision(new Date());
        presupuesto.setFecha_validez(gui.getValidezDC().getDate());
        presupuesto.setFecha_inicio(gui.getInicioDC().getDate());
        presupuesto.setFecha_fin(gui.getFinDC().getDate());
        presupuesto.setMostrar_descuento(gui.getDescuentoCB().isSelected());
        presupuesto.setMostrar_precios(gui.getPreciosCB().isSelected());
        presupuesto.setObservaciones(gui.getObservacionesTA().getText());

        dbm.save(presupuesto);

        PrintReport printReport = new PrintReport();

        if (presupuesto.getPresupuestoMaterial().size() > 1) {

            try {

                printReport.printReport(presupuesto.getFecha_emision(), "presupuesto", presupuesto, gui.getImprimirCB().isSelected());

            } catch (JRException e) {

                e.printStackTrace();
                Utilities.mensajeError("Error al generar el report.");
            }

            if (gui.getCorreoCB().isSelected()) {

                if (presupuesto.getPresupuestoMaterial().size() > 1)
                    new GUIenviaremail(presupuesto, "presupuesto").setVisible(true);
            }

            if (gui.getBoolCB().isSelected()) {

                if (presupuesto.getPresupuestoMaterial().size() > 1) presupuestoAprovado(presupuesto);
            } else {
                if (presupuesto.getPresupuestoMaterial().size() > 1) presupuesto.setAprovado(false);
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
