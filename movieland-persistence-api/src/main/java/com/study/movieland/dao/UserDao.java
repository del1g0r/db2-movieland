package com.study.movieland.dao;

import com.study.movieland.entity.User;

import java.util.Collection;

public interface UserDao {

    User get(int id);

    Collection<User> getAll();
}
