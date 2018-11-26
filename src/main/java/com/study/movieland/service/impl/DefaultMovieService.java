package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {

    private MovieDao movieDao;

    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    @Override
    public List<Movie> getSortedAll(Sort sort) {
        return movieDao.getSortedAll(sort);
    }

    @Override
    public List<Movie> getRandom(int count) {
        return movieDao.getRandom(count);
    }

    @Override
    public List<Movie> getByGenre(int genreId) {
        return movieDao.getByGenre(genreId);
    }

    @Autowired
    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }
}
