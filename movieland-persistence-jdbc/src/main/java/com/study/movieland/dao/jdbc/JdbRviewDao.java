package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.ReviewDao;
import com.study.movieland.dao.jdbc.mapper.ReviewRowMapper;
import com.study.movieland.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class JdbRviewDao implements ReviewDao {

    private static final String GET_SQL = "SELECT r.id, r.user_id, r.review_text FROM review r WHERE r.id = ?";
    private static final String GET_BY_USER_SQL = "SELECT r.id, r.user_id, r.review_text FROM review r WHERE r.movie_id = ?";
    private static final ReviewRowMapper REVIEW_ROW_MAPPER = new ReviewRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Override
    public Collection<Review> getByMovie(int movieId) {
        return jdbcTemplate.query(GET_BY_USER_SQL, REVIEW_ROW_MAPPER, movieId);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}