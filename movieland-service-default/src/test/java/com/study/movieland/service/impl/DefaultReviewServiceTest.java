package com.study.movieland.service.impl;

import com.study.movieland.dao.ReviewDao;
import com.study.movieland.dto.ReviewDto;
import com.study.movieland.entity.User;
import com.study.movieland.service.UserService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

public class DefaultReviewServiceTest {

    @Test
    public void testGet() {
        DefaultReviewService reviewService = new DefaultReviewService();
        reviewService.setReviewDao(new StubRwviewDao());
        reviewService.setUserService(new StubUserService());

        ReviewDto expectedReview = new ReviewDto.Builder()
                .id(1)
                .text("Some review text")
                .user(new User.Builder().id(1).nickName("Some User 1").build())
                .build();
        ReviewDto actualReview = reviewService.get(1);

        assertEquals(expectedReview.getId(), actualReview.getId());
        assertEquals(expectedReview.getText(), actualReview.getText());
        assertEquals(expectedReview.getUser().getId(), actualReview.getUser().getId());
        assertEquals(expectedReview.getUser().getNickName(), actualReview.getUser().getNickName());
    }

    @Test
    public void testGetByMovie() {
        DefaultReviewService reviewService = new DefaultReviewService();
        reviewService.setReviewDao(new StubRwviewDao());
        reviewService.setUserService(new StubUserService());

        Collection<ReviewDto> expectedReviews = Arrays.asList(
                new ReviewDto.Builder()
                        .id(1)
                        .text("Some review text 1")
                        .user(new User.Builder().id(1).nickName("Some User 1").build())
                        .build(),
                new ReviewDto.Builder()
                        .id(2)
                        .text("Some review text 2")
                        .user(new User.Builder().id(1).nickName("Some User 1").build())
                        .build()
        );
        Collection<ReviewDto> actualReviews = reviewService.getByMovie(1);

        Assert.assertThat(actualReviews, is(expectedReviews));
    }

    class StubRwviewDao implements ReviewDao {
        @Override
        public ReviewDto get(int id) {
            return new ReviewDto.Builder()
                    .id(1)
                    .text("Some review text")
                    .user(new User.Builder().id(1).build())
                    .build();
        }

        @Override
        public Collection<ReviewDto> getByMovie(int movieId) {
            return Arrays.asList(
                    new ReviewDto.Builder()
                            .id(1)
                            .text("Some review text 1")
                            .user(new User.Builder().id(1).nickName("Some User 1").build())
                            .build(),
                    new ReviewDto.Builder()
                            .id(2)
                            .text("Some review text 2")
                            .user(new User.Builder().id(1).nickName("Some User 2").build())
                            .build()
            );
        }
    }

    class StubUserService implements UserService {

        @Override
        public User get(int id) {
            return new User.Builder().id(1).nickName("Some User 1").build();
        }

        @Override
        public Collection<User> getAll() {
            return null;
        }
    }
}