package com.github.jlazarre95.stockscraper;

import com.github.jlazarre95.stockscraper.services.StockBotDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class StockScraperApplication implements CommandLineRunner {

    @Autowired
    private StockBotDispatcher dispatcher;

    public static void main(String[] args) {
        SpringApplication.run(StockScraperApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.dispatcher.dispatchBot();
    }

}
