package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.MovieDao;
import com.study.movieland.dao.jdbc.mapper.MovieDtoRowMapper;
import com.study.movieland.dao.jdbc.mapper.MovieRowMapper;
import com.study.movieland.dao.jdbc.sql.SqlGenerator;
import com.study.movieland.data.RequestParams;
import com.study.movieland.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class JdbcMovieDao implements MovieDao {

    private static final String GET_SQL = "SELECT m.id, m.name, m.original_name, m.year, m.rating, m.price, m.description, p.poster_url, ARRAY(SELECT mg.genre_id FROM movie_genre mg WHERE mg.movie_id = m.id) genre_ids, ARRAY(SELECT mc.country_id FROM movie_country mc WHERE mc.movie_id = m.id) country_ids FROM movie m LEFT JOIN poster p ON p.movie_id = m.id WHERE m.id = ?";
    private static final String GET_ALL_SQL = "SELECT m.id, m.name, m.original_name, m.year, m.rating, m.price, p.poster_url FROM movie m LEFT JOIN poster p ON p.movie_id = m.id";
    private static final String GET_RANDOM_SQL = "SELECT m.id, m.name, m.original_name, m.year, m.rating, m.price, p.poster_url FROM movie m LEFT JOIN poster p ON p.movie_id = m.id ORDER BY RANDOM() LIMIT ?";
    private static final String GET_BY_GENRE_SQL = "SELECT m.id, m.name, m.original_name, m.year, m.rating, m.price, p.poster_url FROM movie m JOIN movie_genre mg ON mg.movie_id = m.id LEFT JOIN poster p ON p.movie_id = m.id WHERE mg.genre_id = ?";
    private static final MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private static final MovieDtoRowMapper MOVIEDAO_ROW_MAPPER = new MovieDtoRowMapper();

    private JdbcTemplate jdbcTemplate;
    private SqlGenerator sqlGenerator;

    @Override
    public Movie get(int id) {
        return jdbcTemplate.queryForObject(GET_SQL, MOVIEDAO_ROW_MAPPER, id);
    }

    @Override
    public Collection<Movie> getAll(RequestParams requestParams) {
        return jdbcTemplate.query(sqlGenerator.getSQL(GET_ALL_SQL, requestParams), MOVIE_ROW_MAPPER);
    }

    @Override
    public Collection<Movie> getRandom(int count) {
        return jdbcTemplate.query(GET_RANDOM_SQL, MOVIE_ROW_MAPPER, count);
    }

    @Override
    public Collection<Movie> getByGenre(int genreId, RequestParams requestParams) {
        return jdbcTemplate.query(sqlGenerator.getSQL(GET_BY_GENRE_SQL, requestParams), MOVIE_ROW_MAPPER, genreId);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setSqlGenerator(SqlGenerator sqlGenerator) {
        this.sqlGenerator = sqlGenerator;
    }
}