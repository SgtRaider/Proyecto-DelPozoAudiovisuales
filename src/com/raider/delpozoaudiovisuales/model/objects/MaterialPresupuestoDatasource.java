package com.raider.delpozoaudiovisuales.model.objects;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raider on 15/11/2016.
 */
public class MaterialPresupuestoDatasource implements JRDataSource {

    private List<Presupuesto_Material> presupuestoMaterialList = new ArrayList<>();
    private int indiceParticipanteActual = -1;

    public void addPresupuestomaterial(Presupuesto_Material presupuestoMaterial) {
        this.presupuestoMaterialList.add(presupuestoMaterial);
    }


    @Override
    public boolean next() throws JRException {
        return ++indiceParticipanteActual < presupuestoMaterialList.size();
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        Object valor = null;

        if(jrField.getName().equals("dias")) {

            valor = presupuestoMaterialList.get(indiceParticipanteActual).getDias_uso();
        } else if(jrField.getName().equals("cantidad")){

            valor = presupuestoMaterialList.get(indiceParticipanteActual).getCantidad();
        } else if(jrField.getName().equals("modelo")){

            valor = presupuestoMaterialList.get(indiceParticipanteActual).getMaterial().getModelo();
        } else if(jrField.getName().equals("nombre_material")){

            valor = presupuestoMaterialList.get(indiceParticipanteActual).getMaterial().getNombre();
        } else if(jrField.getName().equals("precio_unidad")){

            valor = presupuestoMaterialList.get(indiceParticipanteActual).getMaterial().getPrecio_dia();
        }

        return valor;
    }
}
