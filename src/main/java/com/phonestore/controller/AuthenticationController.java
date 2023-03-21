package com.phonestore.controller;

import com.phonestore.constant.AppConstant;
import com.phonestore.constant.AuthEndpointConstant;
import com.phonestore.constant.HomeEndpointConstant;
import com.phonestore.dto.ResultDTO;
import com.phonestore.entity.User;
import com.phonestore.service.UserService;
import com.phonestore.util.ReflectionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = {
        AuthEndpointConstant.AUTH_LOGIN_ENDPOINT,
        AuthEndpointConstant.AUTH_LOGOUT_ENDPOINT,
        AuthEndpointConstant.AUTH_REGISTER_ENDPOINT
})
public class AuthenticationController extends HttpServlet {

    private static final String PATH_LOGIN_VIEW = "/view/login.jsp";
    private static final String PATH_REGISTER_VIEW = "/view/register.jsp";

    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String endpoint = req.getServletPath();
        if (AuthEndpointConstant.AUTH_LOGIN_ENDPOINT.equals(endpoint)) {
            req.getRequestDispatcher(PATH_LOGIN_VIEW).forward(req, resp);
            return;
        }

        if (AuthEndpointConstant.AUTH_REGISTER_ENDPOINT.equals(endpoint)) {
            req.setAttribute("user", new User());
            req.getRequestDispatcher(PATH_REGISTER_VIEW).forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        session.removeAttribute(AppConstant.SESSION_USER);
        resp.sendRedirect(req.getContextPath()+AuthEndpointConstant.AUTH_LOGIN_ENDPOINT);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String endpoint = req.getServletPath();
        User user = ReflectionUtil.requestParamToObject(req, User.class);

        if (AuthEndpointConstant.AUTH_REGISTER_ENDPOINT.equals(endpoint)) {
            ResultDTO<User> result = this.userService.register(user);
            if (result.isError()) {
                req.setAttribute("error", result.getMessage());
                req.setAttribute("user", user);
                req.getRequestDispatcher(PATH_REGISTER_VIEW).forward(req, resp);
                return;
            }
            resp.sendRedirect(req.getContextPath()+AuthEndpointConstant.AUTH_LOGIN_ENDPOINT);
            return;
        }

        if (AuthEndpointConstant.AUTH_LOGIN_ENDPOINT.equals(endpoint)) {

            ResultDTO<User> result = this.userService.login(user);
            if (result.isError()) {
                req.setAttribute("error", result.getMessage());
                req.getRequestDispatcher(PATH_LOGIN_VIEW).forward(req, resp);
                return;
            }
            User data = result.getData();
            req.getSession().setAttribute(AppConstant.SESSION_USER, data);
            if (AppConstant.ROLE_ADMIN.equals(data.getRole())) {
                resp.sendRedirect(req.getContextPath()+ HomeEndpointConstant.HOME_ADMIN_ENDPOINT);
            }

            if (AppConstant.ROLE_USER.equals(data.getRole())) {
                resp.sendRedirect(req.getContextPath() + HomeEndpointConstant.HOME_ENDPOINT);
            }
        }
    }

    @Override
    public void destroy() {
        this.userService = null;
    }
}