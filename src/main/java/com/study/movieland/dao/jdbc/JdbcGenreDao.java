package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.GenreDao;
import com.study.movieland.dao.jdbc.mapper.GenreRowMapper;
import com.study.movieland.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {

    private static final String GET_ALL_SQL = "SELECT g.id, g.name FROM genre g ORDER BY g.name";
    private static final GenreRowMapper GENRE_ROW_MAPPER = new GenreRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, GENRE_ROW_MAPPER);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}