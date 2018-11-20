package com.study.movieland.web.filter;

import com.study.movieland.entity.Role;
import com.study.movieland.entity.Session;
import com.study.movieland.service.SecurityService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;

public class RoleFilter implements Filter {

    private SecurityService securityService;
    private EnumSet<Role> roles = EnumSet.noneOf(Role.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        boolean isAuth = roles.contains(Role.GUEST);
        Cookie[] cookies;
        Session session = null;
        if ((cookies = httpServletRequest.getCookies()) != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    String token = cookie.getValue();
                    session = securityService.getSession(token);
                    if (session != null) {
                        if (roles.contains(session.getUser().getRole())) {
                            isAuth = true;
                        }
                    }
                    break;
                }
            }
        }

        if (isAuth) {
            if (servletRequest.getAttribute("session") == null) {
                servletRequest.setAttribute("session", session);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        for (String strRole : filterConfig.getInitParameter("roles").split(",")) {
            roles.add(Role.getByName(strRole));
        }
        securityService = WebApplicationContextUtils.getRequiredWebApplicationContext
                (filterConfig.getServletContext()).getBean(SecurityService.class);
    }

    @Override
    public void destroy() {
    }
}


