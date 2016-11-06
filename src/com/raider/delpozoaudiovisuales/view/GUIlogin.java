package com.raider.delpozoaudiovisuales.view;

import raider.Util.Utilities;

import javax.swing.*;
import java.awt.event.*;

public class GUIlogin extends JDialog implements ActionListener {

    private JPanel contentPane;
    private JButton btOk;
    private JButton btCancel;
    private JPasswordField txtPassword;
    private JTextField txtUsuario;

    private String usuario;
    private String contrasena;

    public GUIlogin() {
        super();
        setTitle("Login");
        getContentPane().add(contentPane);
        getRootPane().setDefaultButton(btOk);
        pack();
        setLocationRelativeTo(null);
        setModal(true);

        btOk.addActionListener(this);
        btCancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btOk) {
            aceptar();
        }
        else if (e.getSource() == btCancel) {
            cancelar();
        }
    }

    private void aceptar() {

        if ((txtUsuario.getText().equals(""))
                || (txtPassword.getPassword().toString().equals(""))) {
            Utilities.mensajeError("Introduzca usuario y contraseña");
            return;
        }

        usuario = txtUsuario.getText();
        contrasena = String.valueOf(txtPassword.getPassword());
        setVisible(false);
    }

    private void cancelar() {
        setVisible(false);
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }
}
