package com.study.movieland.service.impl.cached;

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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Primary
public class CachedMovieService implements MovieService {

    private final Map<Integer, SoftReference<Movie>> MOVIES = new ConcurrentHashMap<>();

    private MovieService movieService;
    private EnrichmentService enrichmentService;
    private CurrencyService currencyService;

    private Movie getFromCache(int id) {
        SoftReference<Movie> ref = MOVIES.get(id);
        return ref != null ? ref.get() : null;
    }

    private void putToCache(Movie movie) {
        MOVIES.put(movie.getId(), new SoftReference<>(movie));
    }

    @Override
    public Movie get(int id) {
        Movie movie = getFromCache(id);
        if (movie != null) {
            return movie;
        }
        movie = movieService.get(id);
        putToCache(movie);
        return movie;
    }

    @Override
    public Movie get(int id, String currencyCode) {
        Movie movie = get(id);
        return new Movie.Builder(movie)
                .price(currencyService.exchange(movie.getPrice(), currencyCode))
                .build();
    }

    @Override
    public void create(Movie movie) {
        movieService.create(movie);
    }

    @Override
    public void update(Movie movie) {
        movieService.update(movie);
        Movie cachedMovie = getFromCache(movie.getId());
        if (cachedMovie != null) {
            movie = enrichmentService.enrichMovie(movie);
            cachedMovie = new Movie.Builder(cachedMovie)
                    .nameNative(movie.getNameNative())
                    .nameRussian(movie.getNameRussian())
                    .picturePath(movie.getPicturePath())
                    .genres(new ArrayList<>(movie.getGenres()))
                    .countries(new ArrayList<>(movie.getCountries()))
                    .build();
            putToCache(cachedMovie);
        }
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
