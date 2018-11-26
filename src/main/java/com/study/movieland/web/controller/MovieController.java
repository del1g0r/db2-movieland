package com.study.movieland.web.controller;

import com.study.movieland.entity.Movie;
import com.study.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "movie")
public class MovieController {

    private int randomCount;
    private MovieService movieService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Movie> getAll(@RequestParam(name = "rating", required = false) String sortRatingDirection,
                              @RequestParam(name = "price", required = false) String sortPriceDirection) {
        if (sortRatingDirection != null) {
            Sort.Direction sortDirection = sortRatingDirection.isEmpty() ? Sort.DEFAULT_DIRECTION : Sort.Direction.fromString(sortRatingDirection);
            return movieService.getSortedAll(new Sort(sortDirection, "rating"));
        }
        if (sortPriceDirection != null) {
            Sort.Direction sortDirection = sortPriceDirection.isEmpty() ? Sort.DEFAULT_DIRECTION : Sort.Direction.fromString(sortPriceDirection);
            return movieService.getSortedAll(new Sort(sortDirection, "price"));
        }
        return movieService.getAll();
    }

    @GetMapping(path = {"random"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Movie> getRandom() {
        return movieService.getRandom(randomCount);
    }

    @GetMapping(path = {"genre/{genreId}"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Movie> getByGenre(@PathVariable int genreId) {
        return movieService.getByGenre(genreId);
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