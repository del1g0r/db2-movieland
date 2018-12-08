package com.study.movieland.service.impl;

import com.study.movieland.dao.ReviewDao;
import com.study.movieland.entity.Review;
import com.study.movieland.entity.User;
import com.study.movieland.service.UserService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

public class DefaultReviewServiceTest {

    @Test
    public void testGetByMovie() {
        DefaultReviewService reviewService = new DefaultReviewService();
        ReviewDao reviewDao = mock(ReviewDao.class);
        when(reviewDao.getByMovie(1)).thenReturn(
                Arrays.asList(
                        new Review.Builder()
                                .id(1)
                                .text("Some review text 1")
                                .user(new User.Builder().id(1).nickName("Some User 1").build())
                                .build(),
                        new Review.Builder()
                                .id(2)
                                .text("Some review text 2")
                                .user(new User.Builder().id(1).nickName("Some User 2").build())
                                .build())
        );
        UserService userService = mock(DefaultUserService.class);
        when(userService.enrich(any(User.class))).thenCallRealMethod();
        when(userService.get(1)).thenReturn(
                new User.Builder().id(1).nickName("Some User 1").build()
        );
        reviewService.setReviewDao(reviewDao);
        reviewService.setUserService(userService);

        Collection<Review> expectedReviews = Arrays.asList(
                new Review.Builder()
                        .id(1)
                        .text("Some review text 1")
                        .user(new User.Builder().id(1).nickName("Some User 1").build())
                        .build(),
                new Review.Builder()
                        .id(2)
                        .text("Some review text 2")
                        .user(new User.Builder().id(1).nickName("Some User 1").build())
                        .build()
        );
        Collection<Review> actualReviews = reviewService.getByMovie(1);

        Assert.assertThat(actualReviews, is(expectedReviews));
    }
}