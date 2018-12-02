package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.dto.ReviewDto;
import com.study.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewDtoRowMapper implements RowMapper<ReviewDto> {

    @Override
    public ReviewDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return new ReviewDto.Builder()
                .id(resultSet.getInt("id"))
                .user(new User.Builder().id(resultSet.getInt("user_id")).build())
                .text(resultSet.getString("review_text"))
                .build();
    }
}