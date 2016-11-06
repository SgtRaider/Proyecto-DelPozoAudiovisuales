package com.raider.delpozoaudiovisuales.view;

import com.raider.delpozoaudiovisuales.model.logic.DbMethods;
import com.raider.delpozoaudiovisuales.model.objects.Cliente;
import raider.Util.Utilities;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIcrearcliente extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private DbMethods dbm;

    private Cliente cliente;

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

    public GUIcrearcliente(DbMethods dbm) {

        this.dbm = dbm;

        setTitle("Alta cliente");
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setPreferredSize(new Dimension(600, 300));
        setResizable(false);
        setModal(true);
        pack();
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);

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
    }

    private void onOK() {

        cliente = new Cliente();
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
            dispose();
        } else {
            Utilities.mensajeError(sb.toString());
        }
    }

    private void onCancel() {

        dispose();
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
}
