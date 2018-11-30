package com.study.movieland.dao;

import com.study.movieland.dto.ReviewDto;

import java.util.Collection;

public interface ReviewDao {

    ReviewDto get(int id);

    Collection<ReviewDto> getByMovie(int movieId);
}
