package com.raider.delpozoaudiovisuales.controller;

import com.raider.delpozoaudiovisuales.model.database.logic.DbMethods;
import com.raider.delpozoaudiovisuales.model.objects.Usuario;
import com.raider.delpozoaudiovisuales.view.*;
import raider.Util.Utilities;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Raider on 06/11/2016.
 * Controlador Principal
 *  @see BaseWindow
 * En esta clase se controlan y gestionan todos los eventos de la clase BaseWindow,
 * para posteriormente mostrar la ventana correspondiente.
 *
 * @since 0.1 Base Alpha
 */
public class ProjectController implements ActionListener{

    private BaseWindow mw;
    private DbMethods dbm;
    private GUIlogin login;
    private Usuario user;

    /**
     * Constructor del frame principal, es el que gestiona la mayotia de eventos de apertura de guis.
     *
     * @param mainWindow frame principal, el cual contiene todos los botones de acceso a la mayoría de guis.
     *
     * Clases:
     * @see com.raider.delpozoaudiovisuales.view.BaseWindow
     *
     * @since 0.1 Base Alpha
     *
     */
    public ProjectController(BaseWindow mainWindow) {

        this.mw = mainWindow;
        this.dbm = new DbMethods();

        login = new GUIlogin(mainWindow);
        login.setVisible(true);

        do {

            user = dbm.login(login.getUsuario(), login.getContrasena());

            if(user != null) {
                login.visible(false);
            } else {
                Utilities.mensajeError("Introduzca un usuario y contraseña correctos.");
                login.visible(true);
            }

        } while (user == null);

        addListeners();
    }

    /**
     * Método el cual añade los listeners a la view.
     *
     * @since 0.1 Base Alpha
     *
     */
    private void addListeners() {

        mw.getMiconsultardocumentos().addActionListener(this);

        mw.getMicrearcliente().addActionListener(this);
        mw.getMicrearmaterial().addActionListener(this);
        mw.getMicrearpedido().addActionListener(this);
        mw.getMicrearpresupuesto().addActionListener(this);
        mw.getMicrearfactura().addActionListener(this);

        mw.getMiconsultargraficas().addActionListener(this);
        mw.getMipreferencias().addActionListener(this);
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

        if (source.getClass() == JMenuItem.class) {

            String actionCommand = ((JMenuItem) e.getSource()).getActionCommand();

            switch (actionCommand) {

                case "Preferencias":
                    new GUIPreferencias().setVisible(true);
                    break;

                case "Pedido":

                    if(source == mw.getMicrearpedido()) {

                        new GUIPedido(dbm, mw);
                    }

                    break;

                case "Presupuesto":

                    if(source == mw.getMicrearpresupuesto()) {

                        new GUIPresupuesto(dbm, mw);
                    }

                    break;

                case "Factura":

                    if(source == mw.getMicrearfactura()) {

                        new GUIFactura(dbm, mw);
                    }

                    break;

                case "Material":

                    if(source == mw.getMicrearmaterial()) {

                        new GUIcrearmaterial(dbm).setVisible(true);
                    }

                    break;

                case "Cliente":

                    if(source == mw.getMicrearcliente()) {

                        new GUIcrearcliente(dbm).setVisible(true);
                    }

                    break;

                case "Documentos":

                    if(source == mw.getMiconsultardocumentos()) {

                        new GUIlistadodocumentos(dbm, mw);
                    }

                    break;

                case "Gráficas":

                    if(source == mw.getMiconsultargraficas()) {

                        new GUIgraficos(dbm);
                    }

                    break;

            }
        }
    }
}
