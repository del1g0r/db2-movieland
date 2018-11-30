package com.study.movieland.service.impl;

import com.study.movieland.dao.ReviewDao;
import com.study.movieland.dto.ReviewDto;
import com.study.movieland.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DefaultReviewService implements ReviewService {

    private ReviewDao reviewDao;
    private UserService userService;

    @Override
    public ReviewDto get(int id) {
        ReviewDto review = reviewDao.get(id);
        return new ReviewDto.Builder(review)
                .user(userService.get(review.getUser().getId()))
                .build();
    }

    @Override
    public Collection<ReviewDto> getByMovie(int movieId) {
        Collection<ReviewDto> reviews = reviewDao.getByMovie(movieId);
        for (ReviewDto review : reviews) {
            new ReviewDto.Builder(review)
                    .user(userService.get(review.getUser().getId()))
                    .build();
        }
        return reviews;
    }

    @Autowired
    public void setReviewDao(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}