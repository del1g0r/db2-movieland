package com.study.movieland.service;

import com.study.movieland.entity.Country;

import java.util.ArrayList;
import java.util.Collection;

public interface CountryService {

    Country get(int id);

    Collection<Country> getAll();

    default Country enrich(Country country) {
        return get(country.getId());
    }

    default Collection<Country> enrich(Collection<Country> countries) {
        Collection<Country> enrichedCountries = new ArrayList<>();
        for (Country country : countries) {
            enrichedCountries.add(enrich(country));
        }
        return enrichedCountries;
    }
}
