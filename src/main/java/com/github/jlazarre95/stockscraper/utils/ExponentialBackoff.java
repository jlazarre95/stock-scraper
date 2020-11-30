package com.github.jlazarre95.stockscraper.utils;

public class ExponentialBackoff {

    private double initialValue;
    private double value;

    public ExponentialBackoff(double value) {
        if(value < 0) {
            throw new IllegalArgumentException("value must be nonnegative");
        }
        this.initialValue = this.value = value / 2.0;
    }

    public double getValue() {
        return this.value = this.value * 2.0;
    }

    public ExponentialBackoff resetValue() {
        this.value = this.initialValue;
        return this;
    }

}
