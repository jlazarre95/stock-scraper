package com.github.jlazarre95.stockscraper.services;

import com.gargoylesoftware.htmlunit.StringWebResponse;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.parser.HTMLParser;
import com.github.jlazarre95.stockscraper.models.ScrapeOptions;
import com.github.jlazarre95.stockscraper.utils.UrlUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HtmlScraper {

    public boolean scrape(String htmlText, ScrapeOptions options) {
        if(options.getRegex() != null) {
            Pattern pattern = Pattern.compile(options.getRegex());
            Matcher matcher = pattern.matcher(htmlText);
            return matcher.find();
        } else if(options.getCssSelector() != null) {
            HtmlPage htmlPage = this.htmlTextToPage(htmlText, options);
            DomNode node = htmlPage.querySelector(options.getCssSelector());
            return node != null;
        } else if(options.getXpath() != null) {
            HtmlPage htmlPage = this.htmlTextToPage(htmlText, options);
            Object obj = htmlPage.getFirstByXPath(options.getXpath());
            return obj != null;
        } else {
            return false;
        }
    }

    private HtmlPage htmlTextToPage(String html, ScrapeOptions options) {
        try (WebClient client = new WebClient()) {
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);

            HTMLParser htmlParser = client.getPageCreator().getHtmlParser();
            WebWindow webWindow = client.getCurrentWindow();

            StringWebResponse webResponse = new StringWebResponse(html, UrlUtils.create(options.getOriginatingUrl()));
            HtmlPage page = new HtmlPage(webResponse, webWindow);
            webWindow.setEnclosedPage(page);

            htmlParser.parse(webResponse, page, true);
            return page;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
