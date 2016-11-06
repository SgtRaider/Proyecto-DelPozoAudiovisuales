package com.raider.delpozoaudiovisuales.model.objects;

import javax.persistence.*;

/**
 * Created by Raider on 01/11/2016.
 */

@Entity
@Table(name = "material_factura")
@AssociationOverrides({
        @AssociationOverride(name = "id.factura", joinColumns = @JoinColumn(name = "id_factura")),
        @AssociationOverride(name = "id.material", joinColumns = @JoinColumn(name = "id_material")) })
public class Factura_Material {

    @EmbeddedId
    private Factura_Material_PK id = new Factura_Material_PK();

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "dias_uso")
    private int dias_uso;

    public Factura_Material_PK getId() {
        return id;
    }

    public void setId(Factura_Material_PK id) {
        this.id = id;
    }

    public Factura getFactura() {
        return getId().getFactura();
    }

    public void setFactura(Factura factura) {
        getId().setFactura(factura);
    }

    public Material getMaterial() {
        return getId().getMaterial();
    }

    public void setMaterial(Material material) {
        getId().setMaterial(material);
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getDias_uso() {
        return dias_uso;
    }

    public void setDias_uso(int dias_uso) {
        this.dias_uso = dias_uso;
    }
}
