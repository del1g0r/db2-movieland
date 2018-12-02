package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.data.RequestParams;
import com.study.movieland.dto.MovieDto;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.CountryService;
import com.study.movieland.service.GenreService;
import com.study.movieland.service.MovieService;
import com.study.movieland.service.ReviewService;
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
    private MovieRequestParamsValidator requestParamValidator;

    @Override
    public MovieDto get(int id) {
        MovieDto movie = movieDao.get(id);
        return new MovieDto.Builder(movie)
                .genres(genreService.getSome(genreService.getIds(movie.getGenres())))
                .countries(countryService.getSome(countryService.getIds(movie.getCountries())))
                .reviews(reviewService.getByMovie(id))
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
    public void setRequestParamValidator(MovieRequestParamsValidator requestParamValidator) {
        this.requestParamValidator = requestParamValidator;
    }
}
