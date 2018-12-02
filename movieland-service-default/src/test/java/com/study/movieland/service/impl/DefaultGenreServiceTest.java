package com.study.movieland.service.impl;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.entity.Genre;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

public class DefaultGenreServiceTest {

    @Test
    public void testGetIds() {
        DefaultGenreService genreService = new DefaultGenreService();
        genreService.setGenreDao(new StubGenreDao());

        int[] expectedIds = new int[]{1, 2};
        int[] actualIds = genreService.getIds(Arrays.asList(
                new Genre.Builder()
                        .id(1)
                        .name("Genre 1")
                        .build(),
                new Genre.Builder()
                        .id(2)
                        .name("Genre 2")
                        .build()
        ));

        Assert.assertThat(actualIds, is(expectedIds));
    }

    @Test
    public void testGet() {
        DefaultGenreService genreService = new DefaultGenreService();
        genreService.setGenreDao(new StubGenreDao());

        Genre expectedGenre = new Genre.Builder().id(1).name("Genre").build();
        Genre actualGenre = genreService.get(1);

        assertEquals(expectedGenre.getId(), actualGenre.getId());
        assertEquals(expectedGenre.getName(), actualGenre.getName());
    }

    @Test
    public void testGetSome() {
        DefaultGenreService genreService = new DefaultGenreService();
        genreService.setGenreDao(new StubGenreDao());

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
        Collection<Genre> actualGenres = genreService.getSome(new int[]{2, 3});

        Assert.assertThat(actualGenres, is(expectedGenres));
    }

    @Test
    public void testGetAll() {
        DefaultGenreService genreService = new DefaultGenreService();
        genreService.setGenreDao(new StubGenreDao());

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
        Collection<Genre> actualGenres = genreService.getAll();

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
