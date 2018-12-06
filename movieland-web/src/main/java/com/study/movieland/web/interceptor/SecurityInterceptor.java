package com.study.movieland.web.interceptor;

import com.study.movieland.entity.Session;
import com.study.movieland.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityInterceptor.class);

    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("uuid");
        Session session = securityService.getSession(token);
        MDC.put("login", session == null ? "guest" : session.getUser().getEMail());
        MDC.put("requestId", UUID.randomUUID().toString());

        return super.preHandle(request, response, handler);
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
