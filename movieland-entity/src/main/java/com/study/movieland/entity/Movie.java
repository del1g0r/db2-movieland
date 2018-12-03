package com.study.movieland.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.movieland.Views;

import java.util.Collection;
import java.util.Objects;

public class Movie {

    @JsonView(Views.Lite.class)
    private int id;
    @JsonView(Views.Lite.class)
    private String nameRussian;
    @JsonView(Views.Lite.class)
    private String nameNative;
    @JsonView(Views.Lite.class)
    private int yearOfRelease;
    private String description;
    @JsonView(Views.Lite.class)
    private double rating;
    @JsonView(Views.Lite.class)
    private double price;
    @JsonView(Views.Lite.class)
    private String picturePath;
    private Collection<Country> countries;
    private Collection<Genre> genres;
    private Collection<Review> reviews;

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

    public Collection<Country> getCountries() {
        return countries;
    }

    public Collection<Genre> getGenres() {
        return genres;
    }

    public Collection<Review> getReviews() {
        return reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie Movie = (Movie) o;
        return id == Movie.id &&
                yearOfRelease == Movie.yearOfRelease &&
                Double.compare(Movie.rating, rating) == 0 &&
                Double.compare(Movie.price, price) == 0 &&
                Objects.equals(nameRussian, Movie.nameRussian) &&
                Objects.equals(nameNative, Movie.nameNative) &&
                Objects.equals(description, Movie.description) &&
                Objects.equals(picturePath, Movie.picturePath) &&
                Objects.equals(countries, Movie.countries) &&
                Objects.equals(genres, Movie.genres) &&
                Objects.equals(reviews, Movie.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameRussian, nameNative, yearOfRelease, description, rating, price, picturePath, countries, genres, reviews);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", nameRussian='" + nameRussian + '\'' +
                ", nameNative='" + nameNative + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                ", picturePath='" + picturePath + '\'' +
                ", countries=" + countries +
                ", genres=" + genres +
                ", reviews=" + reviews +
                '}';
    }

    public static class Builder {

        private Movie movie;

        public Builder() {
            movie = new Movie();
        }

        public Builder(Movie movie) {
            this.movie = movie;
        }

        public Movie.Builder id(int value) {
            movie.id = value;
            return this;
        }

        public Movie.Builder nameRussian(String value) {
            movie.nameRussian = value;
            return this;
        }

        public Movie.Builder nameNative(String value) {
            movie.nameNative = value;
            return this;
        }

        public Movie.Builder yearOfRelease(int value) {
            movie.yearOfRelease = value;
            return this;
        }

        public Movie.Builder description(String value) {
            movie.description = value;
            return this;
        }

        public Movie.Builder rating(double value) {
            movie.rating = value;
            return this;
        }

        public Movie.Builder price(double value) {
            movie.price = value;
            return this;
        }

        public Movie.Builder picturePath(String value) {
            movie.picturePath = value;
            return this;
        }

        public Movie.Builder countries(Collection<Country> value) {
            movie.countries = value;
            return this;
        }

        public Movie.Builder genres(Collection<Genre> value) {
            movie.genres = value;
            return this;
        }

        public Movie.Builder reviews(Collection<Review> value) {
            movie.reviews = value;
            return this;
        }

        public Movie build() {
            return movie;
        }
    }
}