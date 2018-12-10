package com.study.movieland.web.controller;

import com.study.movieland.dto.ReviewRequestDto;
import com.study.movieland.entity.*;
import com.study.movieland.service.ReviewService;
import com.study.movieland.web.UserHolder;
import com.study.movieland.web.annotation.ProtectedBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "review")
public class ReviewController {

    private ReviewService reviewService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ProtectedBy(role = {Role.USER, Role.ADMIN})
    public void post(@RequestBody ReviewRequestDto reviewRequest, @RequestAttribute(required = false) Session session) {
        reviewService.create(reviewRequest.getMovieId(),
                new Review.Builder()
                        .user(UserHolder.getCurrentUser())
                        .text(reviewRequest.getText())
                        .build()
        );
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
}