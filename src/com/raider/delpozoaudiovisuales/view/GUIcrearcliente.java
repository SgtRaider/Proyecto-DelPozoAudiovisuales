package com.raider.delpozoaudiovisuales.view;

import com.raider.delpozoaudiovisuales.model.database.logic.DbMethods;
import com.raider.delpozoaudiovisuales.model.objects.Cliente;
import raider.Util.Utilities;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class GUIcrearcliente extends JDialog implements ListSelectionListener {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton eliminarButton;
    private DbMethods dbm;

    private Cliente cliente;

    private DefaultListModel<Cliente> defaultListModel;

    private JTextField empresaTF;
    private JTextField cifTF;
    private JTextField nombrecontactoTF;
    private JTextField apellidoscontactoTF;
    private JTextField telefonocontactoTF;
    private JTextField emailcontactoTF;
    private JTextField direccionTF;
    private JTextField ciudadTF;
    private JTextField proviciaTF;
    private JTextField paisTF;
    private JTextField telefonoTF;
    private JTextField emailTF;
    private JTextField faxTF;
    private JTextField cpTF;
    private JList listCliente;
    private JTextField buscarTF;
    private JButton crearButton;

    public GUIcrearcliente(DbMethods dbm) {

        this.dbm = dbm;

        cliente = new Cliente();

        defaultListModel = new DefaultListModel<>();
        listCliente.setModel(defaultListModel);
        listCliente.addListSelectionListener(this);
        loadList();

        setTitle("Alta cliente");
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setPreferredSize(new Dimension(600, 470));
        setMinimumSize(new Dimension(600, 470));
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
                } else {
                    String busqueda = buscarTF.getText().toString();
                    HashMap<String, String> campos = new HashMap<String, String>();
                    campos.put("empresa", busqueda);
                    campos.put("nombre_contacto", busqueda);
                    loadList(campos);
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

        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setEliminarButton();
            }
        });

        crearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setCrearButton();
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
    }

    private void loadList() {

        List<Cliente> clienteList = dbm.list("cliente", new HashMap<String, String>());

        defaultListModel.clear();

        if (clienteList != null) {

            for (Cliente cliente: clienteList){

                defaultListModel.addElement(cliente);
            }
        }
    }

    private void loadList(HashMap<String,String> campoBusqueda) {

        List<Cliente> clienteList = dbm.list("cliente", campoBusqueda);

        defaultListModel.clear();

        if (clienteList != null) {

            for (Cliente cliente: clienteList){

                defaultListModel.addElement(cliente);
            }
        }
    }

    private void onOK() {

        StringBuffer sb = new StringBuffer();

        if(!empresaTF.getText().isEmpty()) cliente.setEmpresa(empresaTF.getText()); else sb.append("Rellene el nombre de la empresa\n");
        if(!cifTF.getText().isEmpty()) {

            if(cifTF.getText().length() == 9){

                if(Character.isAlphabetic(cifTF.getText().charAt(0))) {

                    cliente.setCif(cifTF.getText());
                } else {
                    sb.append("El primer caracter del CIF debe ser un caracter alfabetico\n");
                }

            } else {

                sb.append("Rellene el cif debe contener 9 caracters\n");
            }

        } else sb.append("Rellene el cif\n");

        if(!direccionTF.getText().isEmpty()) cliente.setDireccion(direccionTF.getText()); else sb.append("Rellene la dirección\n");
        if(!cpTF.getText().isEmpty()) cliente.setCp(cpTF.getText()); else sb.append("Rellene el codigo postal\n");
        if(!ciudadTF.getText().isEmpty()) cliente.setCiudad(ciudadTF.getText());
        if(!proviciaTF.getText().isEmpty()) cliente.setProvincia(proviciaTF.getText());
        if(!paisTF.getText().isEmpty()) cliente.setPais(paisTF.getText());
        if(!telefonoTF.getText().isEmpty()) cliente.setTelefono(telefonoTF.getText()); else sb.append("Rellene el telefono de la empresa\n");
        if(!faxTF.getText().isEmpty()) cliente.setFax(faxTF.getText());
        if(!emailTF.getText().isEmpty()) cliente.setEmail(emailTF.getText());
        if(!nombrecontactoTF.getText().isEmpty()) cliente.setNombre_contacto(nombrecontactoTF.getText()); else sb.append("Rellene el nombre del contacto\n");
        if(!apellidoscontactoTF.getText().isEmpty()) cliente.setApellidos_contacto(apellidoscontactoTF.getText());
        if(!telefonocontactoTF.getText().isEmpty()) cliente.setTelefono_contacto(telefonocontactoTF.getText());
        if(!emailcontactoTF.getText().isEmpty()) cliente.setEmail_contacto(emailcontactoTF.getText());

        if(sb.toString().isEmpty()){

            dbm.save(cliente);
            loadList();
            clearCliente();
            cliente = new Cliente();
        } else {
            Utilities.mensajeError(sb.toString());
        }
    }

    private void onCancel() {

        dispose();
    }

    private void setEliminarButton() {

        dbm.delete(cliente);
        cliente = new Cliente();
        loadList();
        clearCliente();
    }

    private void setCrearButton() {

        cliente = new Cliente();
        clearCliente();
    }

    private void loadCliente(Cliente cliente) {

        empresaTF.setText(cliente.getEmpresa());
        cifTF.setText(cliente.getCif());
        direccionTF.setText(cliente.getDireccion());
        cpTF.setText(cliente.getCp());
        ciudadTF.setText(cliente.getCiudad());
        proviciaTF.setText(cliente.getProvincia());
        paisTF.setText(cliente.getPais());
        telefonoTF.setText(cliente.getTelefono());
        faxTF.setText(cliente.getFax());
        emailTF.setText(cliente.getEmail());
        nombrecontactoTF.setText(cliente.getNombre_contacto());
        apellidoscontactoTF.setText(cliente.getApellidos_contacto());
        telefonocontactoTF.setText(cliente.getTelefono_contacto());
        emailcontactoTF.setText(cliente.getEmail_contacto());
    }

    private void clearCliente() {

        empresaTF.setText("");
        cifTF.setText("");
        direccionTF.setText("");
        cpTF.setText("");
        ciudadTF.setText("");
        proviciaTF.setText("");
        paisTF.setText("");
        telefonoTF.setText("");
        faxTF.setText("");
        emailTF.setText("");
        nombrecontactoTF.setText("");
        apellidoscontactoTF.setText("");
        telefonocontactoTF.setText("");
        emailcontactoTF.setText("");
    }

    public JTextField getFaxTF() {
        return faxTF;
    }

    public void setFaxTF(JTextField faxTF) {
        this.faxTF = faxTF;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public JTextField getEmpresaTF() {
        return empresaTF;
    }

    public void setEmpresaTF(JTextField empresaTF) {
        this.empresaTF = empresaTF;
    }

    public JTextField getCifTF() {
        return cifTF;
    }

    public void setCifTF(JTextField cifTF) {
        this.cifTF = cifTF;
    }

    public JTextField getNombrecontactoTF() {
        return nombrecontactoTF;
    }

    public void setNombrecontactoTF(JTextField nombrecontactoTF) {
        this.nombrecontactoTF = nombrecontactoTF;
    }

    public JTextField getApellidoscontactoTF() {
        return apellidoscontactoTF;
    }

    public void setApellidoscontactoTF(JTextField apellidoscontactoTF) {
        this.apellidoscontactoTF = apellidoscontactoTF;
    }

    public JTextField getTelefonocontactoTF() {
        return telefonocontactoTF;
    }

    public void setTelefonocontactoTF(JTextField telefonocontactoTF) {
        this.telefonocontactoTF = telefonocontactoTF;
    }

    public JTextField getEmailcontactoTF() {
        return emailcontactoTF;
    }

    public void setEmailcontactoTF(JTextField emailcontactoTF) {
        this.emailcontactoTF = emailcontactoTF;
    }

    public JTextField getDireccionTF() {
        return direccionTF;
    }

    public void setDireccionTF(JTextField direccionTF) {
        this.direccionTF = direccionTF;
    }

    public JTextField getCiudadTF() {
        return ciudadTF;
    }

    public void setCiudadTF(JTextField ciudadTF) {
        this.ciudadTF = ciudadTF;
    }

    public JTextField getProviciaTF() {
        return proviciaTF;
    }

    public void setProviciaTF(JTextField proviciaTF) {
        this.proviciaTF = proviciaTF;
    }

    public JTextField getPaisTF() {
        return paisTF;
    }

    public void setPaisTF(JTextField paisTF) {
        this.paisTF = paisTF;
    }

    public JTextField getTelefonoTF() {
        return telefonoTF;
    }

    public void setTelefonoTF(JTextField telefonoTF) {
        this.telefonoTF = telefonoTF;
    }

    public JTextField getEmailTF() {
        return emailTF;
    }

    public void setEmailTF(JTextField emailTF) {
        this.emailTF = emailTF;
    }

    public JTextField getCpTF() {
        return cpTF;
    }

    public void setCpTF(JTextField cpTF) {
        this.cpTF = cpTF;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        if (listCliente.getSelectedValue() != null) cliente = (Cliente) listCliente.getSelectedValue();
        loadCliente(cliente);
    }
}
