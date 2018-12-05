package com.study.movieland.service.impl;

import com.study.movieland.dao.CurrencyDao;
import com.study.movieland.entity.Currency;
import com.study.movieland.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DefaultCurrencyService implements CurrencyService {

    private CurrencyDao currencyDao;

    @Override
    public Currency get(String code) {
        return currencyDao.get(code);
    }

    @Override
    public Collection<Currency> getAll() {
        return currencyDao.getAll();
    }

    @Autowired
    public void setCurrencyDao(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }
}
