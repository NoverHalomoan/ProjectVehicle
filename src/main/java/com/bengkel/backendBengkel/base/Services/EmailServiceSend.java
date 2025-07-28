package com.bengkel.backendBengkel.base.Services;

import java.util.List;
import java.util.Map;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceSend {

    private final JavaMailSender mailSender;

    private TemplateEngine templateEngine;

    public EmailServiceSend(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    private Context createContentEmail(Map<String, String> contentMap) {
        Context context = new Context();
        for (String dataKey : contentMap.keySet()) {
            context.setVariable(dataKey, contentMap.get(dataKey));
        }
        return context;
    }

    @Async
    public void sendEmail(String toEmail, String subject, Map<String, String> MapContext, String pathEmail) throws MessagingException {
        Context context = createContentEmail(MapContext);

        String htmlContent = templateEngine.process(pathEmail, context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
        log.info("Send email successfully");
    }
}
