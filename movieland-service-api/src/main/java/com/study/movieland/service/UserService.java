package com.study.movieland.service;

import com.study.movieland.entity.User;

import java.util.Collection;

public interface UserService {

    User get(int id);

    User checkUser(String login, String password);

    Collection<User> getAll();

    default User enrich(User user) {
        return get(user.getId());
    }
}
