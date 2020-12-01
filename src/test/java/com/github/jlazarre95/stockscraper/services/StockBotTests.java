package com.github.jlazarre95.stockscraper.services;

import com.github.jlazarre95.stockscraper.configuration.StockBotProperties;
import com.github.jlazarre95.stockscraper.models.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StockBotTests {

    @InjectMocks
    private StockBot stockBot;

    @Mock
    private StockBotProperties stockBotProperties;

    @Mock
    private HtmlFetcher htmlFetcher;

    @Mock
    private InStockAlerter inStockAlerter;

    @Mock
    private ErrorAlerter errorAlerter;

    @Before
    public void setUp() {
        this.stockBotProperties.setInStockCooldown(1_000);
        this.stockBotProperties.setDefaultEmailRecipients(Arrays.asList("johndoe01@testFalseInStockAlerts.com"));
    }

    @Test
    public void testWatchBestBuyInStockItem() throws ExecutionException, InterruptedException {
        when(this.htmlFetcher.fetch("https://bestbuy.com/is")).thenReturn(loadItemFixture("best-buy-in-stock-item.txt"));

        Item item = new Item()
                .setUrl("https://bestbuy.com/is")
                .setRegex("Add to Cart")
                .setMaxChecks(1);

        CompletableFuture<Integer> timesInStock = this.stockBot.watch(item);

        assertThat(timesInStock.get()).isEqualTo(1);
        verify(this.inStockAlerter).send(item, this.stockBotProperties.getDefaultEmailRecipients());
        verifyZeroInteractions(this.errorAlerter);
    }

    @Test
    public void testWatchBestBuyOutOfStockItem() throws ExecutionException, InterruptedException {
        when(this.htmlFetcher.fetch("https://bestbuy.com/oos")).thenReturn(loadItemFixture("best-buy-out-of-stock-item.txt"));

        Item item = new Item()
                .setUrl("https://bestbuy.com/oos")
                .setRegex("Add to Cart")
                .setMaxChecks(1);

        CompletableFuture<Integer> timesInStock = this.stockBot.watch(item);

        assertThat(timesInStock.get()).isEqualTo(0);
        verifyZeroInteractions(this.inStockAlerter, this.errorAlerter);
    }

    @Test
    public void testWatchWalmartInStockItem() throws ExecutionException, InterruptedException {
        when(this.htmlFetcher.fetch("https://walmart.com/is")).thenReturn(loadItemFixture("walmart-in-stock-item.txt"));

        Item item = new Item()
                .setUrl("https://walmart.com/is")
                .setRegex("Add to cart")
                .setMaxChecks(1);

        CompletableFuture<Integer> timesInStock = this.stockBot.watch(item);

        assertThat(timesInStock.get()).isEqualTo(1);
        verify(this.inStockAlerter).send(item, this.stockBotProperties.getDefaultEmailRecipients());
        verifyZeroInteractions(this.errorAlerter);
    }

    @Test
    public void testWatchWalmartOutOfStockItem() throws ExecutionException, InterruptedException {
        when(this.htmlFetcher.fetch("https://walmart.com/oos")).thenReturn(loadItemFixture("walmart-out-of-stock-item.txt"));

        Item item = new Item()
                .setUrl("https://walmart.com/oos")
                .setRegex("Add to cart")
                .setMaxChecks(1);

        CompletableFuture<Integer> timesInStock = this.stockBot.watch(item);

        assertThat(timesInStock.get()).isEqualTo(0);
        verifyZeroInteractions(this.inStockAlerter, this.errorAlerter);
    }

    @Test
    public void testWatchNeweggInStockItem() throws ExecutionException, InterruptedException {
        when(this.htmlFetcher.fetch("https://newegg.com/is")).thenReturn(loadItemFixture("newegg-in-stock-item.txt"));

        Item item = new Item()
                .setUrl("https://newegg.com/is")
                .setRegex("Add to cart")
                .setMaxChecks(1);

        CompletableFuture<Integer> timesInStock = this.stockBot.watch(item);

        assertThat(timesInStock.get()).isEqualTo(1);
        verify(this.inStockAlerter).send(item, this.stockBotProperties.getDefaultEmailRecipients());
        verifyZeroInteractions(this.errorAlerter);
    }

    @Test
    public void testWatchNeweggOutOfStockItem() throws ExecutionException, InterruptedException {
        when(this.htmlFetcher.fetch("https://newegg.com/oos")).thenReturn(loadItemFixture("newegg-out-of-stock-item.txt"));

        Item item = new Item()
                .setUrl("https://newegg.com/oos")
                .setRegex("Add to cart")
                .setMaxChecks(1);

        CompletableFuture<Integer> timesInStock = this.stockBot.watch(item);

        assertThat(timesInStock.get()).isEqualTo(0);
        verifyZeroInteractions(this.inStockAlerter, this.errorAlerter);
    }

    private String loadItemFixture(String name) {
        InputStream is = this.getClass().getResourceAsStream("/" + name);
        try {
            return StreamUtils.copyToString(is, Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
