package com.raider.delpozoaudiovisuales.view;

import com.raider.delpozoaudiovisuales.controller.ProjectController;

import javax.swing.*;
import java.awt.*;

/**
 * Created by PMata on 3/10/16.
 */
public class BaseWindow {

    private JPanel basePanel;

    private static JMenuBar mbventana;

    private JMenu mmenu;
    private JMenu mcrear;
    private JMenu mconsultar;

    private JMenuItem milogin;
    private JMenuItem mipreferencias;

    private JMenuItem micrearpresupuesto;
    private JMenuItem micrearpedido;
    private JMenuItem micrearfactura;
    private JMenuItem micrearcliente;
    private JMenuItem micrearmaterial;

    private JMenuItem miconsultarpresupuesto;
    private JMenuItem miconsultarpedido;
    private JMenuItem miconsultarfactura;
    private JMenuItem miconsultarcliente;
    private JMenuItem miconsultarmaterial;

    public BaseWindow() {

        MenuBar();
        ProjectController pc = new ProjectController(BaseWindow.this);
    }

    public static void main(String[] args) {

        // Creacion de la Ventana o Frame con sus elementos

        JFrame frame = new JFrame("Gestor DelPozoAudiovisuales");
        frame.setContentPane(new BaseWindow().basePanel);
        frame.setDefaultCloseOperation(3);
        frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setMinimumSize(new Dimension(1100, 800));
        //frame.setResizable(false);
        frame.pack();
        //frame.setState(Frame.NORMAL);
        frame.setVisible(true);

        frame.setJMenuBar(getMenuBar());
    }

    public void MenuBar() {

        mbventana = new JMenuBar();

        mmenu = new JMenu("Menú");
        mcrear = new JMenu("Dar de alta");
        mconsultar = new JMenu("Consultar");

        milogin = new JMenuItem("Login");
        mipreferencias = new JMenuItem("Preferencias");

        micrearpresupuesto = new JMenuItem("Presupuesto");
        micrearpedido = new JMenuItem("Pedido");
        micrearfactura = new JMenuItem("Factura");
        micrearcliente = new JMenuItem("Cliente");
        micrearmaterial = new JMenuItem("Material");

        miconsultarpresupuesto = new JMenuItem("Presupuesto");
        miconsultarpedido = new JMenuItem("Pedido");
        miconsultarfactura = new JMenuItem("Factura");
        miconsultarmaterial = new JMenuItem("Material");

        mbventana.add(mmenu);
        mbventana.add(mcrear);
        mbventana.add(mconsultar);

        mmenu.add(milogin);
        mmenu.add(mipreferencias);

        mcrear.add(micrearpresupuesto);
        mcrear.add(micrearpedido);
        mcrear.add(micrearfactura);
        mcrear.add(micrearcliente);
        mcrear.add(micrearmaterial);

        mconsultar.add(miconsultarpresupuesto);
        mconsultar.add(miconsultarpedido);
        mconsultar.add(miconsultarfactura);
        mconsultar.add(miconsultarmaterial);
    }

    public static JMenuBar getMenuBar() {
        return mbventana;
    }

    public JPanel getBasePanel() {
        return basePanel;
    }

    public void setBasePanel(JPanel basePanel) {
        this.basePanel = basePanel;
    }

    public static JMenuBar getMbventana() {
        return mbventana;
    }

    public static void setMbventana(JMenuBar mbventana) {
        BaseWindow.mbventana = mbventana;
    }

    public JMenu getMmenu() {
        return mmenu;
    }

    public void setMmenu(JMenu mmenu) {
        this.mmenu = mmenu;
    }

    public JMenu getMcrear() {
        return mcrear;
    }

    public void setMcrear(JMenu mcrear) {
        this.mcrear = mcrear;
    }

    public JMenu getMconsultar() {
        return mconsultar;
    }

    public void setMconsultar(JMenu mconsultar) {
        this.mconsultar = mconsultar;
    }

    public JMenuItem getMilogin() {
        return milogin;
    }

    public void setMilogin(JMenuItem milogin) {
        this.milogin = milogin;
    }

    public JMenuItem getMipreferencias() {
        return mipreferencias;
    }

    public void setMipreferencias(JMenuItem mipreferencias) {
        this.mipreferencias = mipreferencias;
    }

    public JMenuItem getMicrearpresupuesto() {
        return micrearpresupuesto;
    }

    public void setMicrearpresupuesto(JMenuItem micrearpresupuesto) {
        this.micrearpresupuesto = micrearpresupuesto;
    }

    public JMenuItem getMicrearpedido() {
        return micrearpedido;
    }

    public void setMicrearpedido(JMenuItem micrearpedido) {
        this.micrearpedido = micrearpedido;
    }

    public JMenuItem getMicrearfactura() {
        return micrearfactura;
    }

    public void setMicrearfactura(JMenuItem micrearfactura) {
        this.micrearfactura = micrearfactura;
    }

    public JMenuItem getMicrearcliente() {
        return micrearcliente;
    }

    public void setMicrearcliente(JMenuItem micrearcliente) {
        this.micrearcliente = micrearcliente;
    }

    public JMenuItem getMicrearmaterial() {
        return micrearmaterial;
    }

    public void setMicrearmaterial(JMenuItem micrearmaterial) {
        this.micrearmaterial = micrearmaterial;
    }

    public JMenuItem getMiconsultarpresupuesto() {
        return miconsultarpresupuesto;
    }

    public void setMiconsultarpresupuesto(JMenuItem miconsultarpresupuesto) {
        this.miconsultarpresupuesto = miconsultarpresupuesto;
    }

    public JMenuItem getMiconsultarpedido() {
        return miconsultarpedido;
    }

    public void setMiconsultarpedido(JMenuItem miconsultarpedido) {
        this.miconsultarpedido = miconsultarpedido;
    }

    public JMenuItem getMiconsultarfactura() {
        return miconsultarfactura;
    }

    public void setMiconsultarfactura(JMenuItem miconsultarfactura) {
        this.miconsultarfactura = miconsultarfactura;
    }

    public JMenuItem getMiconsultarcliente() {
        return miconsultarcliente;
    }

    public void setMiconsultarcliente(JMenuItem miconsultarcliente) {
        this.miconsultarcliente = miconsultarcliente;
    }

    public JMenuItem getMiconsultarmaterial() {
        return miconsultarmaterial;
    }

    public void setMiconsultarmaterial(JMenuItem miconsultarmaterial) {
        this.miconsultarmaterial = miconsultarmaterial;
    }
}
