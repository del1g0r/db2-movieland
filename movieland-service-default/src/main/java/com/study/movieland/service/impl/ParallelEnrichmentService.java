package com.study.movieland.service.impl;

import com.study.movieland.data.MovieEnricherType;
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

    public Movie enrichMovie(Movie movie, Set<MovieEnricherType> enricherTypes) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();
        Movie finalMovie = movie;
        Collection<Callable<Function<Movie.Builder, Movie.Builder>>> tasks = new ArrayList<>();

        if (enricherTypes.contains(MovieEnricherType.GENRES)) {
            tasks.add(() -> {
                MDC.setContextMap(contextMap);
                Collection<Genre> genres = genreService.enrich(finalMovie.getGenres());
                MDC.clear();
                return (r) -> {
                    r.genres(genres);
                    return r;
                };
            });
        }

        if (enricherTypes.contains(MovieEnricherType.COUNTRY)) {
            tasks.add(() -> {
                MDC.setContextMap(contextMap);
                Collection<Country> countries = countryService.enrich(finalMovie.getCountries());
                MDC.clear();
                return (r) -> {
                    r.countries(countries);
                    return r;
                };
            });
        }

        if (enricherTypes.contains(MovieEnricherType.REVIEWS)) {
            tasks.add(() -> {
                MDC.setContextMap(contextMap);
                Collection<Review> reviews = reviewService.getByMovie(finalMovie.getId());
                MDC.clear();
                return (r) -> {
                    r.reviews(reviews);
                    return r;
                };
            });
        }

        try {
            List<Future<Function<Movie.Builder, Movie.Builder>>> futures = executorService.invokeAll(
                    tasks,
                    enrichTimeout,
                    TimeUnit.MILLISECONDS);

            Movie.Builder builder = new Movie.Builder()
                    .id(movie.getId())
                    .nameRussian(movie.getNameRussian())
                    .nameNative(movie.getNameNative())
                    .yearOfRelease(movie.getYearOfRelease())
                    .description(movie.getDescription())
                    .rating(movie.getRating())
                    .price(movie.getPrice())
                    .picturePath(movie.getPicturePath())
                    .countries(new ArrayList<>())
                    .genres(new ArrayList<>())
                    .reviews(new ArrayList<>());

            for (Future<Function<Movie.Builder, Movie.Builder>> future : futures) {
                try {
                    if (future.isCancelled()) {
                        log.warn("The movie {} has not been enriched fully because of timeout", movie.getId());
                    } else {
                        Function<Movie.Builder, Movie.Builder> function = future.get();
                        builder = function.apply(builder);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    log.error("Step of movie {} enrichment was skipped", movie.getId(), e);
                }
            }
            return builder.build();
        } catch (InterruptedException e) {
            log.error("Enrichment of movie {} was interrupted", movie.getId(), e);
            return null;
        }
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
