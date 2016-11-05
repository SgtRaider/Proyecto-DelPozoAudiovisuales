package com.raider.delpozoaudiovisuales.model.objects;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Raider on 01/11/2016.
 */
@Entity
@Table(name="cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name = "empresa")
    private String empresa;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "cp")
    private String cp;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "pais")
    private String pais;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "fax")
    private String fax;

    @Column(name = "email")
    private String email;

    @Column(name = "nombre_contacto")
    private String nombre_contacto;

    @Column(name = "apellidos_contacto")
    private String apellidos_contacto;

    @Column(name = "telefono_contacto")
    private String telefono_contacto;

    @Column(name = "email_contacto")
    private String email_contacto;

    @OneToMany(mappedBy = "cliente")
    private List<Factura> facturaList;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidoList;

    @OneToMany(mappedBy = "cliente")
    private List<Presupuesto> presupuestoList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre_contacto() {
        return nombre_contacto;
    }

    public void setNombre_contacto(String nombre_contacto) {
        this.nombre_contacto = nombre_contacto;
    }

    public String getApellidos_contacto() {
        return apellidos_contacto;
    }

    public void setApellidos_contacto(String apellidos_contacto) {
        this.apellidos_contacto = apellidos_contacto;
    }

    public String getTelefono_contacto() {
        return telefono_contacto;
    }

    public void setTelefono_contacto(String telefono_contacto) {
        this.telefono_contacto = telefono_contacto;
    }

    public String getEmail_contacto() {
        return email_contacto;
    }

    public void setEmail_contacto(String email_contacto) {
        this.email_contacto = email_contacto;
    }
}
