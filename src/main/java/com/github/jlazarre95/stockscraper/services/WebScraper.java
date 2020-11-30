package com.github.jlazarre95.stockscraper.services;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WebScraper {

    public String scrape(String url) {
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        HtmlPage page;
        try {
            page = client.getPage(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return page.asText();
    }

}
