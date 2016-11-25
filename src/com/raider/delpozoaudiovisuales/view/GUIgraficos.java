package com.raider.delpozoaudiovisuales.view;

import com.raider.delpozoaudiovisuales.controller.guiControllers.GraficosController;
import com.raider.delpozoaudiovisuales.model.database.logic.DbMethods;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Raider on 21/11/2016.
 */
public class GUIgraficos {

    private JFrame frame;
    private JPanel panel1;
    private JButton closeBT;
    private JComboBox tipoCB;
    private JPanel graficoP;

    public GUIgraficos(DbMethods dbm) {

        new GraficosController(dbm, GUIgraficos.this);

        buildFrame();
    }

    private void buildFrame() {

        frame = new JFrame("Business Inteligence");
        frame.setContentPane(panel1);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setMinimumSize(new Dimension(800, 600));
        frame.pack();
        //frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public JButton getCloseBT() {
        return closeBT;
    }

    public void setCloseBT(JButton closeBT) {
        this.closeBT = closeBT;
    }

    public JComboBox getTipoCB() {
        return tipoCB;
    }

    public void setTipoCB(JComboBox tipoCB) {
        this.tipoCB = tipoCB;
    }

    public JPanel getGraficoP() {
        return graficoP;
    }

    public void setGraficoP(JPanel graficoP) {
        this.graficoP = graficoP;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
