package com.study.movieland.dao;

import com.study.movieland.data.RequestParams;
import com.study.movieland.entity.Movie;

import java.util.List;

public interface MovieDao {

    List<Movie> getAll(RequestParams requestParams);

    List<Movie> getRandom(int count);

    List<Movie> getByGenre(int genreId);
}
