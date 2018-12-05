package com.study.movieland.web.controller;

import com.study.movieland.entity.Genre;
import com.study.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "genre")
public class GenreController {

    private GenreService genreService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Collection<Genre> getAll() {
        return genreService.getAll();
    }

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }
}