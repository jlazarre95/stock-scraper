package com.github.jlazarre95.stockscraper.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.jlazarre95.stockscraper.configuration.StockBotProperties;
import com.github.jlazarre95.stockscraper.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemRepository {

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    private List<Item> items = new ArrayList<>();
    private StockBotProperties properties;

    @Autowired
    public ItemRepository(StockBotProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/" + properties.getItemRepository());
        if(is == null)
            throw new RuntimeException("Resource 'items.yml' not found");
        this.items = this.mapper.readValue(is, new TypeReference<List<Item>>(){});
    }

    public List<Item> getItems() {
        return items;
    }

}
