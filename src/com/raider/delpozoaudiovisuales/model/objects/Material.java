package com.raider.delpozoaudiovisuales.model.objects;

import javax.persistence.*;

/**
 * Created by Raider on 01/11/2016.
 */

@Entity
@Table(name="material")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="categoria")
    private String categoria = "";

    @Column(name="sub_categoria")
    private String sub_categoria;

    @Column(name="modelo")
    private String modelo;

    @Column(name="fabricante")
    private String fabricante;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="precio_dia")
    private float precio_dia;

    @Column(name="precio_feria")
    private float precio_feria;

    @Column(name="serial")
    private String serial;

    @Column(name="cantidad")
    private int cantidad;

    @Column(name="posicion")
    private String posicion;

    @Column(name="comprobado")
    private boolean comprobado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSub_categoria() {
        return sub_categoria;
    }

    public void setSub_categoria(String sub_categoria) {
        this.sub_categoria = sub_categoria;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio_dia() {
        return precio_dia;
    }

    public void setPrecio_dia(float precio_dia) {
        this.precio_dia = precio_dia;
    }

    public float getPrecio_feria() {
        return precio_feria;
    }

    public void setPrecio_feria(float precio_feria) {
        this.precio_feria = precio_feria;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public boolean isComprobado() {
        return comprobado;
    }

    public void setComprobado(boolean comprobado) {
        this.comprobado = comprobado;
    }

    @Override
    public String toString() {
        return nombre + " - " + modelo + " - " + sub_categoria;
    }
}
