package hanco.planpie.common.service;

import hanco.planpie.common.config.cache.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String senderEmail;

    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    private final RedisUtil redisUtil;

    public void sendEmail(String to) throws MessagingException {
        // 인증코드
        String verificationCode = redisUtil.createdCertifyNum();
        String message = build(verificationCode);
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // private Email
        mimeMessage.setFrom(senderEmail);
        // to Email
        mimeMessage.setRecipients(MimeMessage.RecipientType.TO, to);

        mailSender.send(mimeMessage);
    }

    public String build(String verifyNum) {
        Context context = new Context();
        context.setVariable("verifyNum", verifyNum);

        // email Template Process
        return templateEngine.process("mailTemplate", context);
    }
}