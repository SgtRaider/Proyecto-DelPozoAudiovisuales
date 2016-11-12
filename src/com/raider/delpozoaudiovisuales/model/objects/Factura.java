package com.raider.delpozoaudiovisuales.model.objects;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Raider on 01/11/2016.
 */

@Entity
@Table(name="factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="no_factura")
    private int no_factura;

    @Column(name="fecha_emision")
    private Date fecha_emision;

    @Column(name="fecha_pago")
    private Date fecha_pago;

    @Column(name="descuento")
    private float descuento;

    @Column(name="subtotal")
    private float sub_total;

    @Column(name="iva")
    private float iva;

    @Column(name="total")
    private float total;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name="fecha_inicio")
    private Date fecha_inicio;

    @Column(name="fecha_fin")
    private Date fecha_fin;

    @Column(name="mostrar_descuento")
    private boolean mostrar_descuento;

    @Column(name="mostrar_precios")
    private boolean mostrar_precios;

    @Column(name="pagado")
    private boolean pagado;

    @OneToOne(optional=false)
    @JoinColumn(
            name="id_pedido", unique=true, nullable=false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "id.factura",
            cascade = CascadeType.ALL)
    private List<Factura_Material> facturaMaterial = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNo_factura() {
        return no_factura;
    }

    public void setNo_factura(int no_factura) {
        this.no_factura = no_factura;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
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

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Factura_Material> getFacturaMaterial() {
        return facturaMaterial;
    }

    public void setFacturaMaterial(List<Factura_Material> facturaMaterial) {
        this.facturaMaterial = facturaMaterial;
    }
}
