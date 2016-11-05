package com.raider.delpozoaudiovisuales.model.objects;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Raider on 02/11/2016.
 */

@Embeddable
public class Presupuesto_Material_PK implements Serializable{

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Presupuesto presupuesto;

    @ManyToOne
    private Material material;

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
