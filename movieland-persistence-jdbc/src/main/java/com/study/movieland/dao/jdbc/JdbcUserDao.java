package com.study.movieland.dao.jdbc;

import com.study.movieland.dao.UserDao;
import com.study.movieland.dao.jdbc.mapper.UserCheckedRowMapper;
import com.study.movieland.dao.jdbc.mapper.UserRowMapper;
import com.study.movieland.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class JdbcUserDao implements UserDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String GET_SQL = "SELECT u.id, u.name, u.role_name FROM \"user\" u WHERE u.id = ?";
    private static final String GET_ALL_SQL = "SELECT u.id, u.name, role_name FROM \"user\" u ORDER BY u.name";
    private static final String CHECK_PWD_SQL = "SELECT pswhash = crypt(?, u.salt) is_checked, u.id, u.name, u.role_name FROM \"user\" u WHERE u.email = ?";

    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final UserCheckedRowMapper USER_CHECKED_ROW_MAPPER = new UserCheckedRowMapper();


    private JdbcTemplate jdbcTemplate;

    @Override
    public User get(int id) {
        return jdbcTemplate.queryForObject(GET_SQL, USER_ROW_MAPPER, id);
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query(GET_ALL_SQL, USER_ROW_MAPPER);
    }

    @Override
    public User checkPassword(String name, String password) {
        try {
            return jdbcTemplate.queryForObject(CHECK_PWD_SQL, USER_CHECKED_ROW_MAPPER, password, name);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Unknown user is trying to connect: {}", name);
            return null;
        }
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}