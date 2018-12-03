package com.study.movieland.dao;

import com.study.movieland.entity.Country;

import java.util.Collection;

public interface CountryDao {

    Country get(int id);

    Collection<Country> getAll();
}