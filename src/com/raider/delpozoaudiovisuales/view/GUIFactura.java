package com.raider.delpozoaudiovisuales.view;

import com.raider.delpozoaudiovisuales.controller.guiControllers.FacturaController;
import com.raider.delpozoaudiovisuales.controller.guiControllers.PedidoController;
import com.raider.delpozoaudiovisuales.model.logic.DbMethods;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Raider on 05/11/2016.
 */
public class GUIFactura {

    private FacturaController fc;
    private BaseWindow mw;
    private JFrame frame = null;

    private JPanel panel1;
    private JButton addmaterialBT;
    private JButton clienteBT;
    private JButton delmaterialBT;
    private JButton delmaterialesBT;
    private JButton calcularBT;
    private JCheckBox imprimirCB;
    private JCheckBox correoCB;
    private JButton guardarBT;
    private JTable TB;
    private JTextArea observacionesTA;
    private JCheckBox descuentoCB;
    private JCheckBox preciosCB;
    private JCheckBox boolCB;
    private JTextField descuentoTF;
    private JLabel noLB;
    private JDateChooser pagoDC;
    private JDateChooser inicioDC;
    private JDateChooser finDC;
    private JLabel empresaLB;
    private JLabel nombre_contactoLB;
    private JLabel apellidos_contactoLB;
    private JLabel telefonoLB;
    private JLabel cifLB;
    private JLabel paisLB;
    private JLabel provinciaLB;
    private JLabel cpLB;
    private JLabel direccionLB;
    private JLabel subtotalLB;
    private JLabel ivaLB;
    private JLabel totalLB;
    private JButton cancelarBT;

    public GUIFactura(DbMethods dbm, BaseWindow mw) {

        this.fc = new FacturaController(dbm, GUIFactura.this);
        this.mw = mw;

        if (mw.getBasePanel().getComponents().length > 0) {

            buildFrame();
        } else {

            addFrame(mw);
        }
    }

    private void buildFrame() {

        frame = new JFrame("Nuevo Presupuesto");
        frame.setContentPane(GUIFactura.this.panel1);
        frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setMinimumSize(new Dimension(1100, 800));
        frame.pack();
        frame.setVisible(true);
    }

    private void addFrame(BaseWindow mw) {

        mw.getBasePanel().add(GUIFactura.this.panel1);
        GUIFactura.this.panel1.setVisible(true);
        GUIFactura.this.panel1.setPreferredSize(mw.getBasePanel().getPreferredSize());
        mw.getBasePanel().updateUI();
    }

    public FacturaController getFc() {
        return fc;
    }

    public void setFc(FacturaController fc) {
        this.fc = fc;
    }

    public BaseWindow getMw() {
        return mw;
    }

    public void setMw(BaseWindow mw) {
        this.mw = mw;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public JButton getAddmaterialBT() {
        return addmaterialBT;
    }

    public void setAddmaterialBT(JButton addmaterialBT) {
        this.addmaterialBT = addmaterialBT;
    }

    public JButton getClienteBT() {
        return clienteBT;
    }

    public void setClienteBT(JButton clienteBT) {
        this.clienteBT = clienteBT;
    }

    public JButton getDelmaterialBT() {
        return delmaterialBT;
    }

    public void setDelmaterialBT(JButton delmaterialBT) {
        this.delmaterialBT = delmaterialBT;
    }

    public JButton getDelmaterialesBT() {
        return delmaterialesBT;
    }

    public void setDelmaterialesBT(JButton delmaterialesBT) {
        this.delmaterialesBT = delmaterialesBT;
    }

    public JButton getCalcularBT() {
        return calcularBT;
    }

    public void setCalcularBT(JButton calcularBT) {
        this.calcularBT = calcularBT;
    }

    public JCheckBox getImprimirCB() {
        return imprimirCB;
    }

    public void setImprimirCB(JCheckBox imprimirCB) {
        this.imprimirCB = imprimirCB;
    }

    public JCheckBox getCorreoCB() {
        return correoCB;
    }

    public void setCorreoCB(JCheckBox correoCB) {
        this.correoCB = correoCB;
    }

    public JButton getGuardarBT() {
        return guardarBT;
    }

    public void setGuardarBT(JButton guardarBT) {
        this.guardarBT = guardarBT;
    }

    public JTable getTB() {
        return TB;
    }

    public void setTB(JTable TB) {
        this.TB = TB;
    }

    public JTextArea getObservacionesTA() {
        return observacionesTA;
    }

    public void setObservacionesTA(JTextArea observacionesTA) {
        this.observacionesTA = observacionesTA;
    }

    public JCheckBox getDescuentoCB() {
        return descuentoCB;
    }

    public void setDescuentoCB(JCheckBox descuentoCB) {
        this.descuentoCB = descuentoCB;
    }

    public JCheckBox getPreciosCB() {
        return preciosCB;
    }

    public void setPreciosCB(JCheckBox preciosCB) {
        this.preciosCB = preciosCB;
    }

    public JCheckBox getBoolCB() {
        return boolCB;
    }

    public void setBoolCB(JCheckBox boolCB) {
        this.boolCB = boolCB;
    }

    public JTextField getDescuentoTF() {
        return descuentoTF;
    }

    public void setDescuentoTF(JTextField descuentoTF) {
        this.descuentoTF = descuentoTF;
    }

    public JLabel getNoLB() {
        return noLB;
    }

    public void setNoLB(JLabel noLB) {
        this.noLB = noLB;
    }

    public JDateChooser getPagoDC() {
        return pagoDC;
    }

    public void setPagoDC(JDateChooser pagoDC) {
        this.pagoDC = pagoDC;
    }

    public JDateChooser getInicioDC() {
        return inicioDC;
    }

    public void setInicioDC(JDateChooser inicioDC) {
        this.inicioDC = inicioDC;
    }

    public JDateChooser getFinDC() {
        return finDC;
    }

    public void setFinDC(JDateChooser finDC) {
        this.finDC = finDC;
    }

    public JLabel getEmpresaLB() {
        return empresaLB;
    }

    public void setEmpresaLB(JLabel empresaLB) {
        this.empresaLB = empresaLB;
    }

    public JLabel getNombre_contactoLB() {
        return nombre_contactoLB;
    }

    public void setNombre_contactoLB(JLabel nombre_contactoLB) {
        this.nombre_contactoLB = nombre_contactoLB;
    }

    public JLabel getApellidos_contactoLB() {
        return apellidos_contactoLB;
    }

    public void setApellidos_contactoLB(JLabel apellidos_contactoLB) {
        this.apellidos_contactoLB = apellidos_contactoLB;
    }

    public JLabel getTelefonoLB() {
        return telefonoLB;
    }

    public void setTelefonoLB(JLabel telefonoLB) {
        this.telefonoLB = telefonoLB;
    }

    public JLabel getCifLB() {
        return cifLB;
    }

    public void setCifLB(JLabel cifLB) {
        this.cifLB = cifLB;
    }

    public JLabel getPaisLB() {
        return paisLB;
    }

    public void setPaisLB(JLabel paisLB) {
        this.paisLB = paisLB;
    }

    public JLabel getProvinciaLB() {
        return provinciaLB;
    }

    public void setProvinciaLB(JLabel provinciaLB) {
        this.provinciaLB = provinciaLB;
    }

    public JLabel getCpLB() {
        return cpLB;
    }

    public void setCpLB(JLabel cpLB) {
        this.cpLB = cpLB;
    }

    public JLabel getDireccionLB() {
        return direccionLB;
    }

    public void setDireccionLB(JLabel direccionLB) {
        this.direccionLB = direccionLB;
    }

    public JLabel getSubtotalLB() {
        return subtotalLB;
    }

    public void setSubtotalLB(JLabel subtotalLB) {
        this.subtotalLB = subtotalLB;
    }

    public JLabel getIvaLB() {
        return ivaLB;
    }

    public void setIvaLB(JLabel ivaLB) {
        this.ivaLB = ivaLB;
    }

    public JLabel getTotalLB() {
        return totalLB;
    }

    public void setTotalLB(JLabel totalLB) {
        this.totalLB = totalLB;
    }

    public JButton getCancelarBT() {
        return cancelarBT;
    }

    public void setCancelarBT(JButton cancelarBT) {
        this.cancelarBT = cancelarBT;
    }
}
