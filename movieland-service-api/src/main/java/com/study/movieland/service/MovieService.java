package com.study.movieland.service;

import com.study.movieland.data.RequestParams;
import com.study.movieland.entity.Movie;

import java.util.Collection;

public interface MovieService {

    Movie get(int id);

    Movie get(int id, String currencyCode);

    Movie create(Movie movie);

    Movie update(Movie movie);

    Collection<Movie> getAll(RequestParams requestParams);

    Collection<Movie> getRandom(int count);

    Collection<Movie> getByGenre(int genreId, RequestParams requestParams);
}
