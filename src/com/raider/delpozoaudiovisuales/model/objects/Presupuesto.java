package com.raider.delpozoaudiovisuales.model.objects;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Raider on 01/11/2016.
 */
@Entity
@Table(name="presupuesto")
public class Presupuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="no_presupuesto")
    private int no_presupuesto;

    @Column(name="fecha_validez")
    private Date fecha_validez;

    @Column(name="fecha_emision")
    private Date fecha_emision;

    @Column(name="descuento")
    private float descuento;

    @Column(name="subtotal")
    private float sub_total;

    @Column(name="iva")
    private float iva;

    @Column(name="total")
    private float total;

    @Column(name="fecha_inicio")
    private Date fecha_inicio;

    @Column(name="fecha_fin")
    private Date fecha_fin;

    @Column(name="mostrar_descuento")
    private boolean mostrar_descuento;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name="mostrar_precios")
    private boolean mostrar_precios;

    @Column(name="aprovado")
    private boolean aprovado;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToOne(optional=false, mappedBy="presupuesto")
    private Pedido pedido;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "id.presupuesto",
            cascade = CascadeType.ALL)
    private List<Presupuesto_Material> presupuestoMaterial = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNo_presupuesto() {
        return no_presupuesto;
    }

    public void setNo_presupuesto(int no_presupuesto) {
        this.no_presupuesto = no_presupuesto;
    }

    public Date getFecha_validez() {
        return fecha_validez;
    }

    public void setFecha_validez(Date fecha_validez) {
        this.fecha_validez = fecha_validez;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public float getSub_total() {
        return sub_total;
    }

    public void setSub_total(float sub_total) {
        this.sub_total = sub_total;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public boolean isMostrar_descuento() {
        return mostrar_descuento;
    }

    public void setMostrar_descuento(boolean mostrar_descuento) {
        this.mostrar_descuento = mostrar_descuento;
    }

    public boolean isMostrar_precios() {
        return mostrar_precios;
    }

    public void setMostrar_precios(boolean mostrar_precios) {
        this.mostrar_precios = mostrar_precios;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<Presupuesto_Material> getPresupuestoMaterial() {
        return presupuestoMaterial;
    }

    public void setPresupuestoMaterial(List<Presupuesto_Material> presupuestoMaterial) {
        this.presupuestoMaterial = presupuestoMaterial;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
