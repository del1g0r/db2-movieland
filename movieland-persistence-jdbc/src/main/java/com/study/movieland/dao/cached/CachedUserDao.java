package com.study.movieland.dao.cached;

import com.study.movieland.dao.UserDao;
import com.study.movieland.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Repository
@Primary
public class CachedUserDao implements UserDao {

    private UserDao userDao;
    private volatile HashMap<Integer, User> users;

    @PostConstruct
    public void refresh() {
        HashMap<Integer, User> users = new HashMap<>();
        for (User user : userDao.getAll()) {
            users.put(user.getId(), user);
        }
        this.users = users;
    }

    @Override
    public User get(int id) {
        return users.get(id);
    }

    @Override
    public Collection<User> getAll() {
        return new ArrayList<>(this.users.values());
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}