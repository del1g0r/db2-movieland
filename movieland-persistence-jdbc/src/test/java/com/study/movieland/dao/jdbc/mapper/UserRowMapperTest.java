package com.study.movieland.dao.jdbc.mapper;

import com.study.movieland.entity.User;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {

    @Test
    public void testMapUser() throws Exception {

        UserRowMapper userRowMapper = new UserRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("Mick name 1");

        User actualUser = userRowMapper.mapRow(resultSet, 1);

        assertEquals(1, actualUser.getId());
        assertEquals("Mick name 1", actualUser.getNickName());
    }
}

