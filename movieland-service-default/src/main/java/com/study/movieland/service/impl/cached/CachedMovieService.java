package com.study.movieland.service.impl.cached;

import com.study.movieland.data.MovieEnricherType;
import com.study.movieland.data.RequestParams;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.CurrencyService;
import com.study.movieland.service.EnrichmentService;
import com.study.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Primary
public class CachedMovieService implements MovieService {

    private final Map<Integer, SoftReference<Movie>> MOVIES = new ConcurrentHashMap<>();

    private MovieService movieService;
    private EnrichmentService enrichmentService;
    private CurrencyService currencyService;

    @Override
    public Movie get(int id) {
        return MOVIES.computeIfAbsent(id, (newMovie) -> {
            return new SoftReference<>(movieService.get(id));
        }).get();
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
        return movieService.create(movie);
    }

    @Override
    public Movie update(Movie movie) {
        return MOVIES.merge(movie.getId(), new SoftReference<>(movieService.update(movie)),
                (oldRef, newRef) -> {
                    Movie oldMovie = oldRef.get();
                    Movie newMovie = enrichmentService.enrichMovie(newRef.get(), EnumSet.of(MovieEnricherType.COUNTRY, MovieEnricherType.GENRES));
                    return new SoftReference<>(
                            new Movie.Builder()
                                    .id(oldMovie.getId())
                                    .nameRussian(newMovie.getNameRussian())
                                    .nameNative(newMovie.getNameNative())
                                    .yearOfRelease(oldMovie.getYearOfRelease())
                                    .description(oldMovie.getDescription())
                                    .rating(oldMovie.getRating())
                                    .price(oldMovie.getPrice())
                                    .picturePath(newMovie.getPicturePath())
                                    .countries(new ArrayList<>(newMovie.getCountries()))
                                    .genres(new ArrayList<>(newMovie.getGenres()))
                                    .reviews(new ArrayList<>(oldMovie.getReviews()))
                                    .build());
                }
        ).get();
    }

    @Override
    public Collection<Movie> getAll(RequestParams requestParams) {
        return movieService.getAll(requestParams);
    }

    @Override
    public Collection<Movie> getRandom(int count) {
        return movieService.getRandom(count);
    }

    @Override
    public Collection<Movie> getByGenre(int genreId, RequestParams requestParams) {
        return movieService.getByGenre(genreId, requestParams);
    }

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
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
