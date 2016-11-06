package com.raider.delpozoaudiovisuales.controller.guiControllers;

import com.raider.delpozoaudiovisuales.model.logic.DbMethods;
import com.raider.delpozoaudiovisuales.view.GUIPresupuesto;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Raider on 06/11/2016.
 */
public class PresupuestoController implements ActionListener, ListSelectionListener{

    private DbMethods dbm;
    private GUIPresupuesto gui;

    public PresupuestoController(DbMethods dbm, GUIPresupuesto guip) {
        this.dbm = dbm;
        this.gui = guip;

        addListeners();
    }

    private void addListeners() {
        gui.getCancelarBT().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source.getClass() == JButton.class) {

            String actionCommand = ((JButton) e.getSource()).getActionCommand();

            switch (actionCommand) {

                case "Guardar":
                    break;

                case "Cancelar":
                    if (gui.getFrame() == null) {
                        gui.getMw().getBasePanel().removeAll();
                        gui.getMw().getBasePanel().updateUI();
                    } else {

                        gui.getFrame().dispose();
                    }
                    break;
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
