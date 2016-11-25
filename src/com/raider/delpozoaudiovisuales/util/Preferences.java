package com.raider.delpozoaudiovisuales.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;


/**
 * Created by Raider on 06/11/2016.
 * Clase en la cual se controla la creación, y modificación de las preferencias.
 * Así como su seguridad.
 *
 * @since 0.1 Base Alpha
 */
public class Preferences {

    private static final JFileChooser fr = new JFileChooser();
    private static final FileSystemView fw = fr.getFileSystemView();
    private static final String PATH = fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor" + File.separator + "config.props";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static BASE64Encoder enc = new BASE64Encoder();
    private static BASE64Decoder dec = new BASE64Decoder();

    /**
     * Método que se encarga de encriptar las cadenas de texto de las preferencias.
     *
     * @param text texto sin encriptar
     *
     * @return String encriptado
     *
     * Clases:
     * @see BASE64Encoder
     *
     * @since 0.1 Base Alpha
     *
     */
    public static String base64encode(String text) {
        try {
            return enc.encode(text.getBytes(DEFAULT_ENCODING));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * Método que se encarga de desencriptar las cadenas de texto de las preferencias.
     *
     * @param text texto encriptada
     *
     * @return String desencriptada
     *
     * Clases:
     * @see BASE64Decoder
     *
     * @since 0.1 Base Alpha
     *
     */
    public static String base64decode(String text) {
        try {
            return new String(dec.decodeBuffer(text), DEFAULT_ENCODING);
        } catch (IOException e) {
            return null;
        }
    }

    @Deprecated
    public static boolean existsProperties() {

        InputStream input = null;

        try {

            JFileChooser fr = new JFileChooser();
            FileSystemView fw = fr.getFileSystemView();

            input = new FileInputStream(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor" + File.separator + "config.props");

        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        } finally {

            if (input != null) {
                try {

                    input.close();
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    /**
     * Método que crea e inicializa las propiedades, encriptadas.
     *
     * @since 0.1 Base Alpha
     *
     */
    public static void initializeProperties() {

        Properties prop = new Properties();
        OutputStream output = null;

        try {

            JFileChooser fr = new JFileChooser();
            FileSystemView fw = fr.getFileSystemView();

            output = new FileOutputStream(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor" + File.separator + "config.props");

            prop.setProperty( base64encode("db.host"), base64encode("localhost"));
            prop.setProperty( base64encode("db.name"), base64encode("proyectodam"));
            prop.setProperty( base64encode("db.user"), base64encode("root"));
            prop.setProperty( base64encode("db.password"), base64encode(""));
            prop.setProperty( base64encode("util.iva"), base64encode("0.21"));
            prop.setProperty( base64encode("util.SecurityKey"), base64encode("Fzi43sdx4b5aafREu9cT"));

            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Método modifica las propiedades.
     *
     * @param host
     * @param name
     * @param user
     * @param pass
     * @param iva
     *
     * @since 0.1 Base Alpha
     *
     */
    public static void setProperties(String host, String name, String user, String pass, String iva) {

        Properties prop = new Properties();
        OutputStream output = null;

        try {

            JFileChooser fr = new JFileChooser();
            FileSystemView fw = fr.getFileSystemView();

            output = new FileOutputStream(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor" + File.separator + "config.props");

            prop.setProperty( base64encode("db.host"), base64encode(host));
            prop.setProperty( base64encode("db.name"), base64encode(name));
            prop.setProperty( base64encode("db.user"), base64encode(user));
            prop.setProperty( base64encode("db.password"), base64encode(pass));
            prop.setProperty( base64encode("util.iva"), base64encode(iva));
            prop.setProperty( base64encode("util.SecurityKey"), base64encode("Fzi43sdx4b5aafREu9cT"));

            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Método que devuelve las propiedades desencriptadas.
     *
     * @return HashMap con las propiedades desencriptadas
     *
     * @since 0.1 Base Alpha
     *
     */
    public static HashMap<String, String> getPropertiesUnprotected() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

                HashMap<String, String> properties = new HashMap<>();

                input = getInputStream(PATH);

                prop.load(input);

                properties.put("db.host", base64decode(prop.getProperty(base64encode("db.host"))));
                properties.put("db.name", base64decode(prop.getProperty(base64encode("db.name"))));
                properties.put("db.user", base64decode(prop.getProperty(base64encode("db.user"))));
                properties.put("db.password", base64decode(prop.getProperty(base64encode("db.password"))));
                properties.put("util.iva", base64decode(prop.getProperty(base64encode("util.iva"))));
                properties.put("util.SecurityKey", base64decode(prop.getProperty(base64encode("util.SecurityKey"))));

                return properties;

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {

            if (input != null) {
                try {

                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * Método que devuelve las propiedades encriptadas.
     *
     * @return HashMap con las propiedades encriptadas
     *
     * @since 0.1 Base Alpha
     *
     */
    public static HashMap<String, String> getPropertiesProtected() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            HashMap<String, String> properties = new HashMap<>();

            input = getInputStream(PATH);

            prop.load(input);

            properties.put("db.host", prop.getProperty(base64encode("db.host")));
            properties.put("db.name", prop.getProperty(base64encode("db.name")));
            properties.put("db.user", prop.getProperty(base64encode("db.user")));
            properties.put("db.password", prop.getProperty(base64encode("db.password")));
            properties.put("util.iva", prop.getProperty(base64encode("util.iva")));
            properties.put("util.SecurityKey", prop.getProperty(base64encode("util.SecurityKey")));

            return properties;

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {

            if (input != null) {
                try {

                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    /**
     * Método que un FileInputStream de las configuraciones, en caso de que existan,
     * en caso contrario, crea la estructura de directorios del proyecto crea las propiedades
     * y vuelve a enviarlas.
     *
     * @return HashMap con las propiedades encriptadas
     *
     * @since 0.1 Base Alpha
     *
     */
    private static FileInputStream getInputStream(String path) throws FileNotFoundException {

        File file = new File(path);
        FileManager fm = new FileManager();
        FileInputStream input = null;

        if (fm.existsDir(Paths.get(file.getPath()))) {

            input = new FileInputStream(file.getPath());
        } else {

            fm.createProjectStructure();
            initializeProperties();

            return getInputStream(path);
        }

        return input;
    }
}
