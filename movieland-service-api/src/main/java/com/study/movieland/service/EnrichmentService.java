package com.study.movieland.service;

import com.study.movieland.entity.Movie;

public interface EnrichmentService {

    Movie enrichMovie(Movie movie, String currencyCode);
}
