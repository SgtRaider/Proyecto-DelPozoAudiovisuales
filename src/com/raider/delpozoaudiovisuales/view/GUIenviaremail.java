package com.raider.delpozoaudiovisuales.view;

import com.raider.delpozoaudiovisuales.model.objects.Factura;
import com.raider.delpozoaudiovisuales.model.objects.Pedido;
import com.raider.delpozoaudiovisuales.model.objects.Presupuesto;
import com.raider.delpozoaudiovisuales.util.Email;
import raider.Util.Utilities;

import javax.mail.MessagingException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.DataBuffer;
import java.io.IOException;
import java.util.Date;

public class GUIenviaremail extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField asuntoTF;
    private JTextArea cuerpoTA;
    private Object source;
    private String type;

    public GUIenviaremail(Object source, String type) {

        this.source = source;
        this.type = type;

        cuerpoTA.setLineWrap(true);
        setTitle("Alta material");
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);
        setPreferredSize(new Dimension(500, 400));
        setMinimumSize(new Dimension(500, 400));
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
    }

    private void onOK() {

        Presupuesto presupuesto;
        Pedido pedido;
        Factura factura;
        String empresaCliente = "";
        String to = "";
        int no = 0;
        Date docDate = null;

        switch (type) {

            case "presupuesto":
                presupuesto = (Presupuesto) source;
                empresaCliente = presupuesto.getCliente().getEmpresa();
                to = presupuesto.getCliente().getEmail();
                docDate = presupuesto.getFecha_emision();
                no = presupuesto.getNo_presupuesto();
                break;

            case "pedido":
                pedido = (Pedido) source;
                empresaCliente = pedido.getCliente().getEmpresa();
                to = pedido.getCliente().getEmail();
                docDate = pedido.getFecha_emision();
                no = pedido.getNo_pedido();
                break;

            case "factura":
                factura = (Factura) source;
                empresaCliente = factura.getCliente().getEmpresa();
                to = factura.getCliente().getEmail();
                docDate = factura.getFecha_emision();
                no = factura.getNo_factura();
                break;
        }

        if (!asuntoTF.getText().isEmpty() & !cuerpoTA.getText().isEmpty()) {

            Email sc = new Email();

            try {
                sc.enviarEmail(to, cuerpoTA.getText().toString(), asuntoTF.getText().toString(), docDate, type, empresaCliente, no);
            } catch (MessagingException e) {
                e.printStackTrace();
                Utilities.mensajeError("Error al enviar mensaje.");
            } catch (IOException e) {
                e.printStackTrace();
                Utilities.mensajeError("Error al cargar plantilla de email.");
            }
            dispose();
        } else {
            Utilities.mensajeError("No deje vacios los campos.");
        }
    }

    private void onCancel() {
        dispose();
    }
}
