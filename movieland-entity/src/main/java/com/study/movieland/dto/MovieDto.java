package com.study.movieland.dto;

import com.study.movieland.entity.Country;
import com.study.movieland.entity.Genre;

import java.util.List;

public class MovieDto {

    private int id;
    private String nameRussian;
    private String nameNative;
    private int yearOfRelease;
    private String description;
    private double rating;
    private double price;
    private String picturePath;
    private List<Country> countries;
    private List<Genre> genres;
    private List<ReviewDto> reviews;

    public int getId() {
        return id;
    }

    public String getNameRussian() {
        return nameRussian;
    }

    public String getNameNative() {
        return nameNative;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public String getDescription() {
        return description;
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

    public List<Country> getCountries() {
        return countries;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<ReviewDto> getReviews() {
        return reviews;
    }

    public static class Builder {

        private MovieDto movie;

        public Builder() {
            movie = new MovieDto();
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

        public Builder countries(List<Country> value) {
            movie.countries = value;
            return this;
        }

        public Builder genres(List<Genre> value) {
            movie.genres = value;
            return this;
        }

        public Builder reviews(List<ReviewDto> value) {
            movie.reviews = value;
            return this;
        }

        public MovieDto build() {
            return movie;
        }
    }
}
