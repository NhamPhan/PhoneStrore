package com.phonestore.filter;

import com.phonestore.constant.AppConstant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();
        Object attribute = session.getAttribute(AppConstant.SESSION_USER);

        if (Objects.isNull(attribute)) {
            res.sendRedirect(req.getContextPath()+"/login");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}