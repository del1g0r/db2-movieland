package com.study.movieland.dao.cached;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.entity.Genre;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

public class CachedGenreDaoTest {

    @Test
    public void testGet() {
        CachedGenreDao cachedGenreDao = new CachedGenreDao();
        cachedGenreDao.setGenreDao(new StubGenreDao());
        cachedGenreDao.refresh();

        Genre expectedGenre = new Genre.Builder().id(1).name("Genre 1").build();
        Genre actualGenre = cachedGenreDao.get(1);

        assertEquals(expectedGenre.getId(), actualGenre.getId());
        assertEquals(expectedGenre.getName(), actualGenre.getName());
    }

    @Test
    public void testGetAll() {
        CachedGenreDao cachedGenreDao = new CachedGenreDao();
        cachedGenreDao.setGenreDao(new StubGenreDao());
        cachedGenreDao.refresh();

        Collection<Genre> expectedGenres = Arrays.asList(
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
        );
        Collection<Genre> actualGenres = cachedGenreDao.getAll();

        Assert.assertThat(actualGenres, is(expectedGenres));
    }

    @Test
    public void testGetSome() {
        CachedGenreDao cachedGenreDao = new CachedGenreDao();
        cachedGenreDao.setGenreDao(new StubGenreDao());
        cachedGenreDao.refresh();

        Collection<Genre> expectedGenres = Arrays.asList(
                new Genre.Builder()
                        .id(2)
                        .name("Genre 2")
                        .build(),
                new Genre.Builder()
                        .id(3)
                        .name("Genre 3")
                        .build()
        );
        Collection<Genre> actualGenres = cachedGenreDao.getSome(new int[]{2, 3});

        Assert.assertThat(actualGenres, is(expectedGenres));
    }

    class StubGenreDao implements GenreDao {
        @Override
        public Genre get(int id) {
            return new Genre.Builder()
                    .id(1)
                    .name("Genre")
                    .build();
        }

        @Override
        public Collection<Genre> getAll() {
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
        public Collection<Genre> getSome(int[] ids) {
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