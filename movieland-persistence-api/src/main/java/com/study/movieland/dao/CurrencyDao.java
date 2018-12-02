package com.study.movieland.dao;

import com.study.movieland.entity.Currency;

import java.util.Collection;

public interface CurrencyDao {

    Currency get(String code);

    Collection<Currency> getAll();
}
