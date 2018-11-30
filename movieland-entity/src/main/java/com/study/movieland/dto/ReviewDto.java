package com.study.movieland.dto;

import com.study.movieland.entity.User;

import java.util.Objects;

public class ReviewDto {

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
        return "ReviewDto{" +
                "id=" + id +
                ", user=" + user +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewDto)) return false;
        ReviewDto reviewDto = (ReviewDto) o;
        return id == reviewDto.id &&
                Objects.equals(user, reviewDto.user) &&
                Objects.equals(text, reviewDto.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, text);
    }

    public static class Builder {

        private ReviewDto review;

        public Builder() {
            review = new ReviewDto();
        }

        public Builder(ReviewDto review) {
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

        public ReviewDto build() {
            return review;
        }
    }
}
