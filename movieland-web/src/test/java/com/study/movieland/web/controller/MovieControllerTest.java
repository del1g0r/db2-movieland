package com.study.movieland.web.controller;

import com.study.movieland.data.RequestParams;
import com.study.movieland.dto.MovieDto;
import com.study.movieland.dto.ReviewDto;
import com.study.movieland.entity.Country;
import com.study.movieland.entity.Genre;
import com.study.movieland.entity.Movie;
import com.study.movieland.entity.User;
import com.study.movieland.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MovieControllerTest.TestContext.class)
@WebAppConfiguration
public class MovieControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private MovieService movieService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testGet() throws Exception {
        MovieDto movie = new MovieDto.Builder()
                .id(1)
                .nameNative("Movie")
                .nameRussian("Фильм")
                .yearOfRelease(2000)
                .rating(100)
                .price(101)
                .picturePath("http://localhost/1.jpg")
                .description("Some description")
                .genres(Arrays.asList(
                        new Genre.Builder().id(1).name("Genre 1").build(),
                        new Genre.Builder().id(2).name("Genre 2").build()
                ))
                .countries(Arrays.asList(
                        new Country.Builder().id(1).name("Country 1").build(),
                        new Country.Builder().id(2).name("Country 2").build()
                ))
                .reviews(Arrays.asList(
                        new ReviewDto.Builder()
                                .id(1)
                                .text("Some review text 1")
                                .user(new User.Builder().id(1).nickName("Some User 1").build())
                                .build(),
                        new ReviewDto.Builder()
                                .id(2)
                                .text("Some review text 2")
                                .user(new User.Builder().id(2).nickName("Some User 2").build())
                                .build()))
                .build();

        when(movieService.get(1, "UAH")).thenReturn(movie);

        mockMvc.perform(get("/movie/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("nameNative", is("Movie")))
                .andExpect(jsonPath("nameRussian", is("Фильм")))
                .andExpect(jsonPath("yearOfRelease", is(2000)))
                .andExpect(jsonPath("rating", is(100d)))
                .andExpect(jsonPath("price", is(101d)))
                .andExpect(jsonPath("picturePath", is("http://localhost/1.jpg")))
                .andExpect(jsonPath("description", is("Some description")))
                .andExpect(jsonPath("genres[0].id", is(1)))
                .andExpect(jsonPath("genres[0].name", is("Genre 1")))
                .andExpect(jsonPath("genres[1].id", is(2)))
                .andExpect(jsonPath("genres[1].name", is("Genre 2")))
                .andExpect(jsonPath("countries[0].id", is(1)))
                .andExpect(jsonPath("countries[0].name", is("Country 1")))
                .andExpect(jsonPath("countries[1].id", is(2)))
                .andExpect(jsonPath("countries[1].name", is("Country 2")))
                .andExpect(jsonPath("reviews[0].id", is(1)))
                .andExpect(jsonPath("reviews[0].text", is("Some review text 1")))
                .andExpect(jsonPath("reviews[0].user.id", is(1)))
                .andExpect(jsonPath("reviews[0].user.nickName", is("Some User 1")))
                .andExpect(jsonPath("reviews[1].id", is(2)))
                .andExpect(jsonPath("reviews[1].text", is("Some review text 2")))
                .andExpect(jsonPath("reviews[1].user.id", is(2)))
                .andExpect(jsonPath("reviews[1].user.nickName", is("Some User 2")));
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Movie> movies = Arrays.asList(
                new Movie.Builder()
                        .id(1)
                        .nameNative("Movie 1")
                        .nameRussian("Фильм 1")
                        .yearOfRelease(2000)
                        .rating(100)
                        .price(101)
                        .picturePath("http://localhost/1.jpg")
                        .build(),
                new Movie.Builder()
                        .id(2)
                        .nameNative("Movie 2")
                        .nameRussian("Фильм 2")
                        .yearOfRelease(2001)
                        .rating(200)
                        .price(201)
                        .picturePath("http://localhost/2.jpg")
                        .build()
        );
        RequestParams requestParams = new RequestParams.Builder().build();

        when(movieService.getAll(requestParams)).thenReturn(movies);

        mockMvc.perform(get("/movie"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nameNative", is("Movie 1")))
                .andExpect(jsonPath("$[0].nameRussian", is("Фильм 1")))
                .andExpect(jsonPath("$[0].yearOfRelease", is(2000)))
                .andExpect(jsonPath("$[0].rating", is(100d)))
                .andExpect(jsonPath("$[0].price", is(101d)))
                .andExpect(jsonPath("$[0].picturePath", is("http://localhost/1.jpg")))
                .andExpect(jsonPath("$[0].*", not(containsInAnyOrder("description"))))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nameNative", is("Movie 2")))
                .andExpect(jsonPath("$[1].nameRussian", is("Фильм 2")))
                .andExpect(jsonPath("$[1].yearOfRelease", is(2001)))
                .andExpect(jsonPath("$[1].rating", is(200d)))
                .andExpect(jsonPath("$[1].price", is(201d)))
                .andExpect(jsonPath("$[1].picturePath", is("http://localhost/2.jpg")))
                .andExpect(jsonPath("$[1].*", not(containsInAnyOrder("description"))));
    }

    @Test
    public void testGetRandom() throws Exception {
        Collection<Movie> movies = Arrays.asList(
                new Movie.Builder()
                        .id(1)
                        .nameNative("Random movie 1")
                        .nameRussian("Фильм 1")
                        .yearOfRelease(2000)
                        .rating(100)
                        .price(101)
                        .picturePath("http://localhost/1.jpg")
                        .build(),
                new Movie.Builder()
                        .id(2)
                        .nameNative("Random movie 2")
                        .nameRussian("Фильм 2")
                        .yearOfRelease(2001)
                        .rating(200)
                        .price(201)
                        .picturePath("http://localhost/2.jpg")
                        .build()
        );
        when(movieService.getRandom(3)).thenReturn(movies);

        mockMvc.perform(get("/movie/random"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nameNative", is("Random movie 1")))
                .andExpect(jsonPath("$[0].nameRussian", is("Фильм 1")))
                .andExpect(jsonPath("$[0].yearOfRelease", is(2000)))
                .andExpect(jsonPath("$[0].rating", is(100d)))
                .andExpect(jsonPath("$[0].price", is(101d)))
                .andExpect(jsonPath("$[0].picturePath", is("http://localhost/1.jpg")))
                .andExpect(jsonPath("$[0].*", not(containsInAnyOrder("description"))))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nameNative", is("Random movie 2")))
                .andExpect(jsonPath("$[1].nameRussian", is("Фильм 2")))
                .andExpect(jsonPath("$[1].yearOfRelease", is(2001)))
                .andExpect(jsonPath("$[1].rating", is(200d)))
                .andExpect(jsonPath("$[1].price", is(201d)))
                .andExpect(jsonPath("$[1].picturePath", is("http://localhost/2.jpg")))
                .andExpect(jsonPath("$[1].*", not(containsInAnyOrder("description"))));
    }

    @Test
    public void testGetByGenre() throws Exception {
        Collection<Movie> movies = Arrays.asList(
                new Movie.Builder()
                        .id(1)
                        .nameNative("Movie 1")
                        .nameRussian("Фильм 1")
                        .yearOfRelease(2000)
                        .rating(100)
                        .price(101)
                        .picturePath("http://localhost/1.jpg")
                        .build(),
                new Movie.Builder()
                        .id(2)
                        .nameNative("Movie 2")
                        .nameRussian("Фильм 2")
                        .yearOfRelease(2001)
                        .rating(200)
                        .price(201)
                        .picturePath("http://localhost/2.jpg")
                        .build()
        );
        RequestParams requestParams = new RequestParams.Builder().build();

        when(movieService.getByGenre(1, requestParams)).thenReturn(movies);

        mockMvc.perform(get("/movie/genre/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nameNative", is("Movie 1")))
                .andExpect(jsonPath("$[0].nameRussian", is("Фильм 1")))
                .andExpect(jsonPath("$[0].yearOfRelease", is(2000)))
                .andExpect(jsonPath("$[0].rating", is(100d)))
                .andExpect(jsonPath("$[0].price", is(101d)))
                .andExpect(jsonPath("$[0].picturePath", is("http://localhost/1.jpg")))
                .andExpect(jsonPath("$[0].*", not(containsInAnyOrder("description"))))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nameNative", is("Movie 2")))
                .andExpect(jsonPath("$[1].nameRussian", is("Фильм 2")))
                .andExpect(jsonPath("$[1].yearOfRelease", is(2001)))
                .andExpect(jsonPath("$[1].rating", is(200d)))
                .andExpect(jsonPath("$[1].price", is(201d)))
                .andExpect(jsonPath("$[1].picturePath", is("http://localhost/2.jpg")))
                .andExpect(jsonPath("$[1].*", not(containsInAnyOrder("description"))));
    }

    @Test
    public void testGetByGenreNoFound() throws Exception {
        Collection<Movie> movies = new ArrayList<>();
        RequestParams requestParams = new RequestParams.Builder().build();

        when(movieService.getByGenre(2, requestParams)).thenReturn(movies);

        mockMvc.perform(get("/movie/genre/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Configuration
    @EnableWebMvc
    public static class TestContext {

        @Bean
        public MovieService movieService() {
            return Mockito.mock(MovieService.class);
        }

        @Bean
        public MovieController movieController() {
            return new MovieController();
        }
    }
}
