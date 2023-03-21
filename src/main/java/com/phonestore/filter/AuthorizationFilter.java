package com.phonestore.filter;

import com.phonestore.constant.AppConstant;
import com.phonestore.entity.User;
import com.phonestore.service.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class AuthorizationFilter implements Filter {

    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.userService = new UserService();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        User user = this.userService.getCurrentUser(session);

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith(request.getContextPath()+"/admin")) {
            if (Objects.nonNull(user) && !AppConstant.ROLE_ADMIN.equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
        } else {
            if (Objects.nonNull(user) && !AppConstant.ROLE_USER.equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        this.userService = null;
    }
}