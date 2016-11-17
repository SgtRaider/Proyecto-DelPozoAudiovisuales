package com.raider.delpozoaudiovisuales.model.objects;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raider on 17/11/2016.
 */
public class MaterialPedidoDatasource implements JRDataSource {

    private List<Pedido_Material> pedidoMaterialList = new ArrayList<>();
    private int indiceParticipanteActual = -1;

    public void addPedidomaterial(Pedido_Material pedidoMaterial) {
        this.pedidoMaterialList.add(pedidoMaterial);
    }


    @Override
    public boolean next() throws JRException {
        return ++indiceParticipanteActual < pedidoMaterialList.size();
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        Object valor = null;

        if(jrField.getName().equals("diasMaterial")) {

            valor = pedidoMaterialList.get(indiceParticipanteActual).getDias_uso();
        } else if(jrField.getName().equals("cantidadMaterial")){

            valor = pedidoMaterialList.get(indiceParticipanteActual).getCantidad();
        } else if(jrField.getName().equals("nombreMaterial")){

            valor = pedidoMaterialList.get(indiceParticipanteActual).getMaterial().getNombre();
        } else if(jrField.getName().equals("precioMaterial")){

            valor = pedidoMaterialList.get(indiceParticipanteActual).getMaterial().getPrecio_dia();
        } else if(jrField.getName().equals("descuentoMaterial")){

            valor = (float) 0.0;
        }

        return valor;
    }
}
