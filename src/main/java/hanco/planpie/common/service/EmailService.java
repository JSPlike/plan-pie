package hanco.planpie.common.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String senderEmail;

    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        mimeMessage.setFrom(senderEmail);
        mimeMessage.setRecipients(MimeMessage.RecipientType.TO, to);
        mimeMessage.setSubject(subject);
        mimeMessage.setText(body, "utf-8", "html");

        mailSender.send(mimeMessage);
    }
}