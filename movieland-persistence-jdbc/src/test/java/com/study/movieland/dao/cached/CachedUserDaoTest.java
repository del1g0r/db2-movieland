package com.study.movieland.dao.cached;

import com.study.movieland.dao.UserDao;
import com.study.movieland.entity.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

public class CachedUserDaoTest {

    @Test
    public void testGet() {
        CachedUserDao cachedUserDao = new CachedUserDao();
        cachedUserDao.setUserDao(new StubUserDao());
        cachedUserDao.refresh();

        User expectedUser = new User.Builder().id(1).nickName("User name 1").build();
        User actualUser = cachedUserDao.get(1);
        
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getNickName(), actualUser.getNickName());
    }

    @Test
    public void testGetAll() {
        CachedUserDao cachedUserDao = new CachedUserDao();
        cachedUserDao.setUserDao(new StubUserDao());
        cachedUserDao.refresh();

        Collection<User> expectedUsers = Arrays.asList(
                new User.Builder()
                        .id(1)
                        .nickName("User name 1")
                        .build(),
                new User.Builder()
                        .id(2)
                        .nickName("User name 2")
                        .build()
        );
        Collection<User> actualUsers = cachedUserDao.getAll();

        Assert.assertThat(actualUsers, is(expectedUsers));
    }

    class StubUserDao implements UserDao {
        @Override
        public User get(int id) {
            return new User.Builder()
                    .id(1)
                    .nickName("User name")
                    .build();
        }

        @Override
        public Collection<User> getAll() {
            return Arrays.asList(
                    new User.Builder()
                            .id(1)
                            .nickName("User name 1")
                            .build(),
                    new User.Builder()
                            .id(2)
                            .nickName("User name 2")
                            .build());
        }
    }
}