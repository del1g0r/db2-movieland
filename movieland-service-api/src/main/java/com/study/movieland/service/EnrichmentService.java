package com.study.movieland.service;

import com.study.movieland.data.MovieEnricherType;
import com.study.movieland.entity.Movie;

import java.util.Set;

public interface EnrichmentService {

    Movie enrichMovie(Movie movie, Set<MovieEnricherType> enricherTypes);

    default Movie enrichMovie(Movie movie) {
        return enrichMovie(movie, MovieEnricherType.ALL);
    }
}
