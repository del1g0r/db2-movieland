package com.study.movieland.service;

import com.study.movieland.entity.User;

import java.util.Collection;

public interface UserService {

    User get(int id);

    Collection<User> getAll();
}
