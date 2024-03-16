package com.yt.backend.service;
import java.io.File;
import com.yt.backend.model.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailWithAttachmentService {

    private String username = "";
    private String password = "";
    private String host = "";
    private String port = "";

    MailWithAttachmentService() {
    }

    MailWithAttachmentService(String username, String password, String host, String port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public Session getSession() {
        final String username = "alexjaquet@gmail.com";
        final String password = "bxwpypgecjwvvthp";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        return session;
    }

    private String getPassword (String email) {
        final String secretKey = "aslkdfjlkj)/d89723897bc";

        String originalString = email;
        String encryptedString = AES.encrypt(originalString, secretKey) ;
        String decryptedString = AES.decrypt(encryptedString, secretKey) ;


        return encryptedString;
    }

    private String getDecryptedPassword (String email) {
        final String secretKey = "aslkdfjlkj)/d89723897bc";

        String originalString = email;
        String encryptedString = AES.encrypt(originalString, secretKey) ;
        String decryptedString = AES.decrypt(email, secretKey) ;


        return decryptedString;
    }



    public void sendRegistrationMail (Session session, String email, Patient p, String url) throws AddressException, MessagingException, IOException {
        System.out.println(" Sending "  + email);

        Message message = createMailRegistration(session,email,p, url);
        Transport.send(message);
    }

    public void sendPassword (Session session, String email, Patient p) throws AddressException, MessagingException, IOException {
        System.out.println(" Sending "  + email);

        Message message = createMailLostPassword(session,email,p);
        Transport.send(message);
    }

    public void sendMail(Session session,String fileName,String textField, String sizeSelectUE, String sizeSelectUs ,String  sizeCm, String email) throws MessagingException, IOException {
        System.out.println(" Sending "  + fileName);
        Message message = createMail(session,fileName,textField, sizeSelectUE, sizeSelectUs ,  sizeCm, email);
        Transport.send(message);
    }

    public Message createMailLostPassword(Session session, String email,Patient p) throws AddressException, MessagingException, IOException {
        String aHref = "<a href=" + "\"" + "http://localhost:8084/confirmRegistration?encryptedEmail="+ getPassword(email) + "\"" + ">" + "Veuillez confirmer votre inscription à Choose !"+ "</a>";
        String html = "Bonjour " + p.getUsername()  +
                ", <br/> Votre mot de passe pour l'application et le site Choose ! est le suivant " +getDecryptedPassword( p.getPassword());

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("alexjaquet@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(p.getEmail()));
        message.setSubject("Votre mot de passe à Choose !");

        Multipart multipart = new MimeMultipart( "alternative" );

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText( aHref, "utf-8" );

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent( html, "text/html; charset=utf-8" );

        multipart.addBodyPart( textPart );
        multipart.addBodyPart( htmlPart );
        message.setContent( multipart );


        // Required magic (violates principle of least astonishment).
        message.saveChanges();
        return message;
    }
}
