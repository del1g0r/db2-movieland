package com.study.movieland.entity;

import java.util.Objects;

public class User {

    private int id;
    private String nickName;
    private String eMail;

    public int getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEMail() {
        return eMail;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", eMail='" + eMail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(nickName, user.nickName) &&
                Objects.equals(eMail, user.eMail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickName, eMail);
    }

    public static class Builder {

        private User user;

        public Builder() {
            user = new User();
        }

        public Builder(User user) {
            this.user = user;
        }

        public Builder id(int value) {
            user.id = value;
            return this;
        }

        public Builder nickName(String value) {
            user.nickName = value;
            return this;
        }

        public Builder eMail(String value) {
            user.eMail = value;
            return this;
        }

        public User build() {
            return user;
        }
    }
}
