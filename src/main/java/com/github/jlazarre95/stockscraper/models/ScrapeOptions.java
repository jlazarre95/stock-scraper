package com.github.jlazarre95.stockscraper.models;

public class ScrapeOptions {
    private String originatingUrl;
    private String cssSelector;
    private String regex;
    private String xpath;

    public String getOriginatingUrl() {
        return originatingUrl;
    }

    public ScrapeOptions setOriginatingUrl(String originatingUrl) {
        this.originatingUrl = originatingUrl;
        return this;
    }

    public String getCssSelector() {
        return cssSelector;
    }

    public ScrapeOptions setCssSelector(String cssSelector) {
        this.cssSelector = cssSelector;
        return this;
    }

    public String getRegex() {
        return regex;
    }

    public ScrapeOptions setRegex(String regex) {
        this.regex = regex;
        return this;
    }

    public String getXpath() {
        return xpath;
    }

    public ScrapeOptions setXpath(String xpath) {
        this.xpath = xpath;
        return this;
    }
}
