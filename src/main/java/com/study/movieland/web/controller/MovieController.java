package com.study.movieland.web.controller;

import com.study.movieland.entity.Movie;
import com.study.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "movie")
public class MovieController {

    private int randomCount;
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAll() {
        return movieService.getAll();
    }

    @GetMapping(path = {"random"})
    public List<Movie> getRandom() {
        return movieService.getRandom(randomCount);
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