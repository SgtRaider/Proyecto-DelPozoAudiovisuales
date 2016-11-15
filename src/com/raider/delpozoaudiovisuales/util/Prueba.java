package com.raider.delpozoaudiovisuales.util;

import com.raider.delpozoaudiovisuales.model.objects.Cliente;
import com.raider.delpozoaudiovisuales.model.objects.Material;
import com.raider.delpozoaudiovisuales.model.objects.Presupuesto;
import com.raider.delpozoaudiovisuales.model.objects.Presupuesto_Material;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import org.apache.commons.io.FileUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;


/**
 * Created by Raider on 04/11/2016.
 */
public class Prueba {

    public static HibernateUtil db = new HibernateUtil();

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

   public void report() {

   }

    public class materialPresupuestoDatasource implements JRDataSource{

        private List<Presupuesto_Material> presupuestoMaterialList = new ArrayList<>();
        private int indiceParticipanteActual = -1;

        @Override
        public boolean next() throws JRException {
            return ++indiceParticipanteActual < presupuestoMaterialList.size();
        }

        @Override
        public Object getFieldValue(JRField jrField) throws JRException {
            Object valor = null;

            if(jrField.getName().equals("dias")) {

                valor = presupuestoMaterialList.get(indiceParticipanteActual).getDias_uso();
            } else if(jrField.getName().equals("cantidad")){
                valor = presupuestoMaterialList.get(indiceParticipanteActual).getCantidad();
            } else if(jrField.getName().equals("modelo")){
                valor = presupuestoMaterialList.get(indiceParticipanteActual).getMaterial().getModelo();
            } else if(jrField.getName().equals("nombre_material")){
                valor = presupuestoMaterialList.get(indiceParticipanteActual).getMaterial().getNombre();
            } else if(jrField.getName().equals("precio_unidad")){
                valor = presupuestoMaterialList.get(indiceParticipanteActual).getMaterial().getPrecio_dia();
            }

            return valor;
        }
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
