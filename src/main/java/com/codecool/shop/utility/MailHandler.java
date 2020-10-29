package com.codecool.shop.utility;

import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Order;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class MailHandler {

    private final Order order;
    private final List<LineItem> lineItems;
    private final String serverEmail = System.getenv("EMAIL");
    private final String serverPassword = System.getenv("EMAIL_PASSWORD");

    public MailHandler(Order order, List<LineItem> lineItems) {
        this.order = order;
        this.lineItems = lineItems;
    }

    public  void sendMail() {

        System.out.println("Trying to send message");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(serverEmail, serverPassword);
            }
        });

        try {
            System.out.println("Preparing message");
            Message message = prepareMessage(session);
            Transport.send(message);
            System.out.println("Message sent successfully");
        } catch (MessagingException e) {
            System.out.println("Error while sending email");
        }
    }

    private Message prepareMessage(Session session) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(serverEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(order.getEmail()));
        message.setSubject("Order confirmation");
        message.setContent(composeMessage(), "text/html");
        return message;
    }

    private String composeMessage() {
        String message = String.format("<p>Dear %s %s,</p>", order.getFirstName(), order.getLastName()) +
                String.format("<p>We would like to inform you that we received your order (ID: %s) on %s</p>",
                        order.getId(),
                        currentDate()) +
                "<p>Your items:</p>" +
                composeTable() +
                "<p>Your order will be delivered soon. In case of any questions feel free do contact us!</p>" +
                "<p>Kind regards,</p>" +
                "<p>Snail House</p>";
        return message;
    }

    private String composeTable() {
        StringBuilder table = new StringBuilder("<table>");
        table.append("<thead>"
                + "<th style=\"border-bottom: black solid 2px; text-align: center;\">Name</th>"
                + "<th style=\"border-bottom: black solid 2px; text-align: center;\">Unit Price</th>"
                + "<th style=\"border-bottom: black solid 2px; text-align: center;\">Quantity</th>"
                + "<th style=\"border-bottom: black solid 2px; text-align: center;\">Subtotal Price</th>"
                + "</thead>");
        for (LineItem lineItem : lineItems) {
            String newLine = "<tr>";
            newLine += "<td style=\"text-align: center;\">" + lineItem.getName() + "</td>";
            newLine += "<td style=\"text-align: center;\">" + lineItem.getUnitPrice() + "</td>";
            newLine += "<td style=\"text-align: center;\">" + lineItem.getQuantity() + "</td>";
            newLine += "<td style=\"text-align: center;\">" + lineItem.getTotalPrice() + "</td>";
            newLine += "</tr>";
            table.append(newLine);
        }
        table.append("</table>");
        return table.toString();
    }

    private Object currentDate() {
        SimpleDateFormat dnt = new SimpleDateFormat("yyyy.MM.dd.");
        Date date = new Date();
        return dnt.format(date);
    }

}
