package com.study.movieland.dao;

import com.study.movieland.entity.Country;
import com.study.movieland.entity.Genre;

import java.util.ArrayList;
import java.util.Collection;

public interface CountryDao {

    Country get(int id);

    Collection<Country> getAll();

    default Country enrich(Country country) {
        return get(country.getId());
    }

    default Collection<Country> enrich(Collection<Country> countries) {
        Collection<Country> enricheCountries = new ArrayList<>();
        for (Country country : countries) {
            enricheCountries.add(get(country.getId()));
        }
        return enricheCountries;
    }
}