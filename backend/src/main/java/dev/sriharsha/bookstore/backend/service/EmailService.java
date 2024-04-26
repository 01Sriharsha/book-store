package dev.sriharsha.bookstore.backend.service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import dev.sriharsha.bookstore.backend.enums.EmailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${application.services.mail.ADMIN_MAIL}")
    private String ADMIN_MAIL;

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(
            String to,
            String username,
            EmailTemplate emailTemplate,
            String activationCode,
            String subject) throws MessagingException {
        String templateName = emailTemplate == null
                ? EmailTemplate.CONFIRAMTION.toString()
                : emailTemplate.toString();

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.toString());

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("activationCode", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        // automatically fetches the template file using template name and process it
        String template = templateEngine.process(templateName, context);

        helper.setFrom(ADMIN_MAIL);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(template);

        mailSender.send(mimeMessage);
    }
}
