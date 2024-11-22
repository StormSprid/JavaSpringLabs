package stormsprid.emilspring.Service;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;
import stormsprid.emilspring.security.EmailConfig;

import java.io.File;
import java.io.IOException;

@Service
public class EmailService {
    ObjectMapper objectMapper = new ObjectMapper();
    EmailConfig emailConfig = objectMapper.readValue(new File("settings.json"), EmailConfig.class);

    private final String host = emailConfig.getHost();
    private final int port = emailConfig.getPort();
    private final String username = emailConfig.getUsername();
    private final String password = emailConfig.getPassword();

    public EmailService() throws IOException {
    }

    public void sendEmail(String to, String subject, String messageContent) {
        // Настройки SMTP сервера
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        // Создание сессии
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Создание сообщения
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject(subject);
            message.setText(messageContent);

            Transport.send(message);

            System.out.println("Email sent successfully to " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while sending email: " + e.getMessage());
        }
    }
}
