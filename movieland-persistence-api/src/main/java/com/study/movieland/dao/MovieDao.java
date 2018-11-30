package com.study.movieland.dao;

import com.study.movieland.data.RequestParams;
import com.study.movieland.dto.MovieDto;
import com.study.movieland.entity.Movie;


import java.util.Collection;

public interface MovieDao {

    MovieDto get(int id);

    Collection<Movie> getAll(RequestParams requestParams);

    Collection<Movie> getRandom(int count);

    Collection<Movie> getByGenre(int genreId, RequestParams requestParams);
}
