package by.bsu.rikz.service.impl;

import by.bsu.rikz.entity.TestInfoContext;
import by.bsu.rikz.service.MailService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Created by USER on 14.12.2016.
 */
@Service
public class MailServiceImpl implements MailService {

    private static final String EMAIL = "enrollee.service@gmail.com";
    private static final String PASSWORD = "rikz_server";
    private String notificationPattern;
    private String resultPattern;

    private Properties sessionProperties = new Properties();

    public MailServiceImpl() {
        sessionProperties.put("mail.smtp.auth", "true");
        sessionProperties.put("mail.smtp.starttls.enable", "true");
        sessionProperties.put("mail.smtp.host", "smtp.gmail.com");
        sessionProperties.put("mail.smtp.port", "587");
        try {
            Path path = Paths.get(getClass().getClassLoader().getResource(
                    "notification_template.txt").toURI());
            List<String> strings = Files.readAllLines(path);
            notificationPattern = strings.stream().collect(Collectors.joining("\n"));

            path = Paths.get(getClass().getClassLoader().getResource(
                    "result_template.txt").toURI());
            strings = Files.readAllLines(path);
            resultPattern = strings.stream().collect(Collectors.joining("\n"));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendNotification(TestInfoContext assignment) {
        String messageText = String.format(notificationPattern, assignment.getName(),
                assignment.getType(),
                assignment.getSubject(),
                assignment.getUniversity(),
                assignment.getAddress(),
                assignment.getRoom(),
                assignment.getDateTime());
        sendMessage(assignment.getEmail(), messageText, "Регистрация на тестирование");
    }

    @Override
    public void sendResult(TestInfoContext assignment) {
        String messageText = String.format(resultPattern, assignment.getName(),
                assignment.getType(),
                assignment.getSubject(),
                assignment.getUniversity(),
                assignment.getAddress(),
                assignment.getDateTime(),
                assignment.getPoints());
        sendMessage(assignment.getEmail(), messageText, "Результаты тестирования");
    }

    @Async
    private void sendMessage(String email, String text, String header) {
        try {
            Session session = Session.getInstance(sessionProperties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL, PASSWORD);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject(header);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
