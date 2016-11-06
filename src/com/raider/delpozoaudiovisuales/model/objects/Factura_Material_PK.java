package com.raider.delpozoaudiovisuales.model.objects;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Raider on 02/11/2016.
 */

@Embeddable
public class Factura_Material_PK implements Serializable{

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Factura factura;

    @ManyToOne
    private Material material;

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
