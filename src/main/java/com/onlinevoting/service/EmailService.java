
package com.onlinevoting.service;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

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

	  public void sendEmailWithTemplate(List<String> toEmailList, String subject, String templateName, Map<String, Object> model)
            throws MessagingException, IOException, TemplateException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Template template = freemarkerConfig.getTemplate(templateName);
        StringWriter writer = new StringWriter();
        template.process(model, writer);
        String toEmails = String.join(",", toEmailList);
        helper.setTo(toEmails);
        helper.setSubject(subject);
        helper.setText(writer.toString(), true);

        mailSender.send(message);
    }
    
}


