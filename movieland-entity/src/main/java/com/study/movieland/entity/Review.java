package com.study.movieland.entity;

import java.util.Objects;

public class Review {

    private int id;
    private User user;
    private String text;

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user=" + user +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review reviewDto = (Review) o;
        return id == reviewDto.id &&
                Objects.equals(user, reviewDto.user) &&
                Objects.equals(text, reviewDto.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, text);
    }

    public static class Builder {

        private Review review;

        public Builder() {
            review = new Review();
        }

        public Builder(Review review) {
            this.review = review;
        }

        public Builder id(int value) {
            review.id = value;
            return this;
        }

        public Builder user(User value) {
            review.user = value;
            return this;
        }

        public Builder text(String value) {
            review.text = value;
            return this;
        }

        public Review build() {
            return review;
        }
    }
}