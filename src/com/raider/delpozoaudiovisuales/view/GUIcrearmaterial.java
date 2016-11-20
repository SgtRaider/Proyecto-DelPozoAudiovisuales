package com.raider.delpozoaudiovisuales.view;

import com.raider.delpozoaudiovisuales.model.database.logic.DbMethods;
import com.raider.delpozoaudiovisuales.model.objects.Material;
import raider.Util.Utilities;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class GUIcrearmaterial extends JDialog implements ListSelectionListener{

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nombreTF;
    private JComboBox subcategoriaCB;
    private JTextField modeloTF;
    private JTextField fabricanteTF;
    private JTextArea descripcionTA;
    private JTextField preciodiaTF;
    private JTextField precioferiaTF;
    private JTextField serialTF;
    private JTextField cantidadTF;
    private JTextField posicionTF;
    private JButton crearButton;
    private JButton eliminarButton;
    private JList materialList;
    private JTextField buscarTF;
    private JCheckBox comprobadoCB;

    private Material material;
    private DefaultListModel<Material> defaultListModel;
    private DbMethods dbm;

    public GUIcrearmaterial(DbMethods dbm) {

        this.dbm = dbm;
        this.material = new Material();
        defaultListModel = new DefaultListModel<>();
        materialList.setModel(defaultListModel);
        materialList.addListSelectionListener(this);
        descripcionTA.setLineWrap(true);
        preciodiaTF.setText(String.valueOf(0.0));
        precioferiaTF.setText(String.valueOf(0.0));
        cantidadTF.setText(String.valueOf(0));

        loadList();

        setTitle("Material");
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setPreferredSize(new Dimension(700, 470));
        setMinimumSize(new Dimension(700, 470));
        setResizable(false);
        setModal(true);
        pack();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);

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
                    emptyText();
                    material = new Material();
                } else {
                    String busqueda = buscarTF.getText().toString();
                    HashMap<String, String> campos = new HashMap<String, String>();
                    campos.put("nombre", busqueda);
                    campos.put("sub_categoria", busqueda);
                    campos.put("modelo", busqueda);
                    loadList(campos);
                    emptyText();
                    material = new Material();
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

        crearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearMaterial();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarMaterial(material);
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        JComboBoxInit();
    }

    private void onOK() {

        setMaterial();
    }

    private void onCancel() {

        dispose();
    }

    private void JComboBoxInit() {

        String[] values = {
                "Proyectores",
                "Pantallas",
                "Videowall",
                "Video",
                "Pantallas proyección",
                "Informática",
                "Iluminación",
                "Mesas de mezclas",
                "Mesas de mezclas auto-amplificadas",
                "Bafles",
                "Bafles auto-amplificados",
                "Etapas de potencia",
                "Microfonos",
                "Amplificadores de sonido",
                "Distribuidores de audio",
                "Traducción simultanea",
                "Equipo visitas guiadas",
                "Mobiliario",
                "Video conferencia"
        };

        for(int i = 0; i < values.length; i++) {
            subcategoriaCB.addItem(values[i]);
        }
    }

    private void crearMaterial() {

        material = new Material();
    }

    private void setMaterial() {

        StringBuffer sb = new StringBuffer();

        if(!nombreTF.getText().isEmpty()) material.setNombre(nombreTF.getText().toString()); else sb.append("Rellene la dirección\n");

        material.setSub_categoria((String) subcategoriaCB.getSelectedItem());
        material.setModelo(modeloTF.getText().toString());
        material.setFabricante(fabricanteTF.getText().toString());
        material.setDescripcion(descripcionTA.getText().toString());

        try {
            if(!preciodiaTF.getText().isEmpty()) material.setPrecio_dia(Float.parseFloat(preciodiaTF.getText().toString())); else sb.append("Rellene precio día\n");
            if(!precioferiaTF.getText().isEmpty()) material.setPrecio_feria(Float.parseFloat(precioferiaTF.getText().toString())); else sb.append("Rellene precio feria\n");
            material.setCantidad(Integer.parseInt(cantidadTF.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            sb.append("Precio o cantidad mal introducidos\n");
        }

        material.setSerial(serialTF.getText().toString());

        material.setPosicion(posicionTF.getText().toString());
        material.setComprobado(comprobadoCB.isSelected());

        if(sb.toString().isEmpty()){

            dbm.save(material);
            crearMaterial();
            emptyText();
            loadList();
            crearMaterial();
        } else {
            Utilities.mensajeError(sb.toString());
        }
    }

    private void eliminarMaterial(Material material) {
        dbm.delete(material);
        loadList();
        emptyText();
        crearMaterial();
    }

    private void loadList() {

        List<Material> materialList = dbm.list("material", new HashMap<String, String>());

        defaultListModel.clear();

        if (materialList != null) {

            for (Material material: materialList){

                defaultListModel.addElement(material);
            }
        }
    }

    private void loadList(HashMap<String,String> campoBusqueda) {

        List<Material> materialList = dbm.list("material", campoBusqueda);

        defaultListModel.clear();

        if (materialList != null) {

            for (Material material: materialList){

                defaultListModel.addElement(material);
            }
        }
    }

    private void loadMaterial(Material material) {

        nombreTF.setText(material.getNombre());
        subcategoriaCB.setSelectedItem(material.getSub_categoria());
        modeloTF.setText(material.getModelo());
        fabricanteTF.setText(material.getFabricante());
        descripcionTA.setText(material.getDescripcion());
        precioferiaTF.setText(String.valueOf(material.getPrecio_feria()));
        preciodiaTF.setText(String.valueOf(material.getPrecio_dia()));
        serialTF.setText(material.getSerial());
        cantidadTF.setText(String.valueOf(material.getCantidad()));
        posicionTF.setText(material.getPosicion());
        comprobadoCB.setSelected(material.isComprobado());
    }

    private void emptyText() {

        nombreTF.setText("");
        subcategoriaCB.setSelectedItem("Proyectores");
        modeloTF.setText("");
        fabricanteTF.setText("");
        descripcionTA.setText("");
        precioferiaTF.setText("");
        preciodiaTF.setText("");
        serialTF.setText("");
        cantidadTF.setText("");
        posicionTF.setText("");
        comprobadoCB.setSelected(false);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        if (materialList.getSelectedValue() != null) material = (Material) materialList.getSelectedValue();
        loadMaterial(material);
    }
}
