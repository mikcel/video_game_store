package mailing;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.MessagingException;

public class Mailing {

    private static Mailing instance = new Mailing();

    private Mailing() {

    }

    public static Mailing getInstance() {
        return instance;
    }

    public boolean sendEmail(String recepientEmail, String emailSubject, String messageBody, String contentType) {

        final String username = "video387soen@gmail.com";
        final String password = "soenvideo";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recepientEmail));
            message.setSubject(emailSubject);
            message.setContent(messageBody, contentType);

            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
