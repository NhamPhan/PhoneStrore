package com.phonestore.controller;

import com.phonestore.constant.AppConstant;
import com.phonestore.constant.UserEndpointConstant;
import com.phonestore.dto.ResultDTO;
import com.phonestore.entity.User;
import com.phonestore.service.UserService;
import com.phonestore.util.ReflectionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {
        UserEndpointConstant.UPDATE_USER_ENDPOINT
})
public class UserController extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        if (UserEndpointConstant.UPDATE_USER_ENDPOINT.equals(servletPath)) {

            User user = ReflectionUtil.requestParamToObject(req, User.class);
            ResultDTO<User> result = this.userService.update(user);
            if (!result.isError()) {
                req.getSession().setAttribute(AppConstant.SESSION_USER, result.getData());
            } else {
                req.getSession().setAttribute("error", result.getMessage());
            }
            resp.sendRedirect(req.getContextPath()+"/product");
        }
    }

    @Override
    public void destroy() {
        this.userService = null;
    }
}