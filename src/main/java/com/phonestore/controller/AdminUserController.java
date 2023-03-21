package com.phonestore.controller;

import com.phonestore.constant.UserStatusEnum;
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
import java.util.List;
import java.util.Objects;

import static com.phonestore.constant.UserEndpointConstant.*;

@WebServlet(value = {
        ADMIN_USER_ENDPOINT,
        ADMIN_LOCK_USER_ENDPOINT,
        ADMIN_UNLOCK_USER_ENDPOINT
})
public class AdminUserController extends HttpServlet {

    public static final String UI_USER_PATH = "/view/admin/user/index.jsp";
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String servletPath = req.getServletPath();

        if (ADMIN_USER_ENDPOINT.equals(servletPath)) {
            this.index(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String servletPath = req.getServletPath();
        User user = ReflectionUtil.requestParamToObject(req, User.class);
        if (ADMIN_LOCK_USER_ENDPOINT.equals(servletPath)) {
            this.lockUser(req, resp, user);
        }

        if (ADMIN_UNLOCK_USER_ENDPOINT.equals(servletPath)) {
            this.unlockUser(req, resp, user);
        }
    }

    protected void index(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        User user = ReflectionUtil.requestParamToObject(req, User.class);
        if (Objects.nonNull(user.getStatus()) && user.getStatus() == -1) {
            user.setStatus(null);
        }
        List<User> users = this.userService.searchUser(user);
        req.setAttribute("users", users);
        req.getRequestDispatcher(UI_USER_PATH).forward(req, res);
    }

    protected void lockUser(HttpServletRequest req, HttpServletResponse res, User user) throws IOException, ServletException {
        ResultDTO resultDTO = this.userService.lockUser(user.getUsername());
        if (resultDTO.isError()) {
            req.setAttribute("error", resultDTO.getMessage());
            req.getRequestDispatcher(UI_USER_PATH).forward(req, res);
            return;
        }
        res.sendRedirect(req.getContextPath()+ADMIN_USER_ENDPOINT);
    }

    protected void unlockUser(HttpServletRequest req, HttpServletResponse res, User user) throws ServletException, IOException {
        ResultDTO resultDTO = this.userService.unlockUser(user.getUsername());
        if (resultDTO.isError()) {
            req.setAttribute("error", resultDTO.getMessage());
            req.getRequestDispatcher(UI_USER_PATH).forward(req, res);
            return;
        }
        res.sendRedirect(req.getContextPath()+ADMIN_USER_ENDPOINT);
    }

    @Override
    public void destroy() {
        this.userService = null;
    }
}