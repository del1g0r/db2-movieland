package com.study.movieland.service.impl;

import com.study.movieland.dao.ReviewDao;
import com.study.movieland.entity.Review;
import com.study.movieland.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DefaultReviewService implements ReviewService {

    private ReviewDao reviewDao;
    private UserService userService;

    @Override
    public Collection<Review> getByMovie(int movieId) {
        Collection<Review> reviews = reviewDao.getByMovie(movieId);
        for (Review review : reviews) {
            review.setUser(userService.enrich(review.getUser()));
        }
        return reviews;
    }

    @Override
    public void create(int movieId, Review review) {
        reviewDao.create(movieId, review);
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