package com.study.movieland.service;

import com.study.movieland.entity.Currency;

import java.util.Collection;

public interface CurrencyService {

    Currency get(String code);

    Collection<Currency> getAll();

    default double exchange(double value, String currencyCode) {
        Currency currency = get(currencyCode);
        double rate = currency == null ? 0 : currency.getRate();
        return rate == 0 ? value : value / rate;
    }
}