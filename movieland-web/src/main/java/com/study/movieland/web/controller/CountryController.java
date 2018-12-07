package com.study.movieland.web.controller;

import com.study.movieland.entity.Country;
import com.study.movieland.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(path = "country")
public class CountryController {

    private CountryService countryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Collection<Country> getAll() {
        return countryService.getAll();
    }

    @Autowired
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }
}