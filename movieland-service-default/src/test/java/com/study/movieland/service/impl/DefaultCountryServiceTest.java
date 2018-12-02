package com.study.movieland.service.impl;

import com.study.movieland.dao.CountryDao;
import com.study.movieland.entity.Country;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

public class DefaultCountryServiceTest {

    @Test
    public void testGetIds() {
        DefaultCountryService CountryService = new DefaultCountryService();
        CountryService.setCountryDao(new StubCountryDao());

        int[] expectedIds = new int[]{1, 2};
        int[] actualIds = CountryService.getIds(Arrays.asList(
                new Country.Builder()
                        .id(1)
                        .name("Country 1")
                        .build(),
                new Country.Builder()
                        .id(2)
                        .name("Country 2")
                        .build()
        ));

        Assert.assertThat(actualIds, is(expectedIds));
    }

    @Test
    public void testGet() {
        DefaultCountryService CountryService = new DefaultCountryService();
        CountryService.setCountryDao(new StubCountryDao());

        Country expectedCountry = new Country.Builder().id(1).name("Country").build();
        Country actualCountry = CountryService.get(1);

        assertEquals(expectedCountry.getId(), actualCountry.getId());
        assertEquals(expectedCountry.getName(), actualCountry.getName());
    }

    @Test
    public void testGetSome() {
        DefaultCountryService CountryService = new DefaultCountryService();
        CountryService.setCountryDao(new StubCountryDao());

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
        Collection<Country> actualCountries = CountryService.getSome(new int[]{2, 3});

        Assert.assertThat(actualCountries, is(expectedCountries));
    }

    @Test
    public void testGetAll() {
        DefaultCountryService CountryService = new DefaultCountryService();
        CountryService.setCountryDao(new StubCountryDao());

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
        Collection<Country> actualCountries = CountryService.getAll();

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
                            .build());
        }

        @Override
        public Collection<Country> getSome(int[] ids) {
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
                            .build());

        }
    }
}
