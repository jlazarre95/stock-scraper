package com.github.jlazarre95.stockscrapertesting;

import com.github.jlazarre95.stockscraper.services.HtmlFetcher;
import com.github.jlazarre95.stockscrapertesting.categories.RegenerateTestFixtures;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.FileWriter;
import java.io.IOException;

@Category(RegenerateTestFixtures.class)
public class RegenerateTestItemFixtures {

    private HtmlFetcher htmlFetcher;

    @Before
    public void setUp() {
        this.htmlFetcher = new HtmlFetcher();
    }

    @Test
    public void scrapeInStockWalmartItem() throws IOException {
        String text = this.htmlFetcher.fetch("https://www.walmart.com/ip/Marvel-s-Spider-Man-Miles-Morales-Ultimate-Launch-Edition-Sony-PlayStation-5/795159051");
        FileWriter fw = new FileWriter("src/testFalseInStockAlerts/resources/walmart-in-stock-item.txt");
        fw.write(text);
        fw.flush();
    }

    @Test
    public void scrapeOutOfStockWalmartItem() throws IOException {
        String text = this.htmlFetcher.fetch("https://www.walmart.com/ip/PlayStation-5-Console/363472942");
        FileWriter fw = new FileWriter("src/testFalseInStockAlerts/resources/walmart-out-of-stock-item.txt");
        fw.write(text);
        fw.flush();
    }

    @Test
    public void scrapeInStockBestBuyItem() throws IOException {
        String text = this.htmlFetcher.fetch("https://www.bestbuy.com/site/searchpage.jsp?st=monitor&_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=&sp=&qp=&list=n&af=true&iht=y&usc=All+Categories&ks=960&keys=keys");
        FileWriter fw = new FileWriter("src/testFalseInStockAlerts/resources/best-buy-in-stock-item.txt");
        fw.write(text);
        fw.flush();
    }

    @Test
    public void scrapeOutOfStockBestBuyItem() throws IOException {
        String text = this.htmlFetcher.fetch("https://www.bestbuy.com/site/searchpage.jsp?st=rtx+3080&_dyncharset=UTF-8&_dynSessConf=&id=pcat17071&type=page&sc=Global&cp=1&nrp=&sp=&qp=&list=n&af=true&iht=y&usc=All+Categories&ks=960&keys=keys");
        FileWriter fw = new FileWriter("src/testFalseInStockAlerts/resources/best-buy-out-of-stock-item.txt");
        fw.write(text);
        fw.flush();
    }

    @Test
    public void scrapeInStockNeweggItem() throws IOException {
        String text = this.htmlFetcher.fetch("https://www.newegg.com/p/pl?d=monitor");
        FileWriter fw = new FileWriter("src/testFalseInStockAlerts/resources/newegg-in-stock-item.txt");
        fw.write(text);
        fw.flush();
    }

    @Test
    public void scrapeOutOfStockNeweggItem() throws IOException {
        String text = this.htmlFetcher.fetch("https://www.newegg.com/p/pl?d=rtx+3080+combo");
        FileWriter fw = new FileWriter("src/testFalseInStockAlerts/resources/newegg-out-of-stock-item.txt");
        fw.write(text);
        fw.flush();
    }

}