package com.study.movieland.service;

import com.study.movieland.entity.Currency;

import java.util.Collection;

public interface CurrencyService {

    Currency get(String code);

    Collection<Currency> getAll();
}