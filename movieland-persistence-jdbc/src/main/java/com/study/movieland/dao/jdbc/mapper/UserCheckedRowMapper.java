package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCheckedRowMapper implements RowMapper<User> {

    private final UserRowMapper mapper = new UserRowMapper();

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        if (resultSet.getBoolean("is_checked")) {
            return mapper.mapRow(resultSet, i);
        }
        return null;
    }
}