package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.study.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcMovieDao implements MovieDao {

    private static final String GET_ALL_SQL = "SELECT m.id, m.name, m.original_name, m.year, m.description, m.rating, m.price, p.poster_url FROM movie m LEFT JOIN poster p ON p.movie_id = m.id";
    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Movie> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, MOVIE_ROW_MAPPER);
    }
}