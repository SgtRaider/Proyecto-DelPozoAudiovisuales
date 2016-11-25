package com.raider.delpozoaudiovisuales.util;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Raider on 06/11/2016.
 * Clase en la que se gestiona la creación de archivos,
 * sobre todo las rutas de las carpetas de pdfs así,
 * como la estructura, ../"Año"/"Mes"/.. dependiendo,
 * la fecha de creación.
 *
 * @since 0.1 Base Alpha
 */
public class FileManager {

    public File documentos;

    public boolean existsDir(Path dir) {
        return Files.exists(dir);
    }

    public void createProjectStructure() {

        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        documentos = new File(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor");

        if (existsDir(Paths.get(documentos.getPath()))) {

            if (existsDir(Paths.get(documentos.getPath() + File.separator + "Imagenes"))) {

                new File(documentos.getPath() + File.separator + "Imagenes").mkdir();
            }

            if (existsDir(Paths.get(documentos.getPath() + File.separator + "PDFs"))) {

                new File(documentos.getPath() + File.separator + "PDFs").mkdir();
            }

            if (existsDir(Paths.get(documentos.getPath() + File.separator + "Jaspers"))){

                new File(documentos.getPath() + File.separator + "Jaspers").mkdir();
            }

        } else{

            documentos.mkdir();
            new File(documentos.getPath() + File.separator + "Imagenes").mkdir();
            new File(documentos.getPath() + File.separator + "PDFs").mkdir();
            new File(documentos.getPath() + File.separator + "Jaspers").mkdir();
        }
    }

    /**
     * Crea la estructura de directorios año/mes, en caso de existir,
     * devuelve un File con la localización de del directorio mes.
     *
     * @param ano año del documento
     * @param mes mes del documento
     *
     * @return File
     *
     * @since 0.1 Base Alpha
     *
     */
    public File createMonthYearhDir(String ano, String mes) {

        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        documentos = new File(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor");

        File contentDir = new File(documentos.getPath() + File.separator + "PDFs");

        if (existsDir(Paths.get(contentDir.getPath()))){

            if (!existsDir(Paths.get(contentDir.getPath() + File.separator + ano))) {
                new File(contentDir.getPath() + File.separator + ano).mkdir();
            }

            if (!existsDir(Paths.get(contentDir.getPath() + File.separator + mes))) {
                new File(contentDir.getPath() + File.separator + ano + File.separator + mes).mkdir();
            }

            return new File(contentDir.getPath() + File.separator + ano + File.separator + mes);
        } else {

            createProjectStructure();
            return createMonthYearhDir(ano, mes);
        }
    }
}
