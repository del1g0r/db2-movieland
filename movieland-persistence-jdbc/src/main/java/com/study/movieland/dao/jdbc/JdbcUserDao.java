package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.UserDao;
import com.study.movieland.dao.jdbc.mapper.UserRowMapper;
import com.study.movieland.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class JdbcUserDao implements UserDao {

    private static final String GET_SQL = "SELECT u.id, u.name nickname FROM \"user\" u WHERE u.id = ?";
    private static final String GET_ALL_SQL = "SELECT u.id, u.name nickname FROM \"user\" u ORDER BY u.name";
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Override
    public User get(int id) {
        return jdbcTemplate.queryForObject(GET_SQL, USER_ROW_MAPPER, id);
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, USER_ROW_MAPPER);
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}