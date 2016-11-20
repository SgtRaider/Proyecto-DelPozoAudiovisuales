package com.raider.delpozoaudiovisuales.util;

import org.apache.commons.io.FileUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by raider on 11/11/2016.
 */
public class Email {

    private static final String SMTP_HOST_NAME = "127.0.0.1";
    private static final String SMTP_AUTH_USER = "pruebas@delpozoaudiovisuales.com";
    private static final String SMTP_AUTH_PWD  = "riu.2016";
    private static final JFileChooser fr = new JFileChooser();
    private static final FileSystemView fw = fr.getFileSystemView();
    private static final FileManager fm = new FileManager();

    public void enviarEmail(String to, String bodyText, String asunto, Date docDate, String type, String empresaCliente) throws MessagingException, IOException {

        File rootFile = new File(fw.getDefaultDirectory() + File.separator + "DelPozo_Gestor");

        if (!fm.existsDir(Paths.get(rootFile.getPath()))) {
            fm.createProjectStructure();
        }

        String from = "pruebas@delpozoaudiovisuales.com";

        Properties properties = new Properties();

        properties.setProperty("mail.smtp.host", SMTP_HOST_NAME);
        properties.setProperty("mail.smtp.port", "2525");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Authenticator auth = new SMTPAuthenticator();
        Session session = Session.getDefaultInstance(properties, auth);
        session.setDebug(false);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(asunto);

        BodyPart messageBodyPart = new MimeBodyPart();
        String str = null;
        File file = new File(rootFile + File.separator + "Jaspers" + File.separator + "email.html");
        str = FileUtils.readFileToString(file, "UTF-8");
        str = str.replace("[texto]", transformContent(bodyText));
        messageBodyPart.setContent(str , "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();

        empresaCliente = empresaCliente.substring(0, 1).toUpperCase() + empresaCliente.substring(1);
        type = type.substring(0, 1).toUpperCase() + type.substring(1);
        String dia = new SimpleDateFormat("dd").format(docDate).toString();
        String filename = fm.createMonthYearhDir(new SimpleDateFormat("yyyy").format(docDate).toString(), new SimpleDateFormat("MM").format(docDate).toString()).getPath() + File.separator + type + "_" + empresaCliente + "_" + dia + ".pdf";

        String name = type.replace("_agrupado", "") + ".pdf";

        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(name);
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource(rootFile + File.separator + "Imagenes" + File.separator + "logo-delpozo.png");
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID","<image>");

        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);

        Transport.send(message);
        System.out.println("Sent message successfully....");
    }

    private static class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            String username = SMTP_AUTH_USER;
            String password = SMTP_AUTH_PWD;
            return new PasswordAuthentication(username, password);
        }
    }

    private static String transformContent(String content) {

        StringBuffer sb = new StringBuffer();

        if (content.contains("\n")){
            for (String part:content.split("\n")) {
                if (!part.isEmpty()) sb.append("<p>" + part + "</p>");
            }
        } else {
            sb.append("<p>" + content + "</p>");
        }

        return sb.toString();
    }
}
