package com.raider.delpozoaudiovisuales.view;

import raider.Util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIlogin extends JDialog implements ActionListener {

    private JPanel contentPane;
    private JButton btOk;
    private JButton btCancel;
    private JPasswordField txtPassword;
    private JTextField txtUsuario;
    private JButton preferenciasButton;

    private String usuario;
    private String contrasena;

    private BaseWindow mw;

    public GUIlogin(BaseWindow mw) {
        super();

        this.mw = mw;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {

                System.exit(0);
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        setTitle("Login");
        getContentPane().add(contentPane);
        getRootPane().setDefaultButton(btOk);
        setPreferredSize(new Dimension(300, 150));
        setMinimumSize(new Dimension(300, 150));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setModal(true);

        preferenciasButton.addActionListener(this);
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
        } else if (e.getSource() == preferenciasButton) {

            new GUIPreferencias().setVisible(true);
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

        visible(false);
    }

    public void visible(boolean bool) { setVisible(bool);}

    private void cancelar() {
        System.exit(0);
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }
}
