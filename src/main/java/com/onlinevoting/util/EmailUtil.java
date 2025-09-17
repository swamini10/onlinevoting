package com.onlinevoting.util;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.io.IOException;
import java.util.Map;

@Component
public class EmailUtil {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freemarkerConfig;

    public void sendEmailWithTemplate(String to, String subject, String templateName, Map<String, Object> model)
            throws MessagingException, IOException, TemplateException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Template template = freemarkerConfig.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        template.process(model, writer);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(writer.toString(), true);

        mailSender.send(message);
    }
}