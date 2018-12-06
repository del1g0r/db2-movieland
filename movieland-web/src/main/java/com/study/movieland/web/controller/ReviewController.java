package com.study.movieland.web.controller;

import com.study.movieland.dto.ReviewRequestDto;
import com.study.movieland.entity.Session;
import com.study.movieland.service.ReviewService;
import com.study.movieland.web.exception.NotAthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "review")
public class ReviewController {

    private ReviewService reviewService;

    @PostMapping
    public void post(@RequestBody ReviewRequestDto reviewRequest, @RequestAttribute(required = false) Session session) {
        if (session == null) {
            throw new NotAthorizedException();
        }
        reviewService.post(reviewRequest.getMovieId(), session.getUser().getId(), reviewRequest.getText());
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
}