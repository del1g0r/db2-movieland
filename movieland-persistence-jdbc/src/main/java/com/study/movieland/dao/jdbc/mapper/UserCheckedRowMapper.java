package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserCheckedRowMapper extends UserRowMapper {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        if (resultSet.getBoolean("is_checked")) {
            return super.mapRow(resultSet, i);
        }
        return null;
    }
}