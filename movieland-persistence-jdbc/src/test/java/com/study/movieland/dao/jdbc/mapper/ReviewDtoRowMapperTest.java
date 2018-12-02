package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.dto.ReviewDto;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReviewDtoRowMapperTest {

    @Test
    public void testMapReviewDto() throws Exception {

        ReviewDtoRowMapper reviewDtoRowMapper = new ReviewDtoRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("review_text")).thenReturn("Review text 1");
        when(resultSet.getInt("user_id")).thenReturn(2);

        ReviewDto actualReviewDto = reviewDtoRowMapper.mapRow(resultSet, 1);

        assertEquals(1, actualReviewDto.getId());
        assertEquals("Review text 1", actualReviewDto.getText());
        assertEquals(2, actualReviewDto.getUser().getId());
    }
}
