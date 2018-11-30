package com.study.movieland.service.impl;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.entity.Genre;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class DefaultGenreServiceTest {

    @Test
    public void testGetAll() {
        DefaultGenreService genreService = new DefaultGenreService();
        GenreDao genreDao = new MockGenreDao();
        genreService.setGenreDao(genreDao);

        Collection<Genre> actualGenres = genreService.getAll();

        Assert.assertThat(actualGenres, is(
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
        public Genre get(int id) {
            return null;
        }

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

        @Override
        public List<Genre> getSome(int[] ids) {
            return null;
        }
    }
}
