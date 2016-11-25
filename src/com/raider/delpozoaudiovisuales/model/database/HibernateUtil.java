package com.raider.delpozoaudiovisuales.model.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Created by Raider on 06/11/2016.
 * Esta clase es la encargada de crear las sesiones, y la conexión con la base de datos.
 * Para poder realizar las gestiones en DbMethods
 *
 * @since 0.1 Base Alpha
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static Session session;

    /**
     * Crea la factoria de sesiones
     */
    public static void buildSessionFactory() {

        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(
                configuration.getProperties()).buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        //openSession();
    }

    /**
     * Abre una nueva sesión
     */
    public static void openSession() {

        session = sessionFactory.openSession();
    }

    /**
     * Devuelve la sesión actual
     * @return sesión
     */
    public static Session getCurrentSession() {

        if (!session.isOpen()) {
            openSession();
        }

        return session;
    }

    /**
     * Cierra Hibernate
     */
    public static void closeSessionFactory() {

        if (session != null)
            session.close();

        if (sessionFactory != null)
            sessionFactory.close();
    }
}
