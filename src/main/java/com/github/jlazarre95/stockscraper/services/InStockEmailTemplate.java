package com.github.jlazarre95.stockscraper.services;

import com.github.jlazarre95.stockscraper.models.Item;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Service
public class InStockEmailTemplate {

    private static String emailTemplate;

    @PostConstruct
    public void init() {
        InputStream is = this.getClass().getResourceAsStream("/in-stock-email-template.html");
        try {
            emailTemplate = StreamUtils.copyToString(is, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String resolve(Item item) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("item.name", item.getName());
        parameters.put("item.url", item.getUrl());
        parameters.put("item.regex", item.getRegex());
        parameters.put("item.refreshInterval", item.getRefreshInterval());
        parameters.put("item.checkoutUrl", item.getCheckoutUrl() != null ? item.getCheckoutUrl() : item.getUrl());
        String email = emailTemplate;
        for(Map.Entry<String, Object> param : parameters.entrySet()) {
            email = email.replace("{{" + param.getKey() + "}}", param.getValue() != null ? param.getValue().toString() : "None");
        }
        return email;
    }

}
