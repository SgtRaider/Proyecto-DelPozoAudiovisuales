package com.raider.delpozoaudiovisuales.controller.guiControllers;

import com.raider.delpozoaudiovisuales.model.database.logic.DbMethods;
import com.raider.delpozoaudiovisuales.view.GUIgraficos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Raider on 21/11/2016.
 */
public class GraficosController {

    private DbMethods dbm;
    private GUIgraficos gui;

    public GraficosController(DbMethods dbm, GUIgraficos gui) {

        this.dbm = dbm;
        this.gui = gui;

        gui.getCloseBT().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.getFrame().dispose();
            }
        });

        gui.getTipoCB().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch ((String) gui.getTipoCB().getSelectedItem()) {

                    case "Mensual":
                    break;

                    case "Trimestral":
                        break;

                    case "Anual":
                        break;
                }
            }
        });
    }


}
