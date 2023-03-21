package com.phonestore.controller;

import com.phonestore.constant.ProductEndpointConstant;
import com.phonestore.constant.HomeEndpointConstant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = {
        HomeEndpointConstant.HOME_ENDPOINT,
        HomeEndpointConstant.HOME_ADMIN_ENDPOINT
})
public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String endpoint = req.getServletPath();

        if (HomeEndpointConstant.HOME_ENDPOINT.equals(endpoint)) {
            resp.sendRedirect(req.getContextPath()+ ProductEndpointConstant.PRODUCT_SEARCH_ENDPOINT);
        }

        if (HomeEndpointConstant.HOME_ADMIN_ENDPOINT.equals(endpoint)) {
            req.getRequestDispatcher("/view/admin/index.jsp").forward(req, resp);
        }
    }
}