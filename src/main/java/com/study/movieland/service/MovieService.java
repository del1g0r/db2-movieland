package com.study.movieland.service;

import com.study.movieland.entity.Movie;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface MovieService {

    List<Movie> getAll();

    List<Movie> getSortedAll(Sort sort);

    List<Movie> getRandom(int count);

    List<Movie> getByGenre(int genreId);
}
