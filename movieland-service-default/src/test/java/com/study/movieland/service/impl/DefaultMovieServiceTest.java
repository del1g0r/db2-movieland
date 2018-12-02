package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.data.RequestParams;
import com.study.movieland.dto.MovieDto;
import com.study.movieland.dto.ReviewDto;
import com.study.movieland.entity.*;
import com.study.movieland.service.CountryService;
import com.study.movieland.service.CurrencyService;
import com.study.movieland.service.GenreService;
import com.study.movieland.service.ReviewService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;

public class DefaultMovieServiceTest {

    @Test
    public void testGet() {
        DefaultMovieService movieService = new DefaultMovieService();
        movieService.setGenreService(new StubGenreService());
        movieService.setCountryService(new StubCountryService());
        movieService.setReviewService(new StubReviewService());
        movieService.setCurrencyService(new StubCurrencyService());
        movieService.setMovieDao(new StubMovieDao());

        MovieDto expectedMovie = new MovieDto.Builder()
                .id(1)
                .nameNative("Movie 1")
                .nameRussian("Фильм 1")
                .yearOfRelease(2000)
                .rating(100)
                .price(101)
                .picturePath("http://localhost/1.jpg")
                .description("Spme text")
                .genres(Arrays.asList(
                        new Genre.Builder().id(1).name("Genre 1").build(),
                        new Genre.Builder().id(2).name("Genre 2").build()
                ))
                .countries(Arrays.asList(
                        new Country.Builder().id(1).name("Country 1").build(),
                        new Country.Builder().id(2).name("Country 2").build()
                ))
                .reviews(Arrays.asList(
                        new ReviewDto.Builder()
                                .id(1)
                                .text("Some review text 1")
                                .user(new User.Builder().id(1).nickName("Some User 1").build())
                                .build(),
                        new ReviewDto.Builder()
                                .id(2)
                                .text("Some review text 2")
                                .user(new User.Builder().id(1).nickName("Some User 2").build())
                                .build()
                ))
                .build();
        MovieDto actualMovie = movieService.get(1, "UAH");

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
        DefaultMovieService movieService = new DefaultMovieService();
        MovieDao movieDao = new StubMovieDao();
        movieService.setMovieDao(movieDao);
        RequestParams requestParams = new RequestParams.Builder().build();

        Collection<Movie> actualMovies = movieService.getAll(requestParams);

        Assert.assertThat(actualMovies, is(
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
                                .build())));
    }

    @Test
    public void testGetRandom() {
        DefaultMovieService movieService = new DefaultMovieService();
        movieService.setMovieDao(new StubMovieDao());

        Collection<Movie> actualMovies = movieService.getRandom(3);

        Assert.assertThat(actualMovies, is(
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
                                .build())));
    }

    class StubMovieDao implements MovieDao {
        @Override
        public MovieDto get(int id) {
            return new MovieDto.Builder()
                    .id(1)
                    .nameNative("Movie 1")
                    .nameRussian("Фильм 1")
                    .yearOfRelease(2000)
                    .rating(100)
                    .price(101)
                    .picturePath("http://localhost/1.jpg")
                    .description("Spme text")
                    .genres(Arrays.asList(
                            new Genre.Builder().id(1).build(),
                            new Genre.Builder().id(2).build()
                    ))
                    .countries(Arrays.asList(
                            new Country.Builder().id(1).build(),
                            new Country.Builder().id(2).build()
                    ))
                    .build();
        }

        @Override
        public Collection<Movie> getAll(RequestParams requestParams) {
            return Arrays.asList(new Movie.Builder()
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
                            .build());
        }

        @Override
        public Collection<Movie> getRandom(int count) {
            return Arrays.asList(new Movie.Builder()
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
                            .build());
        }

        @Override
        public Collection<Movie> getByGenre(int genreId, RequestParams requestParams) {
            return Arrays.asList(new Movie.Builder()
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
                            .build());
        }
    }

    class StubGenreService implements GenreService {
        @Override
        public int[] getIds(Collection<Genre> genres) {
            return new int[0];
        }

        @Override
        public Genre get(int id) {
            return null;
        }

        @Override
        public Collection<Genre> getSome(int[] ids) {
            return Arrays.asList(
                    new Genre.Builder()
                            .id(1)
                            .name("Genre 1")
                            .build(),
                    new Genre.Builder()
                            .id(2)
                            .name("Genre 2")
                            .build()
            );
        }

        @Override
        public Collection<Genre> getAll() {
            return null;
        }
    }

    class StubCountryService implements CountryService {
        @Override
        public int[] getIds(Collection<Country> countries) {
            return new int[0];
        }

        @Override
        public Country get(int id) {
            return null;
        }

        @Override
        public Collection<Country> getSome(int[] ids) {
            return Arrays.asList(
                    new Country.Builder()
                            .id(1)
                            .name("Country 1")
                            .build(),
                    new Country.Builder()
                            .id(2)
                            .name("Country 2")
                            .build());
        }

        @Override
        public Collection<Country> getAll() {
            return null;
        }
    }

    class StubReviewService implements ReviewService {
        @Override
        public ReviewDto get(int id) {
            return new ReviewDto.Builder()
                    .id(1)
                    .text("Some review text")
                    .user(new User.Builder().id(1).nickName("Some User").build())
                    .build();
        }

        @Override
        public Collection<ReviewDto> getByMovie(int movieId) {
            return Arrays.asList(
                    new ReviewDto.Builder()
                            .id(1)
                            .text("Some review text 1")
                            .user(new User.Builder().id(1).nickName("Some User 1").build())
                            .build(),
                    new ReviewDto.Builder()
                            .id(2)
                            .text("Some review text 2")
                            .user(new User.Builder().id(1).nickName("Some User 2").build())
                            .build());
        }
    }

    class StubCurrencyService implements CurrencyService {

        @Override
        public Currency get(String code) {
            return null;
        }

        @Override
        public Collection<Currency> getAll() {
            return null;
        }
    }
}