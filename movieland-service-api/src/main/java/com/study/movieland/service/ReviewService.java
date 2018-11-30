package com.study.movieland.service;

import com.study.movieland.dto.ReviewDto;

import java.util.Collection;

public interface ReviewService {

    ReviewDto get(int id);

    Collection<ReviewDto> getByMovie(int movieId);
}
