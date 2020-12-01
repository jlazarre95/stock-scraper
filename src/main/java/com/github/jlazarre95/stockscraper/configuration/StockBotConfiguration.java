package com.github.jlazarre95.stockscraper.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class StockBotConfiguration {

    @Bean(name = "stockBotExecutor")
    public Executor stockBotExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(12);
        executor.setMaxPoolSize(12);
        executor.setThreadNamePrefix("StockBot-");
        executor.initialize();
        return executor;
    }

}
