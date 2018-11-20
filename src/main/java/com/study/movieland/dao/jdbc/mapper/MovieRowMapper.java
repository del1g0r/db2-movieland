package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements RowMapper<Movie> {

    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        Movie movie = new Movie();
        movie.setId(resultSet.getInt("id"));
        movie.setNameRussian(resultSet.getString("name"));
        movie.setNameNative(resultSet.getString("original_name"));
        movie.setYearOfRelease(resultSet.getInt("year"));
        movie.setDescription(resultSet.getString("description"));
        movie.setRating(resultSet.getFloat("rating"));
        movie.setPrice(resultSet.getFloat("price"));
        movie.setPicturePath(resultSet.getString("poster_url"));
        return movie;
    }
}