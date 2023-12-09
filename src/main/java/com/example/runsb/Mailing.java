package com.example.runsb;



import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mailing {



    void envoyerEmailConfirmation(String destinataire) {

        String utilisateur = "run.customerservice@outlook.fr";
        String motDePasse = "Run123run123";


        String sujet = "Confirmation d'inscription RUN";
        String corps = "Merci pour votre inscription à RUN. Votre compte a été créé avec succès.";


        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Session SMTP
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(utilisateur, motDePasse);
            }
        });

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(utilisateur));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(corps);

            // Envoi du message
            Transport.send(message);

            System.out.println("E-mail de confirmation envoyé avec succès.");

        } catch (MessagingException e) {
            // Gérez l'exception de manière appropriée (enregistrez-la, renvoyez une erreur, etc.)
            e.printStackTrace();
        }
    }


    void envoyerEmailContact(String message1, String name, String mail, String numero) {

        String utilisateur = "run.customerservice@outlook.fr";
        String motDePasse = "Run123run123";
        String destinataire = "run.customerservice@outlook.fr";

        String sujet = "Contacted by : "+name;
        String corps = "Mail: "+mail+" Message: "+message1+" Numero: "+numero;


        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Session SMTP
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(utilisateur, motDePasse);
            }
        });

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(utilisateur));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(corps);

            // Envoi du message
            Transport.send(message);

            System.out.println("E-mail de confirmation envoyé avec succès.");

        } catch (MessagingException e) {
            // Gérez l'exception de manière appropriée (enregistrez-la, renvoyez une erreur, etc.)
            e.printStackTrace();
        }
    }

}
