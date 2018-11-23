package com.study.movieland.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Movie {

    private int id;
    private String nameRussian;
    private String nameNative;
    private int yearOfRelease;
    @JsonIgnore
    private String description;
    private double rating;
    private double price;
    private String picturePath;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameRussian() {
        return nameRussian;
    }

    public void setNameRussian(String nameRussian) {
        this.nameRussian = nameRussian;
    }

    public String getNameNative() {
        return nameNative;
    }

    public void setNameNative(String nameNative) {
        this.nameNative = nameNative;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", nameRussian='" + nameRussian + '\'' +
                ", nameNative='" + nameNative + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", rating=" + rating +
                ", picturePath='" + picturePath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return id == movie.id &&
                yearOfRelease == movie.yearOfRelease &&
                Double.compare(movie.rating, rating) == 0 &&
                Double.compare(movie.price, price) == 0 &&
                Objects.equals(nameRussian, movie.nameRussian) &&
                Objects.equals(nameNative, movie.nameNative) &&
                Objects.equals(description, movie.description) &&
                Objects.equals(picturePath, movie.picturePath);
    }

    public static class Builder implements javafx.util.Builder {

        private Movie movie;

        public Builder() {
            movie = new Movie();
        }

        public Builder id(int value) {
            movie.setId(value);
            return this;
        }

        public Builder nameRussian(String value) {
            movie.setNameRussian(value);
            return this;
        }

        public Builder nameNative(String value) {
            movie.setNameNative(value);
            return this;
        }

        public Builder yearOfRelease(int value) {
            movie.setYearOfRelease(value);
            return this;
        }

        public Builder description(String value) {
            movie.setDescription(value);
            return this;
        }

        public Builder rating(double value) {
            movie.setRating(value);
            return this;
        }

        public Builder price(double value) {
            movie.setPrice(value);
            return this;
        }

        public Builder picturePath(String value) {
            movie.setPicturePath(value);
            return this;
        }

        @Override
        public Movie build() {
            return movie;
        }
    }
}
