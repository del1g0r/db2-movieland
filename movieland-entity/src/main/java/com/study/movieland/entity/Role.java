package com.study.movieland.entity;

public enum Role {

    GUEST,
    USER,
    ADMIN;

    static Role DEFAULT = GUEST;

    public static Role get(String value) {
        return Role.valueOf(value.toUpperCase());
    }
}
