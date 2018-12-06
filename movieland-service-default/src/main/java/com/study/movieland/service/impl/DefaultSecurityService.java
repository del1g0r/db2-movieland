package com.study.movieland.service.impl;

import com.study.movieland.entity.Session;
import com.study.movieland.entity.User;
import com.study.movieland.service.SecurityService;
import com.study.movieland.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultSecurityService implements SecurityService {

    private Map<String, Session> sessions = new ConcurrentHashMap<>();

    private UserService userService;
    private int sessionAge;

    private synchronized Session getOrCreateSession(User user) {
        for (Session session : sessions.values()) {
            if (user.getId() == session.getUser().getId()) {
                session.setExpireTime(LocalDateTime.now().plusSeconds(sessionAge));
                return session;
            }
        }
        Session session = new Session();
        session.setUser(user);
        session.setToken(UUID.randomUUID().toString());
        session.setExpireTime(LocalDateTime.now().plusSeconds(sessionAge));
        sessions.put(session.getToken(), session);
        return session;
    }

    @Scheduled(fixedDelayString = "${scheduled.securityFixedDelay:600000}", initialDelayString = "${scheduled.securityFixedDelay:600000}")
    public void cleanExpiredSessions() {
        sessions.values().removeIf((Session session) ->
                session.getExpireTime().isBefore(LocalDateTime.now())
        );
    }

    @Override
    public Session login(String login, String password) {
        User user = userService.checkUser(login, password);
        if (user != null) {
            return getOrCreateSession(user);
        }
        return null;
    }

    @Override
    public void logout(String token) {
        sessions.remove(token);
    }

    @Override
    public Session getSession(String token) {
        Session session;
        if (token != null && (session = sessions.get(token)) != null) {
            if (session.getExpireTime().isAfter(LocalDateTime.now())) {
                return session;
            }
            sessions.remove(token);
        }
        return null;
    }

    @Override
    @Value("${web.sessionAge:7200}")
    public void setSessionAge(int age) {
        sessionAge = age;
    }

    @Override
    public int getSessionAge() {
        return sessionAge;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}