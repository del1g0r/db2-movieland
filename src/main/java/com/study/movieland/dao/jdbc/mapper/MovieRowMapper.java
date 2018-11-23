package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements RowMapper<Movie> {

    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Movie.Builder()
                .id(resultSet.getInt("id"))
                .nameNative(resultSet.getString("original_name"))
                .nameRussian(resultSet.getString("name"))
                .yearOfRelease(resultSet.getInt("year"))
                .rating(resultSet.getFloat("rating"))
                .price(resultSet.getFloat("price"))
                .picturePath(resultSet.getString("poster_url"))
                .build();
    }
}