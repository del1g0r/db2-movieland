package com.study.movieland.dao;

import com.study.movieland.entity.Review;

import java.util.Collection;

public interface ReviewDao {

    Collection<Review> getByMovie(int movieId);

    void create(int movieId, Review review);
}
