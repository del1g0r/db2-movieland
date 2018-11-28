package com.study.movieland.web.controller;

import com.study.movieland.entity.Genre;
import com.study.movieland.service.GenreService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GenreControllerTest.TestContext.class)
@WebAppConfiguration
public class GenreControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private GenreService genreService;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        List<Genre> genres = Arrays.asList(
                new Genre.Builder()
                        .id(1)
                        .name("Genre 1")
                        .build(),
                new Genre.Builder()
                        .id(2)
                        .name("Genre 2")
                        .build(),
                new Genre.Builder()
                        .id(3)
                        .name("Genre 3")
                        .build()
        );

        when(genreService.getAll()).thenReturn(genres);

        mockMvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Genre 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Genre 2")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Genre 3")));
    }

    @Configuration
    @EnableWebMvc
    public static class TestContext {

        @Bean
        public GenreService genreService() {
            return Mockito.mock(GenreService.class);
        }

        @Bean
        public GenreController genreController() {
            return new GenreController();
        }
    }
}

