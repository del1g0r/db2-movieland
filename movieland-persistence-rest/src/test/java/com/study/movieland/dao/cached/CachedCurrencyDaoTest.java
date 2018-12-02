package com.study.movieland.dao.cached;

import com.study.movieland.dao.CurrencyDao;
import com.study.movieland.entity.Currency;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertEquals;

public class CachedCurrencyDaoTest {

    @Test
    public void testGet() {
        CachedCurrencyDao cachedCurrencyDao = new CachedCurrencyDao();
        cachedCurrencyDao.setCurrencyDao(new StubCurrencyDao());
        cachedCurrencyDao.refresh();

        Currency expectedCurrency = new Currency.Builder().cc("USD").txt("Долар США").rate(28.2).build();
        Currency actualCurrency = cachedCurrencyDao.get("USD");

        assertEquals(expectedCurrency.getCc(), actualCurrency.getCc());
        assertEquals(expectedCurrency.getTxt(), actualCurrency.getTxt());
    }

    @Test
    public void testGetAll() {
        CachedCurrencyDao cachedCurrencyDao = new CachedCurrencyDao();
        cachedCurrencyDao.setCurrencyDao(new StubCurrencyDao());
        cachedCurrencyDao.refresh();

        Collection<Currency> actualCountries = cachedCurrencyDao.getAll();

        //Assert.assertThat(actualCountries, containsInAnyOrder(expectedCountries));
        Assert.assertThat(actualCountries, hasItem(
                new Currency.Builder()
                        .cc("USD")
                        .txt("Долар США")
                        .rate(28.2)
                        .build()));
        Assert.assertThat(actualCountries, hasItem(
                new Currency.Builder()
                        .cc("EUR")
                        .txt("Євро")
                        .rate(30)
                        .build()));
    }

    class StubCurrencyDao implements CurrencyDao {
        @Override
        public Currency get(String code) {
            return new Currency.Builder()
                    .cc("USD")
                    .txt("Долар США")
                    .rate(28.2)
                    .build();
        }

        @Override
        public Collection<Currency> getAll() {
            return Arrays.asList(
                    new Currency.Builder()
                            .cc("USD")
                            .txt("Долар США")
                            .rate(28.2)
                            .build(),
                    new Currency.Builder()
                            .cc("EUR")
                            .txt("Євро")
                            .rate(30)
                            .build()
            );
        }
    }
}