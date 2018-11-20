package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.Role;
import com.study.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                Role.getByName(resultSet.getString("role_name")));
    }
}