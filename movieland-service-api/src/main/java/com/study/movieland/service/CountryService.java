package com.study.movieland.service;

import com.study.movieland.entity.Country;

import java.util.ArrayList;
import java.util.Collection;

public interface CountryService {

    Country get(int id);

    Collection<Country> getAll();

    default Collection<Country> enrich(Collection<Country> countries) {
        Collection<Country> enrichedCountries = new ArrayList<>();
        for (Country country : countries) {
            enrichedCountries.add(get(country.getId()));
        }
        return enrichedCountries;
    }
}
