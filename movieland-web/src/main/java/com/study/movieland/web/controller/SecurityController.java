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

    @PostMapping(path = {"login"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

    @DeleteMapping(path = {"logout"})
    public void logout(@RequestHeader("uuid") String token) {
        securityService.logout(token);
    }

    @GetMapping(path = {"test"}, produces = MediaType.TEXT_HTML_VALUE)
    public String  test() {
       return  "<html><body>" +
               "<script>\n" +
               "function test() {\n" +
               "var xhr = new XMLHttpRequest();\n" +
               "var url = \"login\";\n" +
               "xhr.open(\"POST\", url, true);\n" +
               "xhr.setRequestHeader(\"Content-Type\", \"application/json\");\n" +
               "xhr.onreadystatechange = function () {\n" +
               "    if (xhr.readyState === 4 && xhr.status === 200) {\n" +
               "        var json = JSON.parse(xhr.responseText);\n" +
               "        console.log(json.email + \", \" + json.password);\n" +
               "    }\n" +
               "};\n" +
               "var data = JSON.stringify({\"email\": \"ronald.reynolds66@example.com\", \"password\": \"paco\"});\n" +
               "xhr.send(data);\n" +
               "}\n" +
               "</script>" +
               "<form enctype='application/json' action=\"login\" method=\"POST\">Name <input type=\"text\" name=\"login\"/>\n" +
               "            Password <input type=\"password\" name=\"password\"/>\n" +
               "            <input type=\"button\" onclick=\"test()\" value=\"Login\">\n</form></body></html>";
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}