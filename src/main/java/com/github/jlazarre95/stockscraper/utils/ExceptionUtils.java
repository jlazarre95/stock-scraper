package com.github.jlazarre95.stockscraper.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class ExceptionUtils {

    public static String toString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String stackTrace = throwable.getMessage() + "\n" + sw.toString();
        return stackTrace;
    }

}
