package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.Country;
import com.study.movieland.entity.Genre;
import com.study.movieland.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDtoRowMapper implements RowMapper<Movie> {

    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        for (int id : (Integer[]) resultSet.getArray("genre_ids").getArray()) {
            genres.add(new Genre.Builder().id(id).build());
        }
        List<Country> countries = new ArrayList<>();
        for (int id : (Integer[]) resultSet.getArray("country_ids").getArray()) {
            countries.add(new Country.Builder().id(id).build());
        }
        return new Movie.Builder()
                .id(resultSet.getInt("id"))
                .nameNative(resultSet.getString("original_name"))
                .nameRussian(resultSet.getString("name"))
                .yearOfRelease(resultSet.getInt("year"))
                .rating(resultSet.getDouble("rating"))
                .price(resultSet.getDouble("price"))
                .picturePath(resultSet.getString("poster_url"))
                .description(resultSet.getString("description"))
                .countries(countries)
                .genres(genres)
                .build();
    }
}