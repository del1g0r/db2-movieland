package com.study.movieland.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.movieland.Views;
import com.study.movieland.data.RequestParams;
import com.study.movieland.dto.MovieRequestDto;
import com.study.movieland.entity.Country;
import com.study.movieland.entity.Genre;
import com.study.movieland.entity.Movie;
import com.study.movieland.entity.Role;
import com.study.movieland.service.MovieService;
import com.study.movieland.web.annotation.ProtectedBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping(path = "movie")
public class MovieController {

    private int randomCount;
    private MovieService movieService;

    @JsonView(Views.Lite.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Collection<Movie> getAll(RequestParams requestParams) {
        return movieService.getAll(requestParams);
    }

    @GetMapping(path = {"{movieId}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Movie get(@PathVariable int movieId, @RequestParam(name = "currency", defaultValue = "UAH") String currencyCode) {
        return movieService.get(movieId, currencyCode);
    }

    @ProtectedBy(role = {Role.ADMIN})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void create(@RequestBody MovieRequestDto movieRequest) {
        movieService.create(new Movie.Builder()
                .nameNative(movieRequest.getNameNative())
                .nameRussian(movieRequest.getNameRussian())
                .price(movieRequest.getPrice())
                .yearOfRelease(movieRequest.getYearOfRelease())
                .description(movieRequest.getDescription())
                .picturePath(movieRequest.getPicturePath())
                .countries(getCountriesFromArray(movieRequest.getCountries()))
                .genres(getGenresFromArray(movieRequest.getGenres()))
                .build());
    }
    
    @ProtectedBy(role = {Role.ADMIN})
    @PutMapping(path = {"/{movieId}"}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void update(@RequestBody MovieRequestDto movieRequest, @PathVariable int movieId) {
        movieService.update(new Movie.Builder()
                .id(movieId)
                .nameNative(movieRequest.getNameNative())
                .nameRussian(movieRequest.getNameRussian())
                .picturePath(movieRequest.getPicturePath())
                .countries(getCountriesFromArray(movieRequest.getCountries()))
                .genres(getGenresFromArray(movieRequest.getGenres()))
                .build());
    }

    @JsonView(Views.Lite.class)
    @GetMapping(path = {"random"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Collection<Movie> getRandom() {
        return movieService.getRandom(randomCount);
    }

    @JsonView(Views.Lite.class)
    @GetMapping(path = {"genre/{genreId}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Collection<Movie> getByGenre(@PathVariable int genreId, RequestParams requestParams) {
        return movieService.getByGenre(genreId, requestParams);
    }

    @Value("${movie.randomCount:3}")
    public void setRandomCount(int randomCount) {
        this.randomCount = randomCount;
    }

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    private Collection<Country> getCountriesFromArray(int[] Ids) {
        Collection<Country> ountries = new ArrayList<>();
        for (int countryId : Ids) {
            ountries.add(new Country.Builder().id(countryId).build());
        }
        return ountries;
    }

    private Collection<Genre> getGenresFromArray(int[] Ids) {
        Collection<Genre> genres = new ArrayList<>();
        for (int genreId : Ids) {
            genres.add(new Genre.Builder().id(genreId).build());
        }
        return genres;
    }
}