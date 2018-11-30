package com.study.movieland.service;

import com.study.movieland.entity.Country;

import java.util.Collection;

public interface CountryService {

    int[] getIds(Collection<Country> countries);

    Country get(int id);

    Collection<Country> getSome(int[] ids);

    Collection<Country> getAll();
}
