package com.study.movieland.service;

import com.study.movieland.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User checkUser(String login, String password);
}
