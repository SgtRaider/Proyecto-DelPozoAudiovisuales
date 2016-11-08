package com.raider.delpozoaudiovisuales.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIcrearmaterial extends JDialog {
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

    public GUIcrearmaterial() {

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
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }
}
