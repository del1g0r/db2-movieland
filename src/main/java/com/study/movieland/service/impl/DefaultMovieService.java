package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.data.RequestParams;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.MovieService;
import com.study.movieland.service.validator.MovieRequestParamsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultMovieService implements MovieService {

    private MovieDao movieDao;
    private MovieRequestParamsValidator requestParamValidator;

    @Override
    public List<Movie> getAll(RequestParams requestParams) {
        if (requestParamValidator != null) {
            requestParamValidator.validate(requestParams);
        }
        return movieDao.getAll(requestParams);
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

    @Autowired
    public void setRequestParamValidator(MovieRequestParamsValidator requestParamValidator) {
        this.requestParamValidator = requestParamValidator;
    }
}
