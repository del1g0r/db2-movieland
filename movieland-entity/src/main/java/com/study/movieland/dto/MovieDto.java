package com.study.movieland.dto;

import com.study.movieland.entity.Country;
import com.study.movieland.entity.Genre;

import java.util.Collection;
import java.util.Objects;

public class MovieDto {

    private int id;
    private String nameRussian;
    private String nameNative;
    private int yearOfRelease;
    private String description;
    private double rating;
    private double price;
    private String picturePath;
    private Collection<Country> countries;
    private Collection<Genre> genres;
    private Collection<ReviewDto> reviews;

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

    public Collection<ReviewDto> getReviews() {
        return reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieDto)) return false;
        MovieDto movieDto = (MovieDto) o;
        return id == movieDto.id &&
                yearOfRelease == movieDto.yearOfRelease &&
                Double.compare(movieDto.rating, rating) == 0 &&
                Double.compare(movieDto.price, price) == 0 &&
                Objects.equals(nameRussian, movieDto.nameRussian) &&
                Objects.equals(nameNative, movieDto.nameNative) &&
                Objects.equals(description, movieDto.description) &&
                Objects.equals(picturePath, movieDto.picturePath) &&
                Objects.equals(countries, movieDto.countries) &&
                Objects.equals(genres, movieDto.genres) &&
                Objects.equals(reviews, movieDto.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameRussian, nameNative, yearOfRelease, description, rating, price, picturePath, countries, genres, reviews);
    }

    @Override
    public String toString() {
        return "MovieDto{" +
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

        private MovieDto movie;

        public Builder() {
            movie = new MovieDto();
        }

        public Builder(MovieDto movie) {
            this.movie = movie;
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

        public Builder countries(Collection<Country> value) {
            movie.countries = value;
            return this;
        }

        public Builder genres(Collection<Genre> value) {
            movie.genres = value;
            return this;
        }

        public Builder reviews(Collection<ReviewDto> value) {
            movie.reviews = value;
            return this;
        }

        public MovieDto build() {
            return movie;
        }
    }
}
