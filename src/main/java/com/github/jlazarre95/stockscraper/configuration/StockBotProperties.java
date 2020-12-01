package com.github.jlazarre95.stockscraper.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "stockbot")
public class StockBotProperties {

    private List<String> defaultEmailRecipients = new ArrayList<>();
    private boolean enableInStockAlerts = true;
    private boolean enableErrorAlerts = true;
    private int inStockCooldown = 60_000 * 2;
    private String itemRepository = "items.yml";

    public List<String> getDefaultEmailRecipients() {
        return defaultEmailRecipients;
    }

    public StockBotProperties setDefaultEmailRecipients(List<String> defaultEmailRecipients) {
        this.defaultEmailRecipients = defaultEmailRecipients;
        return this;
    }

    public boolean isEnableInStockAlerts() {
        return enableInStockAlerts;
    }

    public StockBotProperties setEnableInStockAlerts(boolean enableInStockAlerts) {
        this.enableInStockAlerts = enableInStockAlerts;
        return this;
    }

    public boolean isEnableErrorAlerts() {
        return enableErrorAlerts;
    }

    public StockBotProperties setEnableErrorAlerts(boolean enableErrorAlerts) {
        this.enableErrorAlerts = enableErrorAlerts;
        return this;
    }

    public int getInStockCooldown() {
        return inStockCooldown;
    }

    public StockBotProperties setInStockCooldown(int inStockCooldown) {
        this.inStockCooldown = inStockCooldown;
        return this;
    }

    public String getItemRepository() {
        return itemRepository;
    }

    public StockBotProperties setItemRepository(String itemRepository) {
        this.itemRepository = itemRepository;
        return this;
    }

    @Override
    public String toString() {
        return "(" +
                "defaultEmailRecipients=" + defaultEmailRecipients +
                ", enableInStockAlerts=" + enableInStockAlerts +
                ", enableErrorAlerts=" + enableErrorAlerts +
                ", inStockCooldown=" + inStockCooldown +
                ", itemRepository=" + itemRepository +
                ')';
    }
}
