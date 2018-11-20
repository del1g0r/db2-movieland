package com.study.movieland.service.impl;

import com.study.movieland.dao.UserDao;
import com.study.movieland.entity.User;
import com.study.movieland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultUserService implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User checkUser(String login, String password) {
        return userDao.checkPassword(login, password);
    }

    private UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}