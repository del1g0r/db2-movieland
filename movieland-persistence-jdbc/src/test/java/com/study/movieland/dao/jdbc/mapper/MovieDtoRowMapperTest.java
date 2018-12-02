package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.dto.MovieDto;
import com.study.movieland.entity.Country;
import com.study.movieland.entity.Genre;
import org.junit.Test;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebConnection;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.*;;

public class MovieDtoRowMapperTest {

    @Test
    public void testMapMovieDto() throws Exception {

        MovieDtoRowMapper movieDtoRowMapper = new MovieDtoRowMapper();

        Array arrayOfGenrteIds = mock(Array.class);
        when(arrayOfGenrteIds.getArray()).thenReturn(new Integer[]{6, 7});

        Array arrayOfCountryIds = mock(Array.class);
        when(arrayOfCountryIds.getArray()).thenReturn(new Integer[]{8, 9});

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("original_name")).thenReturn("Movie 1");
        when(resultSet.getString("name")).thenReturn("Фильм 1");
        when(resultSet.getInt("year")).thenReturn(2000);
        when(resultSet.getDouble("rating")).thenReturn(100d);
        when(resultSet.getDouble("price")).thenReturn(101d);
        when(resultSet.getString("poster_url")).thenReturn("http://localhost/1.jpg");
        when(resultSet.getString("description")).thenReturn("Some text");
        when(resultSet.getArray("genre_ids")).thenReturn(arrayOfGenrteIds);
        when(resultSet.getArray("country_ids")).thenReturn(arrayOfCountryIds);

        MovieDto actualMovieDto = movieDtoRowMapper.mapRow(resultSet, 1);

        assertEquals(1, actualMovieDto.getId());
        assertEquals("Movie 1", actualMovieDto.getNameNative());
        assertEquals("Фильм 1", actualMovieDto.getNameRussian());
        assertEquals(2000, actualMovieDto.getYearOfRelease());
        assertEquals(100, actualMovieDto.getRating(), 0.0001);
        assertEquals(101, actualMovieDto.getPrice(), 0.0001);
        assertEquals("http://localhost/1.jpg", actualMovieDto.getPicturePath());
        assertEquals("Some text", actualMovieDto.getDescription());
        assertThat(actualMovieDto.getCountries(), is(Arrays.asList(
                new Country.Builder().id(8).build(),
                new Country.Builder().id(9).build()
        )));
        assertThat(actualMovieDto.getGenres(), is(Arrays.asList(
                new Genre.Builder().id(6).build(),
                new Genre.Builder().id(7).build()
        )));
    }
}
