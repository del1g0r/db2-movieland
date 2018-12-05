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
import static org.junit.Assert.assertEquals;

public class DefaultReviewServiceTest {

    @Test
    public void testGetByMovie() {
        DefaultReviewService reviewService = new DefaultReviewService();
        reviewService.setReviewDao(new StubRwviewDao());
        reviewService.setUserService(new StubUserService());

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

    class StubRwviewDao implements ReviewDao {

        @Override
        public Collection<Review> getByMovie(int movieId) {
            return Arrays.asList(
                    new Review.Builder()
                            .id(1)
                            .text("Some review text 1")
                            .user(new User.Builder().id(1).nickName("Some User 1").build())
                            .build(),
                    new Review.Builder()
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
        public User checkUser(String login, String password) {
            return null;
        }

        @Override
        public Collection<User> getAll() {
            return null;
        }
    }
}