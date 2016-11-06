package com.raider.delpozoaudiovisuales.model.logic;

import com.raider.delpozoaudiovisuales.model.objects.*;
import com.raider.delpozoaudiovisuales.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Raider on 05/11/2016.
 */
public class DbMethods {

    public static HibernateUtil db = new HibernateUtil();

    public DbMethods() {

        db.buildSessionFactory();
        db.openSession();
    }

    public <T> Object save(T entity) {

        Session session = db.getCurrentSession();

        session.beginTransaction();

        session.save(entity);

        session.getTransaction().commit();

        return entity;
    }

    public <T> Object update(T entitye) {

        Session session = db.getCurrentSession();

        session.beginTransaction();

        session.merge(entitye);

        session.getTransaction().commit();

        return entitye;

    }

    public <T> void delete(T entity) {

        Session session = db.getCurrentSession();

        session.beginTransaction();

        session.delete(entity);

        session.getTransaction().commit();
    }

    public List list(String object) {

        Session session = db.getCurrentSession();

        String sql;
        Query query;
        StringBuffer sb = new StringBuffer();

        switch (object) {

            case "cliente":
                sql = "SELECT Cliente FROM Cliente ";
                query = session.createQuery(sql);
                List<Cliente> clienteList = query.list();
                return clienteList;

            case "factura":
                sql = "SELECT Factura FROM Factura ";
                query = session.createQuery(sql);
                List<Factura> facturaList = query.list();
                return facturaList;

            case "material":
                sql = "SELECT Material FROM Material ";
                query = session.createQuery(sql);
                List<Material> materialList = query.list();
                return materialList;

            case "pedido":
                sql = "SELECT Pedido FROM Pedido ";
                query = session.createQuery(sql);
                List<Pedido> pedidoList = query.list();
                return pedidoList;

            case "presupuesto":
                sql = "SELECT Presupuesto FROM Presupuesto ";
                query = session.createQuery(sql);
                List<Presupuesto> presupuestoList = query.list();
                return presupuestoList;

            default:
                return null;
        }
    }
}
