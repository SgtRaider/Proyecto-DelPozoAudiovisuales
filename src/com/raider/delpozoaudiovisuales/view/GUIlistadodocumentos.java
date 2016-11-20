package com.raider.delpozoaudiovisuales.view;

import com.raider.delpozoaudiovisuales.controller.guiControllers.ListadoDocumentosController;
import com.raider.delpozoaudiovisuales.model.database.logic.DbMethods;

import javax.swing.*;
import java.awt.*;

/**
 * Created by pmata_ext on 18/11/2016.
 */
public class GUIlistadodocumentos {

    private JPanel panel1;
    private JTable TB;
    private JButton cancelarButton;
    private JButton abrirButton;
    private JTextField buscarTF;
    private JFrame frame;
    private DbMethods dbm;
    private ListadoDocumentosController lc;

    public GUIlistadodocumentos(DbMethods dbm, BaseWindow mw) {
        this.dbm = dbm;
        this.lc = new ListadoDocumentosController(dbm, GUIlistadodocumentos.this, mw);

        buildFrame();
    }

    private void buildFrame() {

        frame = new JFrame("Consultar Documentos");
        frame.setContentPane(GUIlistadodocumentos.this.panel1);
        frame.setResizable(false);
        frame.setMinimumSize(new Dimension(850, 500));
        frame.setMinimumSize(new Dimension(850, 500));
        frame.pack();
        frame.setVisible(true);
    }

    public JTable getTB() {
        return TB;
    }

    public void setTB(JTable TB) {
        this.TB = TB;
    }

    public JButton getCancelarButton() {
        return cancelarButton;
    }

    public void setCancelarButton(JButton cancelarButton) {
        this.cancelarButton = cancelarButton;
    }

    public JButton getAbrirButton() {
        return abrirButton;
    }

    public void setAbrirButton(JButton abrirButton) {
        this.abrirButton = abrirButton;
    }

    public JTextField getBuscarTF() {
        return buscarTF;
    }

    public void setBuscarTF(JTextField buscarTF) {
        this.buscarTF = buscarTF;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public DbMethods getDbm() {
        return dbm;
    }

    public void setDbm(DbMethods dbm) {
        this.dbm = dbm;
    }

    public ListadoDocumentosController getLc() {
        return lc;
    }

    public void setLc(ListadoDocumentosController lc) {
        this.lc = lc;
    }
}
