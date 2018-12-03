package com.study.movieland.service.impl;

import com.study.movieland.dao.CountryDao;
import com.study.movieland.entity.Country;
import com.study.movieland.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DefaultCountryService implements CountryService {

    private CountryDao countryDao;

    @Override
    public Country get(int id) {
        return countryDao.get(id);
    }

    @Override
    public Collection<Country> getAll() {
        return countryDao.getAll();
    }

    @Autowired
    public void setCountryDao(CountryDao countryDao) {
        this.countryDao = countryDao;
    }
}
