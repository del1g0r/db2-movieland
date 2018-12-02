package com.study.movieland.dao.cached;

import com.study.movieland.dao.CurrencyDao;
import com.study.movieland.entity.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
@Primary
public class CachedCurrencyDao implements CurrencyDao {

    private CurrencyDao currencyDao;
    private volatile Map<String, Currency> countries;

    @PostConstruct
    @Scheduled(fixedDelayString = "${scheduled.currencyFixedDelay:14400000}", initialDelayString = "${scheduled.currencyFixedDelay:14400000}")
    public void refresh() {
        HashMap<String, Currency> countries = new HashMap<>();
        for (Currency currency : currencyDao.getAll()) {
            countries.put(currency.getCc(), currency);
        }
        this.countries = countries;
    }

    @Override
    public Currency get(String code) {
        return countries.get(code);
    }

    @Override
    public Collection<Currency> getAll() {
        return new ArrayList<>(this.countries.values());
    }

    @Autowired
    public void setCurrencyDao(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }
}