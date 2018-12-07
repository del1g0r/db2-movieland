package com.study.movieland.web.controller;

import com.study.movieland.dto.LoginRequestDto;
import com.study.movieland.dto.LoginResponseDto;
import com.study.movieland.entity.Session;
import com.study.movieland.service.SecurityService;
import com.study.movieland.web.exception.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class SecurityController {

    private SecurityService securityService;

    @PostMapping(path = {"login"}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequest) {
        Session session = securityService.login(loginRequest.getEMail(), loginRequest.getPassword());
        if (session == null) {
            throw new InvalidUserException(loginRequest.getEMail());
        }
        LoginResponseDto response = new LoginResponseDto();
        response.setUuid(session.getToken());
        response.setNickname(session.getUser().getNickName());
        return response;
    }

    @DeleteMapping(path = {"logout"}, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void logout(@RequestHeader("uuid") String token) {
        securityService.logout(token);
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}