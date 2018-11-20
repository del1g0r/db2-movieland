package com.study.movieland.dao;

import com.study.movieland.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getAll();

    User getByName(String name);

    User checkPassword(String name, String password);
}
