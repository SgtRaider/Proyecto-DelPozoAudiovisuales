package com.raider.delpozoaudiovisuales.model.objects;

import javax.persistence.*;

/**
 * Created by Raider on 01/11/2016.
 */

@Entity
@Table(name = "material_presupuesto")
@AssociationOverrides({
        @AssociationOverride(name = "id.presupuesto", joinColumns = @JoinColumn(name = "id_presupuesto")),
        @AssociationOverride(name = "id.material", joinColumns = @JoinColumn(name = "id_material")) })
public class Presupuesto_Material {

    @EmbeddedId
    private Presupuesto_Material_PK id = new Presupuesto_Material_PK();

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "dias_uso")
    private int dias_uso;

    public Presupuesto_Material_PK getId() {
        return id;
    }

    public void setId(Presupuesto_Material_PK id) {
        this.id = id;
    }

    public Presupuesto getPresupuesto() {
        return getId().getPresupuesto();
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        getId().setPresupuesto(presupuesto);
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
