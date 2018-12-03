package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.data.RequestParams;
import com.study.movieland.entity.Currency;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.*;
import com.study.movieland.service.validator.MovieRequestParamsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DefaultMovieService implements MovieService {

    private MovieDao movieDao;
    private GenreService genreService;
    private CountryService countryService;
    private ReviewService reviewService;
    private CurrencyService currencyService;
    private MovieRequestParamsValidator requestParamValidator;

    @Override
    public Movie get(int id) {
        Movie movie = movieDao.get(id);
        return new Movie.Builder(movie)
                .genres(genreService.enrich(movie.getGenres()))
                .countries(countryService.enrich(movie.getCountries()))
                .reviews(reviewService.getByMovie(id))
                .price(currencyRate == 0 ? movie.getPrice() : movie.getPrice() / currencyRate)
                .build();
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
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @Autowired
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Autowired
    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Autowired
    public void setRequestParamValidator(MovieRequestParamsValidator requestParamValidator) {
        this.requestParamValidator = requestParamValidator;
    }
}
