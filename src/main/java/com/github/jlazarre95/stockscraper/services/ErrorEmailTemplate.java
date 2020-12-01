package com.github.jlazarre95.stockscraper.services;

import com.github.jlazarre95.stockscraper.models.Item;
import com.github.jlazarre95.stockscraper.utils.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Service
public class ErrorEmailTemplate {

    private static String emailTemplate;

    @PostConstruct
    public void init() {
        InputStream is = this.getClass().getResourceAsStream("/error-email-template.html");
        try {
            emailTemplate = StreamUtils.copyToString(is, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String resolve(Item item, String error, int backoff) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("item.name", item.getName());
        parameters.put("item.url", item.getUrl());
        parameters.put("item.regex", item.getRegex());
        parameters.put("item.cssSelector", item.getCssSelector());
        parameters.put("item.xpath", item.getXpath());
        parameters.put("item.refreshInterval", item.getRefreshInterval());
        parameters.put("error", error);
        parameters.put("backoff", backoff);
        String email = emailTemplate;
        for(Map.Entry<String, Object> param : parameters.entrySet()) {
            email = email.replace("{{" + param.getKey() + "}}", param.getValue() != null ? param.getValue().toString() : "None");
        }
        return email;
    }

}
