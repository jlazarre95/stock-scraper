package com.github.jlazarre95.stockscraper;

import com.github.jlazarre95.stockscraper.models.Item;
import com.github.jlazarre95.stockscraper.services.StockBotDispatcher;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StockScraperApplication.class)
public class FalseInStockAlertTests {

    @Autowired
    private StockBotDispatcher stockBotDispatcher;

    @Test
    public void testFalseInStockAlerts() throws InterruptedException, ExecutionException, TimeoutException {
        Map<Item, CompletableFuture<Integer>> futures = this.stockBotDispatcher.dispatchBot();
        SoftAssertions softly = new SoftAssertions();
        for(Map.Entry<Item, CompletableFuture<Integer>> entry : futures.entrySet()) {
            softly.assertThat(entry.getValue().get(60, TimeUnit.SECONDS)).isEqualTo(0);
        }
    }

}
