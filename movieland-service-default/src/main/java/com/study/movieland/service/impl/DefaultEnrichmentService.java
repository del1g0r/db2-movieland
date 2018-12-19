package com.study.movieland.service.impl;

import com.study.movieland.data.MovieEnricherType;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

@Service
public class DefaultEnrichmentService implements EnrichmentService {

    private GenreService genreService;
    private CountryService countryService;
    private ReviewService reviewService;

    @Override
    public Movie enrichMovie(Movie movie, Set<MovieEnricherType> enricherTypes) {
        return new Movie.Builder()
                .id(movie.getId())
                .nameRussian(movie.getNameRussian())
                .nameNative(movie.getNameNative())
                .yearOfRelease(movie.getYearOfRelease())
                .description(movie.getDescription())
                .rating(movie.getRating())
                .price(movie.getPrice())
                .picturePath(movie.getPicturePath())
                .countries(
                        enricherTypes.contains(MovieEnricherType.COUNTRY) ?
                                countryService.enrich(movie.getCountries()) :
                                new ArrayList<>(movie.getCountries()))
                .genres(
                        enricherTypes.contains(MovieEnricherType.GENRES) ?
                                genreService.enrich(movie.getGenres()) :
                                new ArrayList<>(movie.getGenres()))
                .reviews(
                        enricherTypes.contains(MovieEnricherType.REVIEWS) ?
                                reviewService.getByMovie(movie.getId()) :
                                new ArrayList<>(movie.getReviews())
                )
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
}
