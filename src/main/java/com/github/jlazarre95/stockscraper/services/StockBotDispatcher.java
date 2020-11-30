package com.github.jlazarre95.stockscraper.services;

import com.github.jlazarre95.stockscraper.models.Item;
import com.github.jlazarre95.stockscraper.repositories.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class StockBotDispatcher {

    private static Logger logger = LoggerFactory.getLogger(StockBotDispatcher.class);
    private StockBot stockBot;
    private ItemRepository itemRepository;

    @Autowired
    public StockBotDispatcher(StockBot stockBot, ItemRepository itemRepository) {
        this.stockBot = stockBot;
        this.itemRepository = itemRepository;
    }

    public Map<Item, CompletableFuture<Integer>> dispatchBot() {
        Map<Item, CompletableFuture<Integer>> futures = new HashMap<>();
        List<Item> items = this.itemRepository.getItems();
        for(int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            CompletableFuture<Integer> future = this.stockBot.watch(item);
            futures.put(item, future);
        }
        return futures;
    }

}
