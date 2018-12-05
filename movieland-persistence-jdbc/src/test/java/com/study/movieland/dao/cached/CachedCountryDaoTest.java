package com.study.movieland.dao.cached;

import com.study.movieland.dao.CountryDao;
import com.study.movieland.entity.Country;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

public class CachedCountryDaoTest {

    @Test
    public void testGet() {
        CachedCountryDao cachedCountryDao = new CachedCountryDao();
        cachedCountryDao.setCountryDao(new StubCountryDao());
        cachedCountryDao.refresh();

        Country expectedCountry = new Country.Builder().id(1).name("Country 1").build();
        Country actualCountry = cachedCountryDao.get(1);

        assertEquals(expectedCountry.getId(), actualCountry.getId());
        assertEquals(expectedCountry.getName(), actualCountry.getName());
    }

    @Test
    public void testGetAll() {
        CachedCountryDao cachedCountryDao = new CachedCountryDao();
        cachedCountryDao.setCountryDao(new StubCountryDao());
        cachedCountryDao.refresh();

        Collection<Country> expectedCountries = Arrays.asList(
                new Country.Builder()
                        .id(1)
                        .name("Country 1")
                        .build(),
                new Country.Builder()
                        .id(2)
                        .name("Country 2")
                        .build(),
                new Country.Builder()
                        .id(3)
                        .name("Country 3")
                        .build()
        );
        Collection<Country> actualCountries = cachedCountryDao.getAll();

        Assert.assertThat(actualCountries, is(expectedCountries));
    }

    class StubCountryDao implements CountryDao {
        @Override
        public Country get(int id) {
            return new Country.Builder()
                    .id(1)
                    .name("Country")
                    .build();
        }

        @Override
        public Collection<Country> getAll() {
            return Arrays.asList(
                    new Country.Builder()
                            .id(1)
                            .name("Country 1")
                            .build(),
                    new Country.Builder()
                            .id(2)
                            .name("Country 2")
                            .build(),
                    new Country.Builder()
                            .id(3)
                            .name("Country 3")
                            .build()
            );
        }
    }
}