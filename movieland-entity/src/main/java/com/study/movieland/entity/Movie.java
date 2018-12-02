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

    public String getNameRussian() {
        return nameRussian;
    }

    public String getNameNative() {
        return nameNative;
    }

    public String getDescription() {
        return description;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public double getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public String getPicturePath() {
        return picturePath;
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

    @Override
    public int hashCode() {
        return Objects.hash(id, nameRussian, nameNative, yearOfRelease, description, rating, price, picturePath);
    }

    public static class Builder {

        private Movie movie;

        public Builder() {
            movie = new Movie();
        }

        public Builder id(int value) {
            movie.id = value;
            return this;
        }

        public Builder nameRussian(String value) {
            movie.nameRussian = value;
            return this;
        }

        public Builder nameNative(String value) {
            movie.nameNative = value;
            return this;
        }

        public Builder yearOfRelease(int value) {
            movie.yearOfRelease = value;
            return this;
        }

        public Builder description(String value) {
            movie.description = value;
            return this;
        }

        public Builder rating(double value) {
            movie.rating = value;
            return this;
        }

        public Builder price(double value) {
            movie.price = value;
            return this;
        }

        public Builder picturePath(String value) {
            movie.picturePath = value;
            return this;
        }

        public Movie build() {
            return movie;
        }
    }
}
