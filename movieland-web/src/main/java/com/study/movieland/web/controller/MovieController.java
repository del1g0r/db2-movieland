package com.study.movieland.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.study.movieland.Views;
import com.study.movieland.data.RequestParams;
import com.study.movieland.entity.Movie;
import com.study.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    public Movie get(@PathVariable int movieId) {
        return movieService.get(movieId);
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
}