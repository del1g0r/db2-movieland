package com.study.movieland.dao.cached;

import com.study.movieland.dao.CountryDao;
import com.study.movieland.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Repository
@Primary
public class CachedCountryDao implements CountryDao {

    private CountryDao countryDao;
    private volatile HashMap<Integer, Country> countries;

    @PostConstruct
    @Scheduled(fixedDelayString = "${scheduled.countryFixedDelay:14400000}", initialDelayString = "${scheduled.countryFixedDelay:14400000}")
    public void refresh() {
        HashMap<Integer, Country> countries = new HashMap<>();
        for (Country country : countryDao.getAll()) {
            countries.put(country.getId(), country);
        }
        this.countries = countries;
    }

    @Override
    public Country get(int id) {
        return countries.get(id);
    }

    @Override
    public Collection<Country> getAll() {
        return new ArrayList<>(this.countries.values());
    }

    @Override
    public Collection<Country> getSome(int[] ids) {
        List<Country> someCountries = new ArrayList<>();
        for (int id : ids) {
            someCountries.add(countries.get(id));
        }
        return someCountries;
    }

    @Autowired
    public void setCountryDao(CountryDao countryDao) {
        this.countryDao = countryDao;
    }
}