package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.entity.*;
import com.study.movieland.service.CountryService;
import com.study.movieland.service.CurrencyService;
import com.study.movieland.service.GenreService;
import com.study.movieland.service.ReviewService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DefaultMovieServiceTest {

    @Test
    public void testGet() {
        GenreService genreService = Mockito.mock(DefaultGenreService.class);
        when(genreService.get(1)).thenReturn(new Genre.Builder().id(1).name("Genre 1").build());
        when(genreService.get(2)).thenReturn(new Genre.Builder().id(2).name("Genre 2").build());
        when(genreService.enrich(any(Genre.class))).thenCallRealMethod();
        when(genreService.enrich(anyCollectionOf(Genre.class))).thenCallRealMethod();

        CountryService countryService = Mockito.mock(DefaultCountryService.class);
        when(countryService.get(1)).thenReturn(new Country.Builder().id(1).name("Country 1").build());
        when(countryService.get(2)).thenReturn(new Country.Builder().id(2).name("Country 2").build());
        when(countryService.enrich(any(Country.class))).thenCallRealMethod();
        when(countryService.enrich(anyCollectionOf(Country.class))).thenCallRealMethod();

        CurrencyService currencyService = Mockito.mock(DefaultCurrencyService.class);
        when(currencyService.get("USD")).thenReturn(new Currency.Builder().id(1).name("US dollar").rate(8).build());
        when(currencyService.get("EUR")).thenReturn(new Currency.Builder().id(2).name("Euro").rate(12).build());
        when(currencyService.exchange(anyDouble(), anyString())).thenCallRealMethod();

        ReviewService reviewService = Mockito.mock(ReviewService.class);
        when(reviewService.getByMovie(1)).thenReturn(
                Arrays.asList(
                        new Review.Builder()
                                .id(1)
                                .text("Some review text 1")
                                .user(new User.Builder().id(1).nickName("Some User 1").build())
                                .build(),
                        new Review.Builder()
                                .id(2)
                                .text("Some review text 2")
                                .user(new User.Builder().id(2).nickName("Some User 2").build())
                                .build())
        );

        MovieDao movieDao = Mockito.mock(MovieDao.class);
        when(movieDao.get(1)).thenReturn(
                new Movie.Builder()
                        .id(1)
                        .nameNative("Movie 1")
                        .nameRussian("Фильм 1")
                        .yearOfRelease(2000)
                        .rating(100)
                        .price(101)
                        .picturePath("http://localhost/1.jpg")
                        .description("Some text")
                        .genres(Arrays.asList(
                                new Genre.Builder().id(1).build(),
                                new Genre.Builder().id(2).build()
                        ))
                        .countries(Arrays.asList(
                                new Country.Builder().id(1).build(),
                                new Country.Builder().id(2).build()
                        ))
                        .build()
        );

        ParallelEnrichmentService enrichmentService = new ParallelEnrichmentService();
        DefaultMovieService movieService = new DefaultMovieService();
        enrichmentService.setGenreService(genreService);
        enrichmentService.setCountryService(countryService);
        enrichmentService.setReviewService(reviewService);
        enrichmentService.setEnrichTimeout(5000);
        movieService.setCurrencyService(currencyService);
        movieService.setEnrichmentService(enrichmentService);
        movieService.setMovieDao(movieDao);

        Movie expectedMovie = new Movie.Builder()
                .id(1)
                .nameNative("Movie 1")
                .nameRussian("Фильм 1")
                .yearOfRelease(2000)
                .rating(100)
                .price(101)
                .picturePath("http://localhost/1.jpg")
                .description("Some text")
                .genres(Arrays.asList(
                        new Genre.Builder().id(1).name("Genre 1").build(),
                        new Genre.Builder().id(2).name("Genre 2").build()
                ))
                .countries(Arrays.asList(
                        new Country.Builder().id(1).name("Country 1").build(),
                        new Country.Builder().id(2).name("Country 2").build()
                ))
                .reviews(Arrays.asList(
                        new Review.Builder()
                                .id(1)
                                .text("Some review text 1")
                                .user(new User.Builder().id(1).nickName("Some User 1").build())
                                .build(),
                        new Review.Builder()
                                .id(2)
                                .text("Some review text 2")
                                .user(new User.Builder().id(2).nickName("Some User 2").build())
                                .build()
                ))
                .build();
        Movie actualMovie = movieService.get(1, "UAH");

        assertEquals(expectedMovie.getId(), actualMovie.getId());
        assertEquals(expectedMovie.getNameNative(), actualMovie.getNameNative());
        assertEquals(expectedMovie.getNameRussian(), actualMovie.getNameRussian());
        assertEquals(expectedMovie.getYearOfRelease(), actualMovie.getYearOfRelease());
        assertEquals(expectedMovie.getRating(), actualMovie.getRating(), 0.0001);
        assertEquals(expectedMovie.getPrice(), actualMovie.getPrice(), 0.0001);
        assertEquals(expectedMovie.getPicturePath(), actualMovie.getPicturePath());
        assertEquals(expectedMovie.getDescription(), actualMovie.getDescription());
        Assert.assertThat(actualMovie.getGenres(), is(expectedMovie.getGenres()));
        Assert.assertThat(actualMovie.getCountries(), is(expectedMovie.getCountries()));
        Assert.assertThat(actualMovie.getReviews(), is(expectedMovie.getReviews()));
    }

    @Test
    public void testGetAll() {
        MovieDao movieDao = Mockito.mock(MovieDao.class);
        when(movieDao.getAll(any())).thenReturn(
                Arrays.asList(new Movie.Builder()
                                .id(1)
                                .nameNative("Movie 1")
                                .nameRussian("Фильм 1")
                                .yearOfRelease(2000)
                                .rating(100)
                                .price(101)
                                .picturePath("http://localhost/1.jpg")
                                .build(),
                        new Movie.Builder()
                                .id(2)
                                .nameNative("Movie 2")
                                .nameRussian("Фильм 2")
                                .yearOfRelease(2001)
                                .rating(200)
                                .price(201)
                                .picturePath("http://localhost/2.jpg")
                                .build())
        );

        DefaultMovieService movieService = new DefaultMovieService();
        movieService.setMovieDao(movieDao);

        Collection<Movie> expectedMovies = Arrays.asList(new Movie.Builder()
                        .id(1)
                        .nameNative("Movie 1")
                        .nameRussian("Фильм 1")
                        .yearOfRelease(2000)
                        .rating(100)
                        .price(101)
                        .picturePath("http://localhost/1.jpg")
                        .build(),
                new Movie.Builder()
                        .id(2)
                        .nameNative("Movie 2")
                        .nameRussian("Фильм 2")
                        .yearOfRelease(2001)
                        .rating(200)
                        .price(201)
                        .picturePath("http://localhost/2.jpg")
                        .build()
        );
        Collection<Movie> actualMovies = movieService.getAll(null);

        Assert.assertThat(actualMovies, is(expectedMovies));
    }

    @Test
    public void testGetRandom() {
        MovieDao movieDao = Mockito.mock(MovieDao.class);
        when(movieDao.getRandom(3)).thenReturn(
                Arrays.asList(new Movie.Builder()
                                .id(1)
                                .nameNative("Random movie 1")
                                .nameRussian("Фильм 1")
                                .yearOfRelease(2000)
                                .rating(100)
                                .price(101)
                                .picturePath("http://localhost/1.jpg")
                                .build(),
                        new Movie.Builder()
                                .id(2)
                                .nameNative("Random movie 2")
                                .nameRussian("Фильм 2")
                                .yearOfRelease(2001)
                                .rating(200)
                                .price(201)
                                .picturePath("http://localhost/2.jpg")
                                .build())
        );

        DefaultMovieService movieService = new DefaultMovieService();
        movieService.setMovieDao(movieDao);

        Collection<Movie> expectedMovies = Arrays.asList(new Movie.Builder()
                        .id(1)
                        .nameNative("Random movie 1")
                        .nameRussian("Фильм 1")
                        .yearOfRelease(2000)
                        .rating(100)
                        .price(101)
                        .picturePath("http://localhost/1.jpg")
                        .build(),
                new Movie.Builder()
                        .id(2)
                        .nameNative("Random movie 2")
                        .nameRussian("Фильм 2")
                        .yearOfRelease(2001)
                        .rating(200)
                        .price(201)
                        .picturePath("http://localhost/2.jpg")
                        .build()
        );
        Collection<Movie> actualMovies = movieService.getRandom(3);

        Assert.assertThat(actualMovies, is(expectedMovies));
    }
}