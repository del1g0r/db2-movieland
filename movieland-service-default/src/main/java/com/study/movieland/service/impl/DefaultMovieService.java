package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.data.RequestParams;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.*;
import com.study.movieland.service.validator.MovieRequestParamsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class DefaultMovieService implements MovieService {

    private static final Logger log = LoggerFactory.getLogger(DefaultMovieService.class);

    private MovieDao movieDao;
    private GenreService genreService;
    private CountryService countryService;
    private ReviewService reviewService;
    private CurrencyService currencyService;
    private MovieRequestParamsValidator requestParamValidator;
    private int enrichTimeout;

    @Override
    public Movie get(int id, String currencyCode) {
        Movie movie = movieDao.get(id);

        try {
            Map<String, String> contextMap = MDC.getCopyOfContextMap();
            if (Executors
                    .newCachedThreadPool()
                    .invokeAll(Arrays.asList(
                            () -> {
                                MDC.setContextMap(contextMap);
                                movie.setGenres(genreService.enrich(movie.getGenres()));
                                MDC.clear();
                                return null;
                            },
                            () -> {
                                MDC.setContextMap(contextMap);
                                movie.setCountries(countryService.enrich(movie.getCountries()));
                                MDC.clear();
                                return null;
                            },
                            () -> {
                                MDC.setContextMap(contextMap);
                                movie.setReviews(reviewService.getByMovie(id));
                                MDC.clear();
                                return null;
                            }
                    ), enrichTimeout, TimeUnit.MILLISECONDS)
                    .stream()
                    .anyMatch(Future::isCancelled)) {
                log.warn("The movie {} has not been enriched fully because of timeout", id);
            }
            ;
        } catch (InterruptedException e) {
            log.error("Enrichment of movie {} was interrupted", e, id);
        }

        return new Movie.Builder(movie)
                .price(currencyService.exchange(movie.getPrice(), currencyCode))
                .build();
    }

    @Override
    public void create(Movie movie) {
        movieDao.create(movie);
    }

    @Override
    public void update(Movie movie) {
        movieDao.update(movie);
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

    @Value("${web.movie.enrichTimeoutMS:5000}")
    public void setEnrichTimeout(int enrichTimeout) {
        this.enrichTimeout = enrichTimeout;
    }
}
