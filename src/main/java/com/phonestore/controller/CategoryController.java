package com.phonestore.controller;

import com.phonestore.constant.CategoryEndpointConstant;
import com.phonestore.dto.ResultDTO;
import com.phonestore.entity.Category;
import com.phonestore.service.CategoryService;
import com.phonestore.util.DataUtil;
import com.phonestore.util.ReflectionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet(value = {
        CategoryEndpointConstant.CATEGORY_SEARCH_ENDPOINT,
        CategoryEndpointConstant.CATEGORY_INSERT_ENDPOINT,
        CategoryEndpointConstant.CATEGORY_UPDATE_ENDPOINT
})
public class CategoryController extends HttpServlet {

    private final static String PATH_UI_CATEGORY_FORM = "/view/admin/category/form.jsp";
    private final static String PATH_UI_CATEGORY_INDEX = "/view/admin/category/index.jsp";

    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        this.categoryService = new CategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String endpoint = req.getServletPath();

        if (CategoryEndpointConstant.CATEGORY_SEARCH_ENDPOINT.equals(endpoint)) {
            req.setAttribute("categories", this.categoryService.findAll());
            req.getRequestDispatcher(PATH_UI_CATEGORY_INDEX).forward(req, resp);
            return;
        }

        String id = req.getParameter("id");
        int idNum = DataUtil.safeToInt(id);
        req.setAttribute("category", idNum == 0 ? new Category() : this.categoryService.findById(idNum));
        req.getRequestDispatcher(PATH_UI_CATEGORY_FORM).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String endpoint = req.getServletPath();

        ResultDTO<Category> resultSubmit = null;
        Category category = ReflectionUtil.requestParamToObject(req, Category.class);

        if (CategoryEndpointConstant.CATEGORY_INSERT_ENDPOINT.equals(endpoint)) {
            resultSubmit = this.categoryService.save(category);
        }

        if (CategoryEndpointConstant.CATEGORY_UPDATE_ENDPOINT.equals(endpoint)) {
            resultSubmit = this.categoryService.update(category);
        }

        if (Objects.nonNull(resultSubmit) && resultSubmit.isError()) {
            req.setAttribute("error", resultSubmit.getMessage());
            req.setAttribute("category", resultSubmit.getData());
            req.getRequestDispatcher(PATH_UI_CATEGORY_FORM).forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath()+CategoryEndpointConstant.CATEGORY_SEARCH_ENDPOINT);
    }

    @Override
    public void destroy() {
        this.categoryService = null;
    }
}