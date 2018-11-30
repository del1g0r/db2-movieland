package com.study.movieland.service.impl;

import com.study.movieland.dao.CountryDao;
import com.study.movieland.dao.GenreDao;
import com.study.movieland.entity.Country;
import com.study.movieland.entity.Genre;
import com.study.movieland.service.CountryService;
import com.study.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class DefaultCountryService implements CountryService {

    private CountryDao countryDao;

    @Override
    public int[] getIds(Collection<Country> countries) {
        int[] ids = new int[countries.size()];
        int id = 0;
        for (Country country : countries) {
            ids[id++] = country.getId();
        }
        return ids;
    }

    @Override
    public Country get(int id) {
        return countryDao.get(id);
    }

    @Override
    public Collection<Country> getSome(int[] ids) {
        return countryDao.getSome(ids);
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
