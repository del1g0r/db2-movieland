package com.study.movieland.web;

import com.study.movieland.entity.User;

public class UserHolder {

    private static final ThreadLocal<User> HOLDER = new ThreadLocal<>();

    public static User getCurrentUser() {
        return HOLDER.get();
    }

    public static void setCurrentUser(User user) {
        HOLDER.set(user);
    }

    public static void clear() {
        HOLDER.remove();
    }

}
