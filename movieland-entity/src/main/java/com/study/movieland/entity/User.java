package com.study.movieland.entity;

import java.util.Objects;

public class User {

    private int id;
    private String nickName;

    public int getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User country = (User) o;
        return id == country.id &&
                Objects.equals(nickName, country.nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickName);
    }

    public static class Builder {

        private User user;

        public Builder() {
            user = new User();
        }

        public Builder(User user) {
            this.user = user;
        }

        public User.Builder id(int value) {
            user.id = value;
            return this;
        }

        public User.Builder nickName(String value) {
            user.nickName = value;
            return this;
        }

        public User build() {
            return user;
        }
    }
}
