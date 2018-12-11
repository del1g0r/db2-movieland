package com.study.movieland.service;

import com.study.movieland.entity.Movie;
import com.study.movieland.entity.Review;

import java.util.Collection;

public interface ReviewService {

    Collection<Review> getByMovie(int movieId);

    void create(int movieId, Review review);
}
