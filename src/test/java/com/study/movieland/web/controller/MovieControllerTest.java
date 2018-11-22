package com.study.movieland.web.controller;

import com.study.movieland.entity.Movie;
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

import java.util.Arrays;
import java.util.List;

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
    public void testGetAll() throws Exception {
        List<Movie> movies = Arrays.asList(
                new Movie.Builder()
                        .id(1)
                        .nameNative("Movie 1")
                        .nameRussian("Фильм 1")
                        .yearOfRelease(2000)
                        .description("Description 1")
                        .rating(100)
                        .price(101)
                        .picturePath("http://localhost/1.jpg")
                        .build(),
                new Movie.Builder()
                        .id(2)
                        .nameNative("Movie 2")
                        .nameRussian("Фильм 2")
                        .yearOfRelease(2001)
                        .description("Description 2")
                        .rating(200)
                        .price(201)
                        .picturePath("http://localhost/2.jpg")
                        .build()
        );

        when(movieService.getAll()).thenReturn(movies);

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
        List<Movie> movies = Arrays.asList(
                new Movie.Builder()
                        .id(1)
                        .nameNative("Random movie 1")
                        .nameRussian("Фильм 1")
                        .yearOfRelease(2000)
                        .description("Description 1")
                        .rating(100)
                        .price(101)
                        .picturePath("http://localhost/1.jpg")
                        .build(),
                new Movie.Builder()
                        .id(2)
                        .nameNative("Random movie 2")
                        .nameRussian("Фильм 2")
                        .yearOfRelease(2001)
                        .description("Description 2")
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
