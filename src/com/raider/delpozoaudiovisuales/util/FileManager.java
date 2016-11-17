package com.raider.delpozoaudiovisuales.util;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by pmata_ext on 17/11/2016.
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
