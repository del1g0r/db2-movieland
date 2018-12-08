package com.study.movieland.web.interceptor;

import com.study.movieland.entity.Role;
import com.study.movieland.entity.Session;
import com.study.movieland.entity.User;
import com.study.movieland.service.SecurityService;
import com.study.movieland.web.UserHolder;
import com.study.movieland.web.annotation.ProtectedBy;
import com.study.movieland.web.exception.ForbiddenException;
import com.study.movieland.web.exception.NotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

@Service
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(SecurityInterceptor.class);

    private SecurityService securityService;

    private void checkPermissions(Object handler, User user) {
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();
            ProtectedBy protectedBy = method.getAnnotation(ProtectedBy.class);
            if (protectedBy != null
                    && Arrays.stream(protectedBy.role()).noneMatch(
                    x -> x.equals(user.getRole()))) {
                if (user.getRole().equals(Role.GUEST)) {
                    throw new NotAuthorizedException();
                }
                throw new ForbiddenException(user.getEMail());
            }
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        MDC.put("requestId", UUID.randomUUID().toString());

        String token = request.getHeader("uuid");
        Session session = securityService.getSession(token);
        User user = session == null ? securityService.getDefaultUser() : session.getUser();
        checkPermissions(handler, user);

        MDC.put("login", user.getEMail());

        UserHolder.setCurrentUser(user);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        MDC.clear();
        UserHolder.clear();
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
