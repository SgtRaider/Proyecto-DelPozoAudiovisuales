package com.raider.delpozoaudiovisuales.model.logic;

import com.raider.delpozoaudiovisuales.model.objects.*;
import com.raider.delpozoaudiovisuales.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        session.saveOrUpdate(entity);

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

    public List list(String object, HashMap<String,String> camposString) {

        Session session = db.getCurrentSession();

        String sql;
        Query query;
        StringBuffer sb = new StringBuffer();
        List<Cliente> clienteList;
        List<Factura> facturaList;
        List<Pedido> pedidoList;
        List<Material> materialList;
        List<Presupuesto> presupuestoList;

        switch (object) {

            case "cliente":

                if (camposString.isEmpty()) {

                    clienteList = session.createQuery("FROM Cliente WHERE 1=1").list();
                } else {

                    sql = "FROM Cliente as c WHERE 1=1";
                    sb.append(sql);
                    sb.append(addLikeSearch(object, camposString));
                    query = session.createQuery(sb.toString());
                    clienteList = query.list();
                }

                return clienteList;

            case "factura":

                if (camposString.isEmpty()) {
                    sql = "FROM Factura as f WHERE 1=1";
                    query = session.createQuery(sql);
                    facturaList = query.list();
                    return facturaList;

                } else {

                    sql = "FROM Factura as f WHERE 1=1";
                    sb.append(sql);
                    sb.append(addLikeSearch(object, camposString));
                    query = session.createQuery(sb.toString());
                    facturaList = query.list();
                    return facturaList;
                }

            case "material":

                if (camposString.isEmpty()) {
                    sql = "FROM Material as m WHERE 1=1";
                    query = session.createQuery(sql);
                    materialList = query.list();
                    return materialList;

                } else {

                    sql = "FROM Material as m WHERE 1=1";
                    sb.append(sql);
                    sb.append(addLikeSearch(object, camposString));
                    query = session.createQuery(sb.toString());
                    materialList = query.list();
                    return materialList;
                }

            case "pedido":

                if (camposString.isEmpty()) {
                    sql = "FROM Pedido as p WHERE 1=1";
                    query = session.createQuery(sql);
                    pedidoList = query.list();
                    return pedidoList;

                } else {

                    sql = "FROM Pedido as p WHERE 1=1";
                    sb.append(sql);
                    sb.append(addLikeSearch(object, camposString));
                    query = session.createQuery(sb.toString());
                    pedidoList = query.list();
                    return pedidoList;
                }

            case "presupuesto":

                if (camposString.isEmpty()) {
                    sql = "FROM Presupuesto as p WHERE 1=1";
                    query = session.createQuery(sql);
                    presupuestoList = query.list();
                    return presupuestoList;

                } else {

                    sql = "FROM Presupuesto as p WHERE 1=1";
                    sb.append(sql);
                    sb.append(addLikeSearch(object, camposString));
                    query = session.createQuery(sb.toString());
                    presupuestoList = query.list();
                    return presupuestoList;
                }

            default:
                return null;
        }
    }

    private String addLikeSearch(String tabla, HashMap<String,String> camposString) {

        StringBuffer sb = new StringBuffer();

        tabla = tabla.substring(0, 1);

        int count = 0;

        for(Map.Entry<String,String> entry :camposString.entrySet()) {

            String key = entry.getKey();
            String value = entry.getValue();

            if (count == 0) sb.append(" and (" + tabla  + "." + key + " LIKE " + "\'" + "%" + value + "%" + "\'");
            else sb.append(" or " + tabla  + "." + key + " LIKE " + "\'" + "%" + value + "%" + "\'");

            count++;
        }

        sb.append(")");

        return sb.toString();
    }

    private void loadObject(String object) {

        //TODO

        switch (object) {

        }
    }
}
