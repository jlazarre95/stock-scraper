package com.github.jlazarre95.stockscraper.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtils {

    public static URL create(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
