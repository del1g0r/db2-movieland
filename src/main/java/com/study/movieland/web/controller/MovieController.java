package com.study.movieland.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MovieController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MovieService movieService;

    @RequestMapping(path = {"/v1/movie"}, method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getJsonAll() throws JsonProcessingException {
        return objectMapper.writeValueAsString(movieService.getAll());
    }
}


