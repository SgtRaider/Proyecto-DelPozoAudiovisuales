package com.raider.delpozoaudiovisuales.model.objects;

import javax.persistence.*;

/**
 * Created by Raider on 01/11/2016.
 */

@Entity
@Table(name = "material_pedido")
@AssociationOverrides({
        @AssociationOverride(name = "id.pedido", joinColumns = @JoinColumn(name = "id_pedido")),
        @AssociationOverride(name = "id.material", joinColumns = @JoinColumn(name = "id_material")) })
public class Pedido_Material {

    @EmbeddedId
    private Pedido_Material_PK id = new Pedido_Material_PK();

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "dias_uso")
    private int dias_uso;

    public Pedido_Material_PK getId() {
        return id;
    }

    public void setId(Pedido_Material_PK id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return getId().getPedido();
    }

    public void setPedido(Pedido pedido) {
        getId().setPedido(pedido);
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
