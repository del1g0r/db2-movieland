package com.study.movieland.service.impl;

import com.study.movieland.dao.UserDao;
import com.study.movieland.entity.User;
import com.study.movieland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DefaultUserService implements UserService {

    private UserDao userDao;

    @Override
    public User get(int id) {
        return userDao.get(id);
    }

    @Override
    public Collection<User> getAll() {
        return userDao.getAll();
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
