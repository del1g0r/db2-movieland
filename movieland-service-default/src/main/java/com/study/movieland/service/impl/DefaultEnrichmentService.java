package com.study.movieland.service.impl;

import com.study.movieland.entity.Movie;
import com.study.movieland.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultEnrichmentService implements EnrichmentService {

    private GenreService genreService;
    private CountryService countryService;
    private ReviewService reviewService;
    private CurrencyService currencyService;

    @Override
    public Movie enrichMovie(Movie movie, String currencyCode) {
        return new Movie.Builder(movie)
                .genres(genreService.enrich(movie.getGenres()))
                .countries(countryService.enrich(movie.getCountries()))
                .reviews(reviewService.getByMovie(movie.getId()))
                .price(currencyService.exchange(movie.getPrice(), currencyCode))
                .build();
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
}
