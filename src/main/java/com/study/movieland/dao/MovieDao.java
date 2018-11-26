package com.study.movieland.dao;

import com.study.movieland.entity.Movie;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface MovieDao {

    List<Movie> getAll();

    List<Movie> getSortedAll(Sort sort);

    List<Movie> getRandom(int count);

    List<Movie> getByGenre(int genreId);
}
