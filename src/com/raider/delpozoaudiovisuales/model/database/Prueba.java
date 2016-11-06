package com.raider.delpozoaudiovisuales.model.database;

import com.raider.delpozoaudiovisuales.model.objects.Cliente;
import com.raider.delpozoaudiovisuales.model.objects.Material;
import com.raider.delpozoaudiovisuales.model.objects.Presupuesto;
import com.raider.delpozoaudiovisuales.model.objects.Presupuesto_Material;
import org.hibernate.Session;


/**
 * Created by Raider on 04/11/2016.
 */
public class Prueba {

    public static HibernateUtil db = new HibernateUtil();

    public static void main(String args[]) {

        db.buildSessionFactory();
        db.openSession();

        Cliente cliente = new Cliente();
        cliente.setEmpresa("Empresa 2");
        save(cliente);

        Material mat = new Material();
        mat.setNombre("material 2");
        mat.setCategoria("x");
        mat.setSub_categoria("x1");
        save(mat);

        Presupuesto prep = new Presupuesto();
        prep.setNo_presupuesto(11);
        prep.setCliente(cliente);

        Presupuesto_Material prepMat = new Presupuesto_Material();
        prepMat.setMaterial(mat);
        prepMat.setPresupuesto(prep);
        prepMat.setCantidad(4);
        prepMat.setDias_uso(2);

        prep.getPresupuestoMaterial().add(prepMat);
        save(prep);
    }

    public static <T> Object save(T entity) {

        Session session = db.getCurrentSession();

        session.beginTransaction();

        session.save(entity);

        session.getTransaction().commit();

        return entity;
    }

    public static <T> Object update(T entitye) {
        Session session = db.getCurrentSession();

        session.beginTransaction();

        session.merge(entitye);

        session.getTransaction().commit();

        return entitye;

    }

    public static <T> void delete(T entity) {
        Session session = db.getCurrentSession();

        session.beginTransaction();

        session.delete(entity);

        session.getTransaction().commit();

    }
}
