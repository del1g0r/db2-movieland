package com.study.movieland.service;

import com.study.movieland.entity.Session;

public interface SecurityService {

    void setSessionAge(int age);

    int getSessionAge();

    Session login(String login, String password);

    void logout(String token);

    Session getSession(String token);
}
