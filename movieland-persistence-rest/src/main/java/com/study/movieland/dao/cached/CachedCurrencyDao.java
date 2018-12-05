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
    private volatile Map<String, Currency> currencies;

    @PostConstruct
    @Scheduled(cron = "${scheduled.currencyCron:0 0 0 * * MON-FRI}")
    public void refresh() {
        Map<String, Currency> currencies = new HashMap<>();
        for (Currency currency : currencyDao.getAll()) {
            currencies.put(currency.getCode(), currency);
        }
        this.currencies = currencies;
    }

    @Override
    public Currency get(String code) {
        return currencies.get(code);
    }

    @Override
    public Collection<Currency> getAll() {
        return new ArrayList<>(this.currencies.values());
    }

    @Autowired
    public void setCurrencyDao(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }
}