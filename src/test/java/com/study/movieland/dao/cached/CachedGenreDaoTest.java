package com.study.movieland.dao.cached;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.entity.Genre;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

public class CachedGenreDaoTest {

    @Test
    public void testGetAll() {
        CachedGenreDao cachedGenreDao = new CachedGenreDao();
        GenreDao genreDao = new StubGenreDao();
        cachedGenreDao.setGenreDao(genreDao);
        cachedGenreDao.refreshTime = LocalDateTime.MIN;
        cachedGenreDao.genres = Arrays.asList(
                new Genre.Builder()
                        .id(1)
                        .name("Cached genre 1")
                        .build(),
                new Genre.Builder()
                        .id(2)
                        .name("Cached genre 2")
                        .build()
        );

        List<Genre> actualGenres = cachedGenreDao.getAll();

        Assert.assertThat(actualGenres, is(
                Arrays.asList(
                        new Genre.Builder()
                                .id(1)
                                .name("Genre 1")
                                .build(),
                        new Genre.Builder()
                                .id(2)
                                .name("Genre 2")
                                .build()
                )));
    }

    @Test
    public void testGetAllCached() {
        CachedGenreDao cachedGenreDao = new CachedGenreDao();
        GenreDao genreDao = new StubGenreDao();
        cachedGenreDao.setGenreDao(genreDao);
        cachedGenreDao.refreshTime = LocalDateTime.now().plusMinutes(10);
        cachedGenreDao.genres = Arrays.asList(
                new Genre.Builder()
                        .id(1)
                        .name("Cached genre 1")
                        .build(),
                new Genre.Builder()
                        .id(2)
                        .name("Cached genre 2")
                        .build()
        );

        List<Genre> actualGenres = cachedGenreDao.getAll();

        Assert.assertThat(actualGenres, is(
                Arrays.asList(
                        new Genre.Builder()
                                .id(1)
                                .name("Cached genre 1")
                                .build(),
                        new Genre.Builder()
                                .id(2)
                                .name("Cached genre 2")
                                .build()
                )));
    }

    class StubGenreDao implements GenreDao {
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
                            .build());
        }
    }
}