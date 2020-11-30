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
public class InStockAlerter {

    private Logger logger = LoggerFactory.getLogger(InStockAlerter.class);

    private EmailService emailService;
    private InStockEmailTemplate inStockEmailTemplate;

    @Autowired
    public InStockAlerter(EmailService emailService, InStockEmailTemplate inStockEmailTemplate) {
        this.emailService = emailService;
        this.inStockEmailTemplate = inStockEmailTemplate;
    }

    public void send(Item item, List<String> defaultRecipients) {
        if((item.getEmailRecipients() != null && item.getEmailRecipients().size() >= 1)
                || (defaultRecipients != null && defaultRecipients.size() >= 1)) {
            String from = "mystockscraper@gmail.com";
            String[] to = EmailUtils.getRecipientList(defaultRecipients);
            String[] bcc = EmailUtils.getRecipientList(item.getEmailRecipients());
            String subject = "IN-STOCK ALERT: " + item.getName() + " is in stock!";
            String text = this.inStockEmailTemplate.resolve(item);
            try {
                this.emailService.sendEmail(from, to, bcc, subject, text, true, new HashMap<>());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            logger.info("Sent IN-STOCK alerts for item {}", item);
        }

    }

}
