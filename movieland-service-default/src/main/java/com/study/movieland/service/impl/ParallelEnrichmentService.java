package com.study.movieland.service.impl;

import com.study.movieland.entity.Country;
import com.study.movieland.entity.Genre;
import com.study.movieland.entity.Movie;
import com.study.movieland.entity.Review;
import com.study.movieland.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;

@Service
@Primary
public class ParallelEnrichmentService implements EnrichmentService {

    private static final Logger log = LoggerFactory.getLogger(ParallelEnrichmentService.class);
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    private GenreService genreService;
    private CountryService countryService;
    private ReviewService reviewService;
    private int enrichTimeout;

    @Override
    public Movie enrichMovie(Movie movie) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        Movie finalMovie = movie;
        try {
            List<Future<Function<Movie, Movie>>> futures = executorService.invokeAll(Arrays.asList(
                    () -> {
                        MDC.setContextMap(contextMap);
                        Collection<Genre> genres = genreService.enrich(finalMovie.getGenres());
                        MDC.clear();
                        return (r) -> {
                            r.setGenres(genres);
                            return r;
                        };
                    },
                    () -> {
                        MDC.setContextMap(contextMap);
                        Collection<Country> countries = countryService.enrich(finalMovie.getCountries());
                        MDC.clear();
                        return (r) -> {
                            r.setCountries(countries);
                            return r;
                        };
                    },
                    () -> {
                        MDC.setContextMap(contextMap);
                        Collection<Review> reviews = reviewService.getByMovie(finalMovie.getId());
                        MDC.clear();
                        return (r) -> {
                            r.setReviews(reviews);
                            return r;
                        };
                    }
                    ),
                    enrichTimeout,
                    TimeUnit.MILLISECONDS);

            for (Future<Function<Movie, Movie>> future : futures) {
                try {
                    if (future.isCancelled()) {
                        log.warn("The movie {} has not been enriched fully because of timeout", movie.getId());
                    } else {
                        Function<Movie, Movie> function = future.get();
                        movie = function.apply(movie);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    log.error("Step of movie {} enrichment was skipped", movie.getId(), e);
                }
            }
        } catch (InterruptedException e) {
            log.error("Enrichment of movie {} was interrupted", movie.getId(), e);
        }
        return movie;
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

    @Value("${web.movie.enrichTimeoutMS:5000}")
    public void setEnrichTimeout(int enrichTimeout) {
        this.enrichTimeout = enrichTimeout;
    }
}
