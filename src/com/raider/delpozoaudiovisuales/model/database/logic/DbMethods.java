package com.raider.delpozoaudiovisuales.model.database.logic;

import com.raider.delpozoaudiovisuales.model.database.HibernateUtil;
import com.raider.delpozoaudiovisuales.model.objects.*;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Raider on 06/11/2016.
 * Esta clase es la encargada de realizar la interacción con la base de datos.
 *
 * @since 0.1 Base Alpha
 */
public class DbMethods {

    public static HibernateUtil db = new HibernateUtil();

    /**
     * Constructor el cual inicia una sesión para poder acceder a la base de datos.
     *
     * Clases:
     * @see HibernateUtil
     *
     * @since 0.1 Base Alpha
     *
     */
    public DbMethods() {

        db.buildSessionFactory();
        db.openSession();
    }

    /**
     * Metodo el cual guarda o actualiza, dependiendo si existe el objeto,
     * en la base de datos o no.
     *
     * @param entity Objeto de la clase a guardar o updatear.
     *
     * @return Devuelve la clase a guardar
     *
     * @see HibernateUtil
     *
     * @since 0.1 Base Alpha
     *
     */
    public <T> Object save(T entity) {

        Session session = db.getCurrentSession();

        session.beginTransaction();

        session.saveOrUpdate(entity);

        session.getTransaction().commit();

        return entity;
    }

    /**
     * Metodo el cual actualiza, si existe el objeto,
     * en la base de datos.
     *
     * @param entity Objeto de la clase a updatear.
     *
     * @return Devuelve la clase a modificar
     *
     * @see HibernateUtil
     *
     * @since 0.1 Base Alpha
     *
     * @deprecated
     */
    public <T> Object update(T entity) {

        Session session = db.getCurrentSession();

        session.beginTransaction();

        session.merge(entity);

        session.getTransaction().commit();

        return entity;

    }

    /**
     * Metodo el cual elimina el objeto de la base de datos,
     * en caso de que exista.
     *
     * @param entity Objeto de la clase a borrar.
     *
     * @see HibernateUtil
     *
     * @since 0.1 Base Alpha
     *
     */
    public <T> void delete(T entity) {

        Session session = db.getCurrentSession();

        session.beginTransaction();

        session.delete(entity);

        session.getTransaction().commit();
    }

    /**
     *
     * Método que lista un conjunto de resultados, dependiendo los parámetros.
     *
     * @param object String que determina la clase que se quiere consultar(el FROM de la consulta).
     *
     * @param camposString HashMap el cual sirve para determinar que se quiere consultar,
     *                     solo válido para strings, y para hacer consultas tipo LIKE.
     *                     Óptimo para búsquedas de strings.
     *                     En caso de no querer consultar nada inicializar el HashMap vacio.
     *
     * @return Devuelve un objeto de la clase List, con el conjunto de valores recogidos. En caso de que el parametro objeto sea erroneo, devuelve null.
     *
     * @see HibernateUtil
     *
     * @since 0.1 Base Alpha
     *
     */
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

    /**
     *
     * Método que te devuelve un Object sin castear de la clase que corresponda.
     *
     * @param type String que determina la clase que se quiere consultar(el FROM de la consulta).
     *
     * @param id id del objeto que quieres obtener.
     *
     * @return Object
     *
     * @see HibernateUtil
     *
     * @since 0.1 Base Alpha
     *
     */
    public Object getById(String type, int id) {

        String sql;
        Query query;
        Session session = db.getCurrentSession();

        switch (type) {

            case "factura":

                sql = "FROM Factura as f WHERE f.id = " + id;
                query = session.createQuery(sql);
                Factura factura = (Factura) query.uniqueResult();
                return factura;

            case "pedido":

                sql = "FROM Pedido as p WHERE p.id = " + id;
                query = session.createQuery(sql);
                Pedido pedido = (Pedido) query.uniqueResult();
                return pedido;

            case "presupuesto":

                sql = "FROM Presupuesto as p WHERE p.id = " + id;
                query = session.createQuery(sql);
                Presupuesto presupuesto = (Presupuesto) query.uniqueResult();
                return presupuesto;

            default:
                return null;
        }
    }

    /**
     *
     * Método que te devuelve la parte del where de una consulta, sirve para hacer búsquedas.
     *
     * @param tabla tabla de los campos a buscar
     *
     * @param camposString map con el campo de búsqueda, y el valor a cotejar.
     *
     * @return String con el where de la consulta, preparado para unirlo al FROM
     *
     * @see HibernateUtil
     *
     * @since 0.1 Base Alpha
     *
     */
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

    /**
     *
     * Método que busca si existe el usuario en la base de datos, en caso de existir lo devuelve,
     * en el caso contrario, devuelve un null.
     *
     * @param user usuario a cotejar.
     *
     * @param pass contraseña a cotejar.
     *
     * @return devuelve el usuario o un null en caso de no existir.
     *
     * @see HibernateUtil
     *
     * @since 0.1 Base Alpha
     *
     */
    public Usuario login(String user, String pass) {

        Session session = db.getCurrentSession();

        String sql;
        Query query;

        sql = "FROM Usuario WHERE username = '" + user + "' and pass = md5('" + pass + "')";
        query = session.createQuery(sql);
        Usuario usuario = (Usuario) query.uniqueResult();

        return usuario;
    }

    /**
     *
     * Método que devuelve el ultimo id de cada tipo de documento, para asignarlo como no de documento.
     *
     * @param op cada opción, corresponde a un tipo de documento.
     *
     * @return int
     *
     * @see HibernateUtil
     *
     * @since 0.1 Base Alpha
     *
     */
    public int lastID(int op) {

        Session session = db.getCurrentSession();

        String sql = "";
        Query query;
        int id = 1;

        switch (op) {

            case 1:

                sql = "SELECT p.id FROM Presupuesto as p ORDER BY p.id DESC LIMIT 1";
                break;

            case 2:

                sql = "SELECT p.id FROM Pedido as p ORDER BY p.id DESC LIMIT 1";
                break;

            case 3:

                sql = "SELECT f.id FROM Factura as f ORDER BY f.id DESC LIMIT 1";
                break;
        }

        if (sql != ""){

            query = session.createQuery(sql);
            Object result = query.setMaxResults(1).uniqueResult();

            if(result == null){
                id = 1;
            } else {
                id = (((int) result) + 1);
            }
        }

        return id;
    }

    /**
     *
     * Método que devuelve la primera factura de la base de datos, en orden temporal.
     *
     * @return date
     *
     * @see HibernateUtil
     *
     * @since 0.1 Base Alpha
     *
     */
    public Date getFirstFactura() {

        Session session = db.getCurrentSession();
        Query query;
        query = session.createSQLQuery("SELECT getFirstFechaPago()");
        Date result = (Date) query.setMaxResults(1).uniqueResult();

        return result;
    }

    /**
     *
     * Método que devuelve la última factura de la base de datos, en orden temporal.
     *
     * @return date
     *
     * @see HibernateUtil
     *
     * @since 0.1 Base Alpha
     *
     */
    public Date getLastFactura() {

        Session session = db.getCurrentSession();
        Query query;
        query = session.createSQLQuery("SELECT getLastFechaPago()");
        Date result = (Date) query.setMaxResults(1).uniqueResult();

        return result;
    }

    /**
     *
     * Método que llama a la función almacenada gananciasTrimestreAno, la cual de vuelve,
     * devuelve las ganancias de un trimestre y un año determinado, dependiendo si han sido pagadas,
     * o no.
     *
     * @param trimestre del cual se quieren saber las ganancias.
     * @param ano del cual se quieren saber las ganancias.
     * @param ispagado si están pagadas las facturas, o no.
     *
     * @return float
     *
     * @see HibernateUtil
     *
     * @since 0.1 Base Alpha
     *
     */
    public float getGananciasTrimestralesAno(int trimestre, int ano, boolean ispagado) {

        Session session = db.getCurrentSession();
        int pagado = 0;
        float result = 0;
        Query query;

        if (ispagado) {
            pagado = 1;
        } else {
            pagado = 0;
        }

        query = session.createSQLQuery("SELECT gananciasTrimestreAno(" + trimestre + ", " + ano + ", " + pagado + ")");

        if (query.setMaxResults(1).uniqueResult() != null) {
            result = (float) query.setMaxResults(1).uniqueResult();
        }

        return result;
    }

    /**
     *
     * Método que llama a la función almacenada gananciasAno, la cual de vuelve,
     * devuelve las ganancias de un año determinado, dependiendo si han sido pagadas,
     * o no.
     *
     * @param ano del cual se quieren saber las ganancias.
     * @param ispagado si están pagadas las facturas, o no.
     *
     * @return float
     *
     * @see HibernateUtil
     *
     * @since 0.1 Base Alpha
     *
     */
    public float getGananciasAno(int ano, boolean ispagado) {

        Session session = db.getCurrentSession();
        int pagado = 0;
        float result = 0;
        Query query;

        if (ispagado) {
            pagado = 1;
        } else {
            pagado = 0;
        }

        query = session.createSQLQuery("SELECT gananciasAno(" + ano + ", " + pagado + ")");

        if (query.setMaxResults(1).uniqueResult() != null) {
            result = (float) query.setMaxResults(1).uniqueResult();
        }

        return result;
    }

    /**
     *
     * Método que llama a la función almacenada gananciasMesAno, la cual de vuelve,
     * devuelve las ganancias de un mes y un año determinado, dependiendo si han sido pagadas,
     * o no.
     *
     * @param mes del cual se quieren saber las ganancias.
     * @param ano del cual se quieren saber las ganancias.
     * @param ispagado si están pagadas las facturas, o no.
     *
     * @return float
     *
     * @see HibernateUtil
     *
     * @since 0.1 Base Alpha
     *
     */
    public float getGananciasMesAno(int mes, int ano, boolean ispagado) {

        Session session = db.getCurrentSession();
        int pagado = 0;
        float result = 0;
        Query query;

        if (ispagado) {
            pagado = 1;
        } else {
            pagado = 0;
        }

        query = session.createSQLQuery("SELECT gananciasMesAno(" + mes + ", " + ano + ", " + pagado + ")");

        if (query.setMaxResults(1).uniqueResult() != null) {
            result = (float) query.setMaxResults(1).uniqueResult();
        }

        return result;
    }
}
