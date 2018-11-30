package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.dao.jdbc.mapper.GenreRowMapper;
import com.study.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class JdbcGenreDao implements GenreDao {

    private static final String GET_SQL = "SELECT g.id, g.name FROM genre g ORDER BY g.name WHERE g.id = ?";
    private static final String GET_SOME_SQL = "SELECT g.id, g.name FROM genre g WHERE g.id IN ( SELECT UNNEST(?) ) ORDER BY g.name";
    private static final String GET_ALL_SQL = "SELECT g.id, g.name FROM genre g ORDER BY g.name";
    private static final GenreRowMapper GENRE_ROW_MAPPER = new GenreRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Override
    public Genre get(int id) {
        return jdbcTemplate.queryForObject(GET_SQL, GENRE_ROW_MAPPER, id);
    }

    @Override
    public Collection<Genre> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, GENRE_ROW_MAPPER);
    }

    @Override
    public Collection<Genre> getSome(int[] ids) {
        return jdbcTemplate.query(GET_SOME_SQL, GENRE_ROW_MAPPER, ids);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}