package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.Country;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CountryRowMapperTest {

    @Test
    public void testMapCountry() throws Exception {

        CountryRowMapper countryRowMapper = new CountryRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Country 1");

        Country actualCountry = countryRowMapper.mapRow(resultSet, 1);

        assertEquals(1, actualCountry.getId());
        assertEquals("Country 1", actualCountry.getName());
    }
}

