package com.raider.delpozoaudiovisuales.model.objects;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raider on 17/11/2016.
 */
public class MaterialFacturaDatasource implements JRDataSource{

    private List<Factura_Material> facturaMaterialList = new ArrayList<>();
    private int indiceParticipanteActual = -1;

    public void addFacturamaterial(Factura_Material facturaMaterial) {
        this.facturaMaterialList.add(facturaMaterial);
    }


    @Override
    public boolean next() throws JRException {
        return ++indiceParticipanteActual < facturaMaterialList.size();
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        Object valor = null;

        if(jrField.getName().equals("diasMaterial")) {

            valor = facturaMaterialList.get(indiceParticipanteActual).getDias_uso();
        } else if(jrField.getName().equals("cantidadMaterial")){

            valor = facturaMaterialList.get(indiceParticipanteActual).getCantidad();
        } else if(jrField.getName().equals("nombreMaterial")){

            valor = facturaMaterialList.get(indiceParticipanteActual).getMaterial().getNombre();
        } else if(jrField.getName().equals("precioMaterial")){

            valor = facturaMaterialList.get(indiceParticipanteActual).getMaterial().getPrecio_dia();
        } else if(jrField.getName().equals("descuentoMaterial")){

            valor = (float) 0.0;
        }

        return valor;
    }
}
