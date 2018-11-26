package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.study.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.StringJoiner;

@Repository
public class JdbcMovieDao implements MovieDao {

    private static final String GET_ALL_SQL = "SELECT m.id, m.name, m.original_name, m.year, m.description, m.rating, m.price, p.poster_url FROM movie m LEFT JOIN poster p ON p.movie_id = m.id";
    private static final String GET_RANDOM_SQL = "SELECT m.id, m.name, m.original_name, m.year, m.description, m.rating, m.price, p.poster_url FROM movie m LEFT JOIN poster p ON p.movie_id = m.id ORDER BY RANDOM() LIMIT ?";
    private static final String GET_BY_GENRE_SQL = "SELECT m.id, m.name, m.original_name, m.year, m.description, m.rating, m.price, p.poster_url FROM movie m JOIN movie_genre mg ON mg.movie_id = m.id LEFT JOIN poster p ON p.movie_id = m.id WHERE mg.genre_id = ?";
    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Movie> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getSortedAll(Sort sort) {
        if (sort == null || sort.isUnsorted()) {
            return getAll();
        }
        StringJoiner orderSql = new StringJoiner(",", " ORDER BY ", "");
        for (Sort.Order order : sort) {
            orderSql.add(order.getProperty() + ' ' + order.getDirection().toString());
        }
        return jdbcTemplate.query(GET_ALL_SQL + orderSql, MOVIE_ROW_MAPPER);
    }

    @Override
    public List<Movie> getRandom(int count) {
        return jdbcTemplate.query(GET_RANDOM_SQL, MOVIE_ROW_MAPPER, count);
    }

    @Override
    public List<Movie> getByGenre(int genreId) {
        return jdbcTemplate.query(GET_BY_GENRE_SQL, MOVIE_ROW_MAPPER, genreId);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}