package com.raider.delpozoaudiovisuales.controller;

import com.raider.delpozoaudiovisuales.model.logic.DbMethods;
import com.raider.delpozoaudiovisuales.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Raider on 06/11/2016.
 */
public class ProjectController implements ActionListener{

    private BaseWindow mw;
    private DbMethods dbm;
    private GUIlogin login;

    public ProjectController(BaseWindow mainWindow) {

        this.mw = mainWindow;
        this.dbm = new DbMethods();

        login = new GUIlogin();
        login.setVisible(true);
        //TODO Comprobar login
        addListeners();
    }

    private void addListeners() {

        mw.getMiconsultarmaterial().addActionListener(this);
        mw.getMiconsultarpedido().addActionListener(this);
        mw.getMiconsultarpresupuesto().addActionListener(this);
        mw.getMiconsultarfactura().addActionListener(this);

        mw.getMicrearcliente().addActionListener(this);
        mw.getMicrearmaterial().addActionListener(this);
        mw.getMicrearpedido().addActionListener(this);
        mw.getMicrearpresupuesto().addActionListener(this);
        mw.getMicrearfactura().addActionListener(this);

        mw.getMilogin().addActionListener(this);
        mw.getMipreferencias().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source.getClass() == JMenuItem.class) {

            String actionCommand = ((JMenuItem) e.getSource()).getActionCommand();

            switch (actionCommand) {

                case "Login":

                    break;

                case "Preferencias":

                    break;

                case "Pedido":

                    if(source == mw.getMicrearpedido()) {

                        new GUIPedido(dbm, mw);
                    } else {

                        if(source == mw.getMiconsultarpedido()) {

                        }
                    }

                    break;

                case "Presupuesto":

                    if(source == mw.getMicrearpresupuesto()) {

                        new GUIPresupuesto(dbm, mw);
                    } else {

                        if(source == mw.getMiconsultarpresupuesto()) {

                        }
                    }

                    break;

                case "Factura":

                    if(source == mw.getMicrearfactura()) {

                        new GUIFactura(dbm, mw);
                    } else {

                        if(source == mw.getMiconsultarfactura()) {

                        }
                    }

                    break;

                case "Material":

                    if(source == mw.getMicrearmaterial()) {
                        new GUIcrearmaterial(dbm).setVisible(true);
                    } else {

                        if(source == mw.getMiconsultarmaterial()) {

                        }
                    }

                    break;

                case "Cliente":

                    if(source == mw.getMicrearcliente()) {

                        new GUIcrearcliente(dbm).setVisible(true);
                    } else {

                        if(source == mw.getMiconsultarcliente()) {

                        }
                    }

                    break;

            }
        }
    }
}
