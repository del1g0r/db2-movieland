package com.study.movieland.service.impl;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.dao.MovieDao;
import com.study.movieland.entity.Genre;
import com.study.movieland.entity.Movie;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class DefaultGenreServiceTest {

    @Test
    public void testGetAll() {
        DefaultGenreService genreService = new DefaultGenreService();
        GenreDao genreDao = new MockGenreDao();
        genreService.setGenreDao(genreDao);

        List<Genre> actualMovies = genreService.getAll();

        Assert.assertThat(actualMovies, is(
                Arrays.asList(
                        new Genre.Builder()
                                .id(1)
                                .name("Genre 1")
                                .build(),
                        new Genre.Builder()
                                .id(2)
                                .name("Genre 2")
                                .build(),
                        new Genre.Builder()
                                .id(3)
                                .name("Genre 3")
                                .build()
                )));
    }

    class MockGenreDao implements GenreDao {
        @Override
        public List<Genre> getAll() {
            return Arrays.asList(
                    new Genre.Builder()
                            .id(1)
                            .name("Genre 1")
                            .build(),
                    new Genre.Builder()
                            .id(2)
                            .name("Genre 2")
                            .build(),
                    new Genre.Builder()
                            .id(3)
                            .name("Genre 3")
                            .build());
        }
    }
}