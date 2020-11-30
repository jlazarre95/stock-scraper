package com.github.jlazarre95.stockscraper.services;

import com.github.jlazarre95.stockscraper.models.Item;
import com.github.jlazarre95.stockscraper.utils.EmailUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;

@Service
public class ErrorAlerter {

    private Logger logger = LoggerFactory.getLogger(ErrorAlerter.class);

    private EmailService emailService;
    private ErrorEmailTemplate errorEmailTemplate;

    @Autowired
    public ErrorAlerter(EmailService emailService, ErrorEmailTemplate errorEmailTemplate) {
        this.emailService = emailService;
        this.errorEmailTemplate = errorEmailTemplate;
    }

    public void send(Item item, String error, int backoff, List<String> defaultRecipients)  {
        if(defaultRecipients != null && defaultRecipients.size() >= 1) {
            String from = "mystockscraper@gmail.com";
            String[] to = EmailUtils.getRecipientList(defaultRecipients);
            String subject = "ERROR ALERT: Bot failed while watching item " + item.getName();
            String text = this.errorEmailTemplate.resolve(item, error, backoff);
            try {
                this.emailService.sendEmail(from, to, new String[]{}, subject, text, true, new HashMap<>());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            logger.info("Sent ERROR alerts for item {}", item);
        }
    }

}
