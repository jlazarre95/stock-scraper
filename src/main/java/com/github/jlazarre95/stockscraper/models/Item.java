package com.github.jlazarre95.stockscraper.models;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private String name;
    private String url;
    private String regex;
    private int refreshInterval;
    private String checkoutUrl;
    private List<String> emailRecipients = new ArrayList<>();
    private int maxChecks;

    public String getName() {
        return name;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Item setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getRegex() {
        return regex;
    }

    public Item setRegex(String regex) {
        this.regex = regex;
        return this;
    }

    public int getRefreshInterval() {
        return refreshInterval;
    }

    public Item setRefreshInterval(int refreshInterval) {
        this.refreshInterval = refreshInterval;
        return this;
    }

    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public Item setCheckoutUrl(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
        return this;
    }

    public List<String> getEmailRecipients() {
        return emailRecipients;
    }

    public Item setEmailRecipients(List<String> emailRecipients) {
        this.emailRecipients = emailRecipients;
        return this;
    }

    public int getMaxChecks() {
        return maxChecks;
    }

    public Item setMaxChecks(int maxChecks) {
        this.maxChecks = maxChecks;
        return this;
    }

    @Override
    public String toString() {
        return "(" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", regex='" + regex + '\'' +
                ", refreshInterval=" + refreshInterval +
                ", checkoutUrl=" + checkoutUrl +
                ", emailRecipients=" + emailRecipients +
                ", maxChecks=" + maxChecks +
                ')';
    }

}
