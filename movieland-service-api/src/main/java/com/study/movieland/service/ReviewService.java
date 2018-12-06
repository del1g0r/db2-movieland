package com.study.movieland.service;

import com.study.movieland.entity.Review;

import java.util.Collection;

public interface ReviewService {

    Collection<Review> getByMovie(int movieId);

    void post(int movieId, int userId, String text);
}
