package com.raider.delpozoaudiovisuales.model.objects;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Raider on 02/11/2016.
 */

@Embeddable
public class Pedido_Material_PK implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private Material material;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
