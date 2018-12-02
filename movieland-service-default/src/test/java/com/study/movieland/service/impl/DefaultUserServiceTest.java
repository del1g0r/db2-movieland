package com.study.movieland.service.impl;

import com.study.movieland.dao.UserDao;
import com.study.movieland.entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

public class DefaultUserServiceTest {

    @Test
    public void testGet() {
        DefaultUserService UserService = new DefaultUserService();
        UserService.setUserDao(new StubUserDao());

        User expectedUser = new User.Builder().id(1).nickName("User").build();
        User actualUser = UserService.get(1);

        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getNickName(), actualUser.getNickName());
    }

    @Test
    public void testGetAll() {
        DefaultUserService UserService = new DefaultUserService();
        UserService.setUserDao(new StubUserDao());

        Collection<User> expectedCountries = Arrays.asList(
                new User.Builder()
                        .id(1)
                        .nickName("User 1")
                        .build(),
                new User.Builder()
                        .id(2)
                        .nickName("User 2")
                        .build(),
                new User.Builder()
                        .id(3)
                        .nickName("User 3")
                        .build()
        );
        Collection<User> actualCountries = UserService.getAll();

        Assert.assertThat(actualCountries, is(expectedCountries));
    }

    class StubUserDao implements UserDao {
        @Override
        public User get(int id) {
            return new User.Builder()
                    .id(1)
                    .nickName("User")
                    .build();
        }

        @Override
        public Collection<User> getAll() {
            return Arrays.asList(
                    new User.Builder()
                            .id(1)
                            .nickName("User 1")
                            .build(),
                    new User.Builder()
                            .id(2)
                            .nickName("User 2")
                            .build(),
                    new User.Builder()
                            .id(3)
                            .nickName("User 3")
                            .build());
        }
    }
}
