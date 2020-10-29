package com.codecool.shop.utility;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailHandler {

    public static void sendMail(String customerEmail) {

        System.out.println("Trying to send message");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String serverEmail = System.getenv("EMAIL");
        String serverPassword = System.getenv("EMAIL_PASSWORD");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(serverEmail, serverPassword);
            }
        });

        try {
            System.out.println("Preparing message");
            Message message = prepareMessage(session, serverEmail, customerEmail);
            Transport.send(message);
            System.out.println("Message sent successfully");
        } catch (MessagingException e) {
            System.out.println("Error while sending email");
        }
    }

    private static Message prepareMessage(Session session, String serverEmail, String customerEmail) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(serverEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(customerEmail));
        message.setSubject("Order confirmation");
        message.setText("We received your order, your products will be delivered soon");
        return message;
    }

}
