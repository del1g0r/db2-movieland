package com.study.movieland.dao;

import com.study.movieland.entity.User;

import java.util.ArrayList;
import java.util.Collection;

public interface UserDao {

    User get(int id);

    Collection<User> getAll();

    User checkPassword(String name, String password);

    default User enrich(User user) {
        return get(user.getId());
    }

    default Collection<User> enrich(Collection<User> users) {
        Collection<User> enrichedUsers = new ArrayList<>();
        for (User user : users) {
            enrichedUsers.add(get(user.getId()));
        }
        return enrichedUsers;
    }
}
