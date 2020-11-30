package com.github.jlazarre95.stockscraper.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;

@Service
public class EmailService {

    private JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(SimpleMailMessage email) {
        mailSender.send(email);
    }

    public void sendEmail(String from, String[] to, String bcc[], String subject, String text, boolean isTextHtml,
                          Map<String, DataSource> dataSources) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setBcc(bcc);
        helper.setSubject(subject);
        helper.setText(text, isTextHtml);
        for(String dataSourceName : dataSources.keySet()) {
            helper.addAttachment(dataSourceName, dataSources.get(dataSourceName));
        }
        mailSender.send(message);
    }

}
