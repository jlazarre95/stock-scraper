package com.github.jlazarre95.stockscraper.utils;

import java.util.List;

public class EmailUtils {

    public static String[] getRecipientList(List<String> list) {
        if(list == null || list.size() < 1) {
            return new String[0];
        }
        return list.toArray(new String[list.size()]);
    }

}
