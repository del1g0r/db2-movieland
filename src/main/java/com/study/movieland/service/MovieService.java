package com.study.movieland.service;

import com.study.movieland.data.RequestParams;
import com.study.movieland.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAll(RequestParams requestParams);

    List<Movie> getRandom(int count);

    List<Movie> getByGenre(int genreId);
}
