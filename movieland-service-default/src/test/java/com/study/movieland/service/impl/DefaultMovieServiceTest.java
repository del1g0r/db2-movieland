package com.study.movieland.service.impl;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.data.RequestParams;
import com.study.movieland.entity.Movie;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

public class DefaultMovieServiceTest {

    @Test
    public void testGetAll() {
        DefaultMovieService movieService = new DefaultMovieService();
        MovieDao movieDao = new MockMovieDao();
        movieService.setMovieDao(movieDao);
        RequestParams requestParams = new RequestParams.Builder().build();

        List<Movie> actualMovies = movieService.getAll(requestParams);

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
        MovieDao movieDao = new MockMovieDao();
        movieService.setMovieDao(movieDao);

        List<Movie> actualMovies = movieService.getRandom(3);

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

    class MockMovieDao implements MovieDao {
        @Override
        public List<Movie> getAll(RequestParams requestParams) {
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
        public List<Movie> getRandom(int count) {
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
        public List<Movie> getByGenre(int genreId, RequestParams requestParams) {
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
}