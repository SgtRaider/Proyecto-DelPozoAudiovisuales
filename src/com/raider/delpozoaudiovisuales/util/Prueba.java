package com.raider.delpozoaudiovisuales.util;

import com.raider.delpozoaudiovisuales.model.database.logic.DbMethods;
import com.raider.delpozoaudiovisuales.model.objects.MaterialPresupuestoDatasource;
import com.raider.delpozoaudiovisuales.model.objects.Presupuesto;
import com.raider.delpozoaudiovisuales.model.objects.Presupuesto_Material;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.io.FileUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.crypto.NoSuchPaddingException;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;


/**
 * Created by Raider on 04/11/2016.
 */
public class Prueba extends ApplicationFrame{

    public static DbMethods dbm;

    public Prueba(final String title) {
        super(title);


        dbm = new DbMethods();

        JFreeChart chart = createJFreeChart("Mensual");
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);
    }

    public static void main(String args[]) throws NoSuchPaddingException, NoSuchAlgorithmException {

        /*db.buildSessionFactory();
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
        save(prep);*/

        /*String binaryKey = "01100001 00101110 10110111 11000101 10110100 01101001" +
                " 00010011 00100010 11001011 00100000 01001101 11100101 11101111 10111100" +
                " 01101101 00001110 00000100 11011110 00000000 11000000 10101011 01100001" +
                " 01010101 01101000 00000000 10001011 01110010 10011110 01111100 00100010" +
                " 11100101 11111111 00101110 01111111 00001010 00001110 11000001 00011001" +
                " 11100111 10110000 00110001 10001011 11100101 00101110 00011110 10010001" +
                " 00010110 11010101 01110111 01111100 00001111 10111111 00110010 11111001" +
                " 01111101 00010000 01101000 01001110 11000011 01110100 00000111 11010101" +
                " 01010011 01100010 11001011 10010010 10011100 01100001 11101011 10100101" +
                " 01001111 10101101 00111010 10001010 10001001 01110010 10111011 10111101" +
                " 10010110 01011000 10100011 01010011 00011111 10010000 10111101 11101100" +
                " 00110001 01110001 10010001 10101100 00110100 11100000 11010011 11101111" +
                " 01001110 10101000 00001101 01110111 01101100 00011011 01011100 00101111" +
                " 00111101 11100110 01001101 10110111 11010110 11111011 01010101 00110000" +
                " 10100010 10110000 01011001 10010000 10101001 01100110 00010000 10011111" +
                " 10001000 11010010 01101000 11011101 10111011 01111101 00100100 01011011" +
                " 00100100 11000011";

        StringBuffer sb = new StringBuffer();

        for (String key:binaryKey.split(" ")) {
            int charCode = Integer.parseInt(key, 2);
            sb.append(new Character((char)charCode).toString());
        }

        System.out.println(sb.toString());*/

        /*email("Esto es una prueba,\n Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla faucibus vulputate nunc quis blandit. Vivamus lacinia nisi tortor, in viverra orci blandit quis. Phasellus viverra velit eu laoreet scelerisque. Vestibulum vel ligula nec nisl maximus ultrices. Nullam scelerisque orci dolor, sed euismod nisl euismod in. Donec rhoncus auctor ligula, nec luctus eros auctor congue. Praesent vehicula libero at felis sollicitudin efficitur. Ut eget mauris euismod, gravida mauris at, semper nibh. Nulla et nunc nisl. Morbi eget est rutrum, porta purus ac, suscipit turpis." +
                "\n" +
                "Aenean vel lacinia mauris. Sed libero tortor, pellentesque a augue ut, congue egestas dui. Vestibulum posuere enim eget velit condimentum, vitae lobortis neque tincidunt. Quisque lobortis faucibus lorem ut lacinia. Ut suscipit ante a urna finibus, quis finibus ex pharetra. Aliquam eget pellentesque ex, nec molestie mauris. Nulla et nisl semper, ultricies arcu at, tempor quam. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec eu rhoncus tortor." +
                "\n" +
                "Integer vel ante et nisl bibendum pulvinar et nec mi. Vivamus ac purus purus. Aenean tincidunt nibh risus, vitae sodales quam viverra vitae. Vivamus ullamcorper mi vitae mauris congue suscipit. Nam tristique mi elit, tincidunt hendrerit lectus congue eu. Duis eleifend neque in neque finibus, nec efficitur est porta. Aliquam non rutrum lacus, condimentum aliquet mi. Duis vel massa vel massa posuere luctus eget sit amet diam. Duis pellentesque vestibulum mi, vitae facilisis nisl volutpat at. Etiam in vulputate mi, at posuere metus.\n" +
                "Fin de la prueba");*/

        Prueba prueba = new Prueba("EL TITULO");
        prueba.pack();
        RefineryUtilities.centerFrameOnScreen(prueba);
        prueba.setVisible(true);

        /*try {
            report();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private static JFreeChart createJFreeChart(String type) {

        Date lastDate = dbm.getLastFactura();
        Date firstDate = dbm.getFirstFactura();

        Long intervalo = 7 * 24 * 60 * 60 * 1000L;
        String x = "";

        TimeSeries gananciasPagadas = new TimeSeries("Ganancias Pagado");
        TimeSeries gananciasNoPagadas = new TimeSeries("Ganancias Sin Pagar");
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        switch (type) {

            case "Mensual":


                for (int i = getYear(firstDate); i <= getYear(lastDate); i++) {
                    for (int j = 1; j <= 12; j++) {
                        gananciasPagadas.add(new Month(j, i), dbm.getGananciasMesAno(j, i, true));
                        gananciasNoPagadas.add(new Month(j, i), dbm.getGananciasMesAno(j, i, false));
                    }
                }

                x = "Meses";

                break;

            case "Trimestral":

                for (int i = getYear(firstDate); i <= getYear(lastDate); i++) {
                    for (int j = 1; j <= 4; j++) {
                        gananciasPagadas.add(new Month(((j * 3) - 2), i), dbm.getGananciasTrimestralesAno(j, i, true));
                        gananciasNoPagadas.add(new Month(((j * 3) - 2), i), dbm.getGananciasTrimestralesAno(j, i, false));
                    }
                }

                x = "Trimestres";

                break;

            case "Anual":

                for (int i = getYear(firstDate); i <= getYear(lastDate); i++) {
                    gananciasPagadas.add(new Year(i), dbm.getGananciasAno(i, true));
                    gananciasNoPagadas.add(new Year(i), dbm.getGananciasAno(i, false));
                }

                x = "Años";

                break;
        }

        dataset.addSeries(gananciasPagadas);
        dataset.addSeries(gananciasNoPagadas);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Gráfico " + type,
                x,
                "Ingresos en Euros",
                dataset,
                true,
                true,
                false
        );


        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);

        return chart;
    }

    private static int getTrimestre(Date date) {

        int mes = Integer.parseInt(new SimpleDateFormat("MM").format(date));
        int trimestre = 0;

        if (mes > 1 & mes <= 3) {

            trimestre = 1;
        } else {

            if (mes > 3 & mes <= 6) {

                trimestre = 2;
            } else {

                if (mes > 6 & mes <= 9) {

                    trimestre = 3;
                } else {

                    if (mes > 9 & mes <= 12) {

                        trimestre = 4;
                    }
                }
            }
        }

        return trimestre;
    }

    private static int getYear(Date date) {
        return  Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
    }

    private static int getMonth(Date date) {
        return  Integer.parseInt(new SimpleDateFormat("MM").format(date));
    }

   /* public static <T> Object save(T entity) {

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
    }*/

   public static void report() throws JRException, Exception {


       DbMethods dbMethods = new DbMethods();

       Presupuesto presupuesto = (Presupuesto) dbMethods.list("presupuesto", new HashMap<>()).get(8);

       MaterialPresupuestoDatasource materialPresupuestoDatasource = new MaterialPresupuestoDatasource();

       for (Presupuesto_Material presupuesto_material : presupuesto.getPresupuestoMaterial()) {

           materialPresupuestoDatasource.addPresupuestomaterial(presupuesto_material);
       }

       PrintReport pr = new PrintReport();
       pr.printReport(presupuesto.getFecha_emision(), "presupuesto", presupuesto, true);

       /*JFileChooser fr = new JFileChooser();
       FileSystemView fw = fr.getFileSystemView();
       File file = new File(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor"+ File.separator + "Jaspers" + File.separator + "Presupuesto_Venta_jasper_report.jasper");
       JasperReport report = (JasperReport) JRLoader.loadObject(file);
       Map<String, Object> parameters = new HashMap<>();
       //TODO no variar no de presupuesto ni fecha de validez al modificar
       parameters.put("nopresupuesto", presupuesto.getNo_presupuesto());
       parameters.put("fecha_validez", presupuesto.getFecha_validez());
       parameters.put("fecha_emision", presupuesto.getFecha_emision());
       parameters.put("fecha_inicio", presupuesto.getFecha_inicio());
       parameters.put("fecha_fin", presupuesto.getFecha_fin());
       parameters.put("iva", Float.parseFloat(Preferences.getPropertiesUnprotected().get("util.iva")));
       parameters.put("empresaCliente", presupuesto.getCliente().getEmpresa());
       parameters.put("cifCliente", presupuesto.getCliente().getCif());
       parameters.put("direccionCliente", presupuesto.getCliente().getDireccion());
       parameters.put("cpCliente", presupuesto.getCliente().getCp());
       parameters.put("ciudadCliente", presupuesto.getCliente().getCiudad());
       parameters.put("imagePath", new File(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor"+ File.separator + "Jaspers" + File.separator + "logo-delpozo.jpg").getCanonicalPath());
       //TODO Meter imagenes como canonical path
       parameters.put("observaciones", presupuesto.getObservaciones());
       JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, materialPresupuestoDatasource);
       JasperViewer.viewReport(jasperPrint);*/
   }

    private static final String SMTP_HOST_NAME = "127.0.0.1";
    private static final String SMTP_AUTH_USER = "pruebas@delpozoaudiovisuales.com";
    private static final String SMTP_AUTH_PWD  = "riu.2016";

    public static void email(String bodyText) {

        // Recipient's email ID needs to be mentioned.
        String to = "panopliaespartana@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "pruebas@delpozoaudiovisuales.com";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = new Properties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", SMTP_HOST_NAME);
        properties.setProperty("mail.smtp.port", "2525");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Authenticator auth = new SMTPAuthenticator();

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties, auth);
        session.setDebug(true);


        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Send the actual HTML message, as big as you like
            String str = null;
            JFileChooser fr = new JFileChooser();
            FileSystemView fw = fr.getFileSystemView();
            File file = new File(fw.getDefaultDirectory() + File.separator + "email.html");
            str = FileUtils.readFileToString(file, "UTF-8");

            str = str.replace("[texto]", transformContent(bodyText));

            messageBodyPart.setContent(str , "text/html; charset=utf-8");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            String filename = "C:\\Users\\asrae\\Downloads\\5009_COE.pdf";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("5009_COE.pdf");
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource("C:\\Users\\asrae\\Documents\\logo-delpozo.png");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID","<image>");

            // add it
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class SMTPAuthenticator extends javax.mail.Authenticator
    {
        public PasswordAuthentication getPasswordAuthentication()
        {
            String username = SMTP_AUTH_USER;
            String password = SMTP_AUTH_PWD;
            return new PasswordAuthentication(username, password);
        }
    }

    private static String transformContent(String content) {

        StringBuffer sb = new StringBuffer();

        for (String part:content.split("\n")) {
            if (!part.isEmpty()) sb.append("<p>" + part + "</p>");
        }

        return sb.toString();
    }
}
