package com.github.jlazarre95.stockscraper.services;

import com.github.jlazarre95.stockscraper.configuration.StockBotProperties;
import com.github.jlazarre95.stockscraper.models.Item;
import com.github.jlazarre95.stockscraper.models.ScrapeOptions;
import com.github.jlazarre95.stockscraper.utils.ExceptionUtils;
import com.github.jlazarre95.stockscraper.utils.ExponentialBackoff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StockBot {

    private static Logger logger = LoggerFactory.getLogger(StockBot.class);
    private StockBotProperties properties;
    private HtmlFetcher htmlFetcher;
    private HtmlScraper htmlScraper;
    private InStockAlerter inStockAlerter;
    private ErrorAlerter errorAlerter;

    @Autowired
    public StockBot(StockBotProperties properties, HtmlFetcher htmlFetcher, HtmlScraper htmlScraper,
                    InStockAlerter inStockAlerter, ErrorAlerter errorAlerter) {
        this.properties = properties;
        this.htmlFetcher = htmlFetcher;
        this.htmlScraper = htmlScraper;
        this.inStockAlerter = inStockAlerter;
        this.errorAlerter = errorAlerter;
    }

    @PostConstruct
    public void init() {
        logger.info("Stock bot properties: {}", properties);
    }

    @Async("stockBotExecutor")
    public CompletableFuture<Integer> watch(Item item) {
        logger.info("Watching item {}", item);
        CompletableFuture<Integer> future = new CompletableFuture<>();
        int timesInStock = 0;
        for(int i = 0; item.getMaxChecks() == 0 || i < item.getMaxChecks(); i++) {
            boolean foundItem = watchItem(item, i);
            if(foundItem) {
                timesInStock++;
            }
        }
        future.complete(timesInStock);
        return future;
    }

    public boolean watchItem(Item item, int numChecks) {
        boolean lastCheck = numChecks == item.getMaxChecks() - 1;
        ExponentialBackoff exponentialBackoff = new ExponentialBackoff(item.getRefreshInterval());
        boolean foundItem = false;
        try {
            foundItem = this.scrapeItemPage(item);
            exponentialBackoff.resetValue();
            if(foundItem) {
                logger.info("IN-STOCK ALERT: item {} is in stock!", item);
                if(this.properties.isEnableInStockAlerts()) {
                    this.inStockAlerter.send(item, this.properties.getDefaultEmailRecipients());
                }
                if(!lastCheck)
                    this.sleep(this.properties.getInStockCooldown());
            } else {
                if(!lastCheck)
                    this.sleep(item.getRefreshInterval());
            }
        } catch(Exception ex) {
            int backoff = (int)exponentialBackoff.getValue();
            logger.error("ERROR ALERT: Failed watching item {}. Will back off for {} seconds", item, backoff / 1000);
            if(this.properties.isEnableErrorAlerts()) {
                this.errorAlerter.send(item, ExceptionUtils.toString(ex), backoff, this.properties.getDefaultEmailRecipients());
            }
            if(!lastCheck)
                this.sleep(backoff);
        }
        return foundItem;
    }

    private boolean scrapeItemPage(Item item) {
        String htmlText = this.htmlFetcher.fetch(item.getUrl());
        return this.htmlScraper.scrape(htmlText, new ScrapeOptions()
                .setOriginatingUrl(item.getUrl())
                .setRegex(item.getRegex())
                .setCssSelector(item.getCssSelector()));
    }

    private void openBrowser(Item item) throws URISyntaxException, IOException {
        if(item.getCheckoutUrl() != null && Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            logger.info("Opening browser URL: {}", item.getCheckoutUrl());
            Desktop.getDesktop().browse(new URI(item.getCheckoutUrl()));
        }
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            logger.error("Sleep interrupted");
        }
    }

}
