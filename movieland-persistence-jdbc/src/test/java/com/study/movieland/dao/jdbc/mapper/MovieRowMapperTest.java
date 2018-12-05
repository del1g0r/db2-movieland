package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.Movie;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieRowMapperTest {

    @Test
    public void testMapMovie() throws Exception {

        MovieRowMapper movieRowMapper = new MovieRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("original_name")).thenReturn("Movie 1");
        when(resultSet.getString("name")).thenReturn("Фильм 1");
        when(resultSet.getInt("year")).thenReturn(2000);
        when(resultSet.getDouble("rating")).thenReturn(100d);
        when(resultSet.getDouble("price")).thenReturn(101d);
        when(resultSet.getString("poster_url")).thenReturn("http://localhost/1.jpg");
        when(resultSet.getString("description")).thenReturn("Some text");

        Movie actualMovie = movieRowMapper.mapRow(resultSet, 1);

        assertEquals(1, actualMovie.getId());
        assertEquals("Movie 1", actualMovie.getNameNative());
        assertEquals("Фильм 1", actualMovie.getNameRussian());
        assertEquals(2000, actualMovie.getYearOfRelease());
        assertEquals(100, actualMovie.getRating(), 0.0001);
        assertEquals(101, actualMovie.getPrice(), 0.0001);
        assertEquals("http://localhost/1.jpg", actualMovie.getPicturePath());
    }
}

