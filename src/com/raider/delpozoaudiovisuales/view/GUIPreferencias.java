package com.raider.delpozoaudiovisuales.view;

import com.raider.delpozoaudiovisuales.util.Preferences;
import raider.Util.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class GUIPreferencias extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField hostTF;
    private JTextField nombreTF;
    private JTextField userTF;
    private JTextField passTF;
    private JTextField ivaTF;
    private JTextField keyTF;
    private JButton desprotegerButton;
    private boolean isProtected = true;

    public GUIPreferencias() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        desprotegerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDesproteger();
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

        setTitle("Preferencias");
        getRootPane().setDefaultButton(buttonOK);
        setPreferredSize(new Dimension(370, 280));
        setMinimumSize(new Dimension(370, 280));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setModal(true);

        loadProtected();
    }

    private void loadProtected() {

        HashMap<String, String> preferences = Preferences.getPropertiesProtected();

        if (!preferences.isEmpty()) {
            hostTF.setText(preferences.get("db.host"));
            nombreTF.setText(preferences.get("db.name"));
            userTF.setText(preferences.get("db.user"));
            passTF.setText(preferences.get("db.password"));
            ivaTF.setText(preferences.get("util.iva"));

            hostTF.setEditable(false);
            nombreTF.setEditable(false);
            userTF.setEditable(false);
            passTF.setEditable(false);
            ivaTF.setEditable(false);
        } else {
            Utilities.mensajeInformacion("Fichero de configuración creado. \nVuelva a acceder a preferencias para cambiarlo.");
            dispose();
        }

    }

    private void loadUnprotected() {

        HashMap<String, String> preferences = Preferences.getPropertiesUnprotected();

        hostTF.setText(preferences.get("db.host"));
        nombreTF.setText(preferences.get("db.name"));
        userTF.setText(preferences.get("db.user"));
        passTF.setText(preferences.get("db.password"));
        ivaTF.setText(String.valueOf(((int) (Float.parseFloat(preferences.get("util.iva")) * 100))));

        hostTF.setEditable(true);
        nombreTF.setEditable(true);
        userTF.setEditable(true);
        passTF.setEditable(true);
        ivaTF.setEditable(true);
    }

    private void onDesproteger() {

        if (keyTF.getText().toString().equals(Preferences.getPropertiesUnprotected().get("util.SecurityKey"))) {
            isProtected = false;
            loadUnprotected();
        }
    }

    private void onOK() {

        if (!isProtected) {
            Preferences.setProperties(hostTF.getText().toString(), nombreTF.getText().toString(), userTF.getText().toString(), passTF.getText().toString(), String.valueOf((Float.parseFloat(ivaTF.getText().toString())/100)));
            dispose();
        } else {

            Utilities.mensajeError("Desproteja la configuración antes de guardarla.");
        }
    }

    private void onCancel() {
        dispose();
    }

}
