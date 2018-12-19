package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.data.MovieEnricherType;
import com.study.movieland.data.RequestParams;
import com.study.movieland.entity.Country;
import com.study.movieland.entity.Genre;
import com.study.movieland.entity.Movie;
import com.study.movieland.entity.Review;
import com.study.movieland.service.*;
import com.study.movieland.service.validator.MovieRequestParamsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class DefaultMovieService implements MovieService {

    private MovieDao movieDao;
    private EnrichmentService enrichmentService;
    private CurrencyService currencyService;

    private MovieRequestParamsValidator requestParamValidator;

    @Override
    public Movie get(int id) {
        return enrichmentService.enrichMovie(movieDao.get(id));
    }

    @Override
    public Movie get(int id, String currencyCode) {
        Movie movie = get(id);
        return new Movie.Builder()
                .id(movie.getId())
                .nameRussian(movie.getNameRussian())
                .nameNative(movie.getNameNative())
                .yearOfRelease(movie.getYearOfRelease())
                .description(movie.getDescription())
                .rating(movie.getRating())
                .price(currencyService.exchange(movie.getPrice(), currencyCode))
                .picturePath(movie.getPicturePath())
                .countries(new ArrayList<>(movie.getCountries()))
                .genres(new ArrayList<>(movie.getGenres()))
                .reviews(new ArrayList<>(movie.getReviews()))
                .build();
    }

    @Override
    public Movie create(Movie movie) {
        return movieDao.create(movie);
    }

    @Override
    public Movie update(Movie movie) {
        return movieDao.update(movie);
    }

    @Override
    public Collection<Movie> getAll(RequestParams requestParams) {
        if (requestParamValidator != null) {
            requestParamValidator.validate(requestParams);
        }
        return movieDao.getAll(requestParams);
    }

    @Override
    public Collection<Movie> getRandom(int count) {
        return movieDao.getRandom(count);
    }

    @Override
    public Collection<Movie> getByGenre(int genreId, RequestParams requestParams) {
        if (requestParamValidator != null) {
            requestParamValidator.validate(requestParams);
        }
        return movieDao.getByGenre(genreId, requestParams);
    }

    @Autowired
    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Autowired
    public void setRequestParamValidator(MovieRequestParamsValidator requestParamValidator) {
        this.requestParamValidator = requestParamValidator;
    }

    @Autowired
    public void setEnrichmentService(EnrichmentService enrichmentService) {
        this.enrichmentService = enrichmentService;
    }

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }
}
